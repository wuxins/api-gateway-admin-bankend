package com.cooljs.modules.gateway.base;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.cooljs.core.code.CodeModel;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 代码生成器
 */
@Component
public class GatewayCodeGenerator {

    private TemplateEngine templateEngine;

    private String baseSrcPath;

    private String baseResPath;

    @PostConstruct
    public void init() {
        templateEngine = coolTemplateEngine();
        baseSrcPath = System.getProperty("user.dir") + "/src/main/java/com/cooljs/modules/";
        baseResPath = System.getProperty("user.dir") + "/src/main/resources/";
    }

    public TemplateEngine coolTemplateEngine() {
        return TemplateUtil.createEngine(new TemplateConfig("cool/code", TemplateConfig.ResourceMode.CLASSPATH));
    }

    private String filePath(CodeModel codeModel, String type) {
        if (type.equals("controller")) {
            return StrUtil.isEmpty(codeModel.getSubModule()) ?
                    baseSrcPath + codeModel.getModule() + "/" + type + "/" + codeModel.getType().value() :
                    baseSrcPath + codeModel.getModule() + "/" + type + "/" + codeModel.getType().value() + "/" + codeModel.getSubModule();
        }
        if (type.equals("xmlMapper")) {
            return StrUtil.isEmpty(codeModel.getSubModule()) ?
                    baseResPath + "mapper/" + codeModel.getModule() :
                    baseResPath + "mapper/" + codeModel.getModule() + "/" + codeModel.getSubModule();
        }
        return StrUtil.isEmpty(codeModel.getSubModule()) ?
                baseSrcPath + codeModel.getModule() + "/" + type :
                baseSrcPath + codeModel.getModule() + "/" + type + "/" + codeModel.getSubModule();
    }

    /**
     * 生成Mapper
     *
     * @param codeModel 代码模型
     */
    public void mapper(CodeModel codeModel) {
        Template template = templateEngine.getTemplate("/mapper/interface.th");
        String result = template.render(new BeanMap(codeModel));
        FileWriter writer = new FileWriter(filePath(codeModel, "mapper") + "/" + codeModel.getEntity() + "Mapper.java");
        writer.write(result);

        Template xmlTemplate = templateEngine.getTemplate("/mapper/xml.th");
        String xmlResult = xmlTemplate.render(new BeanMap(codeModel));
        FileWriter xmlWriter = new FileWriter(filePath(codeModel, "xmlMapper") + "/" + codeModel.getEntity() + "Mapper.xml");
        xmlWriter.write(xmlResult);

    }

    /**
     * 生成Service
     *
     * @param codeModel 代码模型
     */
    public void service(CodeModel codeModel) {
        Template interfaceTemplate = templateEngine.getTemplate("/service/interface-gateway.th");
        String interfaceResult = interfaceTemplate.render(new BeanMap(codeModel));
        FileWriter interfaceWriter = new FileWriter(filePath(codeModel, "service") + "/" + codeModel.getEntity() + "Service.java");
        interfaceWriter.write(interfaceResult);

        Template template = templateEngine.getTemplate("/service/impl-gateway.th");
        String result = template.render(new BeanMap(codeModel));
        FileWriter writer = new FileWriter(filePath(codeModel, "service") + "/impl/" + codeModel.getEntity() + "ServiceImpl.java");
        writer.write(result);
    }

    /**
     * 生成Controller
     *
     * @param codeModel 代码模型
     */
    public void controller(CodeModel codeModel) {
        Template template = templateEngine.getTemplate("controller-gateway.th");
        System.out.println(codeModel.getType().value());
        Dict data = Dict.create()
                .set("upperType", StrUtil.upperFirst(codeModel.getType().value()))
                .set("url", "/" + codeModel.getType() + "/" + StrUtil.toUnderlineCase(codeModel.getEntity()).replace("_", "/"));
        data.putAll(new BeanMap(codeModel));
        data.set("type", codeModel.getType().value());
        String result = template.render(data);
        FileWriter writer = new FileWriter(filePath(codeModel, "controller") + "/" + StrUtil.upperFirst(codeModel.getType().value()) + codeModel.getEntity() + "Controller.java");
        writer.write(result);
    }
}
