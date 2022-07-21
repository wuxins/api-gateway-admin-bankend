package com.cooljs.modules.gateway.base;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cooljs.core.request.R;
import com.cooljs.modules.gateway.base.request.CrudOption;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制层基类
 *
 * @param <S>
 * @param <T>
 */
public abstract class BaseController<S extends BaseService<T>, T extends BaseEntity<T>> {
    @Autowired
    protected S service;

    private final String COOL_PAGE_OP = "COOL_PAGE_OP";
    private final String COOL_LIST_OP = "COOL_LIST_OP";

    private final ThreadLocal<CrudOption<T>> pageOption = new ThreadLocal<>();
    private final ThreadLocal<CrudOption<T>> listOption = new ThreadLocal<>();
    private final ThreadLocal<JSONObject> requestParams = new ThreadLocal<>();

    @ModelAttribute
    private void preHandle(HttpServletRequest request, @RequestAttribute JSONObject requestParams) {
        this.pageOption.set(new CrudOption<>(requestParams));
        this.listOption.set(new CrudOption<>(requestParams));
        this.requestParams.set(requestParams);
        init(request, requestParams);
        request.setAttribute(COOL_PAGE_OP, this.pageOption.get());
        request.setAttribute(COOL_LIST_OP, this.listOption.get());
        removeThreadLocal();
    }

    /**
     * 手动移除变量
     */
    private void removeThreadLocal() {
        this.listOption.remove();
        this.pageOption.remove();
        this.requestParams.remove();
    }

    public CrudOption<T> createOp() {
        return new CrudOption<T>(this.requestParams.get());
    }


    public void setListOption(CrudOption<T> listOption) {
        this.listOption.set(listOption);
    }

    public void setPageOption(CrudOption<T> pageOption) {
        this.pageOption.set(pageOption);
    }

    protected abstract void init(HttpServletRequest request, JSONObject requestParams);

    /**
     * 新增
     * <p>
     * //     * @param t 实体对象
     */
    @Operation(summary = "新增", description = "新增信息，对应后端的实体类")
    @PostMapping("/add")
    protected R add(@RequestAttribute() JSONObject requestParams) {
        String body = requestParams.getStr("body");
        if (JSONUtil.isJsonArray(body)) {
            JSONArray array = JSONUtil.parseArray(body);
            return R.ok(Dict.create().set("ids", service.addBatch(requestParams, array.toList(currentEntityClass()))));
        } else {
            return R.ok(Dict.create().set("id", service.add(requestParams, JSONUtil.parseObj(body).toBean(currentEntityClass()))));
        }
    }

    /**
     * 删除
     *
     * @param params 请求参数 ids 数组 或者按","隔开
     */
    @Operation(summary = "删除", description = "支持批量删除 请求参数 ids 数组 或者按\",\"隔开")
    @PostMapping("/delete")
    protected R delete(@RequestBody Map<String, Object> params, @RequestAttribute() JSONObject requestParams) {
        Object ids = params.get("ids");
        if (!(ids instanceof ArrayList)) {
            ids = ids.toString().split(",");
        }
        List<String> idList = Convert.toList(String.class, ids);
        service.delete(requestParams, Convert.toLongArray(idList));
        return R.ok();
    }

    /**
     * 修改
     *
     * @param t 修改对象
     */
    @Operation(summary = "修改", description = "根据ID修改")
    @PostMapping("/update")
    protected R update(@RequestBody T t, @RequestAttribute() JSONObject requestParams) {
        service.update(requestParams, t);
        return R.ok();
    }

    /**
     * 信息
     *
     * @param id ID
     */
    @Operation(summary = "信息", description = "根据ID查询单个信息")
    @GetMapping("/info")
    protected R info(@RequestAttribute() JSONObject requestParams, @RequestParam(required = true) Long id) {
        return R.ok(service.info(requestParams, id));
    }

    /**
     * 列表查询
     *
     * @param requestParams 请求参数
     */
    @Operation(summary = "查询", description = "查询多个信息")
    @PostMapping("/list")
    protected R list(@RequestAttribute() JSONObject requestParams, @RequestAttribute(COOL_LIST_OP) CrudOption<T> option) {
        return R.ok(service.list(requestParams, option.getQueryWrapper()));
    }

    /**
     * 分页查询
     *
     * @param requestParams 请求参数
     */
    @Operation(summary = "分页", description = "分页查询多个信息")
    @PostMapping("/page")
    protected R page(@RequestAttribute() JSONObject requestParams, @RequestAttribute(COOL_PAGE_OP) CrudOption<T> option) {
        Integer page = requestParams.getInt("page", 0);
        Integer size = requestParams.getInt("size", 20);
        return R.ok(pageResult((Page<T>) service.page(requestParams, new Page<T>(page, size), option.getQueryWrapper())));
    }

    /**
     * 分页结果
     *
     * @param page 分页返回数据
     */
    protected Map<String, Object> pageResult(Page<T> page) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("size", page.getSize());
        pagination.put("page", page.getCurrent());
        pagination.put("total", page.getTotal());
        result.put("list", page.getRecords());
        result.put("pagination", pagination);
        return result;
    }

    protected Class<T> currentEntityClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseController.class, 1);
    }

}
