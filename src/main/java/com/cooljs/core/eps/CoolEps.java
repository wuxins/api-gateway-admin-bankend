package com.cooljs.core.eps;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cooljs.core.annotation.CoolRestController;
import com.cooljs.core.annotation.CoolTable;
import com.cooljs.modules.base.controller.admin.sys.AdminBaseSysUserController;
import com.tangzc.mpe.actable.annotation.Column;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * 实体信息与路径
 */
@Component
@Slf4j
public class CoolEps {

    @Value("${server.port}")
    private int serverPort;

    @Value("${springdoc.api-docs.path}")
    private String apiUrl;

    private Dict entityInfo;

    private JSONObject swaggerInfo;

    public Dict info;

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public Dict getInfo() {
        return info;
    }

    @Async
    public void init() {
        entityInfo = Dict.create();
        swaggerInfo = swaggerInfo();
        entity();
        urls();
    }

    /**
     * 构建所有的url
     */
    private void urls() {
        Dict result = Dict.create();
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> methodEntry : map.entrySet()) {
            RequestMappingInfo info = methodEntry.getKey();
            HandlerMethod method = methodEntry.getValue();
            String module = getModule(method);
            if (StrUtil.isNotEmpty(module)) {
                String entityName = getEntity(method.getBeanType());
                String methodPath = getMethodUrl(method);
                String prefix = Objects.requireNonNull(getUrl(info)).replaceFirst("(?s)(.*)" + methodPath, "$1" + "");
                if (result.get(module) == null) {
                    result.set(module, new ArrayList<Dict>());
                }
                List<Dict> urls = result.getBean(module);
                Dict item = CollUtil.findOne(urls, dict -> {
                    if (dict != null) {
                        return dict.getStr("module").equals(module) && dict.getStr("controller").equals(method.getBeanType().getSimpleName());
                    } else {
                        return false;
                    }
                });
                if (item != null) {
                    item.set("api", apis(prefix, methodPath, item.getBean("api")));
                } else {
                    item = Dict.create();
                    item.set("controller", method.getBeanType().getSimpleName());
                    item.set("module", module);
                    item.set("name", entityName);
                    item.set("api", new ArrayList<Dict>());
                    item.set("prefix", prefix);
                    item.set("columns", entityInfo.get(entityName));
                    item.set("api", apis(prefix, methodPath, item.getBean("api")));
                    urls.add(item);
                }
                result.set(module, urls);
            }
        }
        info = result;
    }

    /**
     * 设置所有的api
     *
     * @param prefix     路由前缀
     * @param methodPath 方法路由
     * @param list       api列表
     * @return api列表
     */
    private List<Dict> apis(String prefix, String methodPath, List<Dict> list) {
        Dict item = Dict.create();
        item.set("method", "");
        item.set("path", methodPath);
        item.set("summary", "");
        item.set("tag", "");
        item.set("dts", new Object());
        setSwaggerInfo(item, prefix + methodPath);
        list.add(item);
        return list;
    }

    /**
     * 设置swagger相关信息
     *
     * @param item 信息载体
     * @param url  url地址
     */
    private void setSwaggerInfo(Dict item, String url) {
        JSONObject paths = swaggerInfo.getJSONObject("paths");
        JSONObject urlInfo = paths.getJSONObject(url);
        String method = urlInfo.keySet().iterator().next();
        JSONObject methodInfo = urlInfo.getJSONObject(method);
        item.set("dts", methodInfo);
        item.set("method", method);
        item.set("summary", methodInfo.getStr("summary"));
        item.set("description", methodInfo.get("description"));
    }

    /**
     * 获得方法的url地址
     *
     * @param handlerMethod 方法
     * @return 方法url地址
     */
    private String getMethodUrl(HandlerMethod handlerMethod) {
        String url = null;
        Method method = handlerMethod.getMethod();
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            url = annotation.annotationType().getName();
            if (annotation.annotationType().getName().contains("org.springframework.web.bind.annotation")) {
                InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation); //获取被代理的对象
                Map map = (Map) ReflectUtil.getFieldValue(invocationHandler, "memberValues");
                url = ((String[]) map.get("value"))[0];
                break;
            }
        }
        return url;
    }

    /**
     * 获得url地址
     *
     * @param info 路由信息
     * @return url地址
     */
    private String getUrl(RequestMappingInfo info) {
        if (info.getPathPatternsCondition() == null) {
            return null;
        }
        Set<String> paths = info.getPathPatternsCondition().getPatternValues();
        return paths.iterator().next();
    }

    /**
     * 获得模块
     *
     * @param method 方法
     * @return 模块
     */
    private String getModule(HandlerMethod method) {
        String beanName = method.getBeanType().getName();
        String[] beanNames = beanName.split("[.]");
        int index = ArrayUtil.indexOf(beanNames, "modules");
        if (index > 0) {
            return beanNames[index + 1];
        }
        return null;
    }

    /**
     * 获得swagger的json信息
     *
     * @return
     */
    private JSONObject swaggerInfo() {
        try {
            HttpRequest request = HttpUtil.createGet("http://127.0.0.1:" + serverPort + apiUrl);
            return JSONUtil.parseObj(request.execute().body());
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    private void controller() {
        // 扫描所有的Controller类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("", CoolRestController.class);
        classes.forEach(e -> {
            if (e == AdminBaseSysUserController.class) {
                // Type type = getEntity(e);
                CoolRestController coolRestController = e.getAnnotation(CoolRestController.class);
                System.out.println(coolRestController);
                Method[] methods = ReflectUtil.getMethodsDirectly(e, false);
            }
        });
    }

    /**
     * 获得Controller上的实体类型
     *
     * @param controller Controller类
     * @return 实体名称
     */
    private String getEntity(Class<?> controller) {
        try {
            Type type = ((ParameterizedType) SpringUtil.getBean(controller).getClass().getGenericSuperclass()).getActualTypeArguments()[1];
            String[] names = type.getTypeName().split("[.]");
            return names[names.length - 1];
        } catch (Exception e) {
            return "";
        }
    }

    private void entity() {
        // 扫描所有的实体类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("", CoolTable.class);
        classes.forEach(e -> {
            // 获得属性
            Field[] fields = ClassUtil.getDeclaredFields(e);
            List<Dict> columns = columns(fields);
            entityInfo.set(e.getSimpleName(), columns);
        });
    }

    /**
     * 获得所有的列
     *
     * @param fields 字段名
     * @return 所有的列
     */
    private List<Dict> columns(Field[] fields) {
        List<Dict> dictList = new ArrayList<>();
        for (Field field : fields) {
            Dict dict = Dict.create();
            Column columnInfo = AnnotatedElementUtils.findMergedAnnotation(field, Column.class);
            if (columnInfo == null) {
                continue;
            }
            dict.set("comment", columnInfo.comment());
            dict.set("length", columnInfo.length());
            dict.set("propertyName", field.getName());
            dict.set("type", matchType(field.getType().getName()));
            dict.set("nullable", !columnInfo.notNull());
            dictList.add(dict);
        }
        return dictList;
    }

    /**
     * java类型转换成JavaScript对应的类型
     *
     * @param type 类型
     * @return JavaScript类型
     */
    private String matchType(String type) {
        switch (type) {
            case "java.lang.Boolean":
                return "boolean";
            case "java.lang.Long":
            case "java.lang.Integer":
            case "java.lang.Short":
            case "java.lang.Float":
            case "java.lang.Double":
                return "number";
            case "java.util.Date":
                return "date";
            default:
                return "string";
        }
    }
}
