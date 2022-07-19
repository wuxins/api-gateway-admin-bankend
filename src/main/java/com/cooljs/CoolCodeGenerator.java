package com.cooljs;

import com.cooljs.core.code.CodeGenerator;
import com.cooljs.core.code.CodeModel;
import com.cooljs.core.code.CodeTypeEnum;
import com.cooljs.modules.demo.entity.DemoCrudEntity;

/**
 * 代码生成
 */
public class CoolCodeGenerator {
    public static void main(String[] args) {
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.init();

        CodeModel codeModel = new CodeModel();
        codeModel.setType(CodeTypeEnum.ADMIN);
        codeModel.setName("测试CURD");
        codeModel.setModule("demo");
//        codeModel.setSubModule("sys");
        codeModel.setEntity(DemoCrudEntity.class);

        codeGenerator.controller(codeModel);
        codeGenerator.mapper(codeModel);
        codeGenerator.service(codeModel);

        System.out.println("代码生成成功");
    }
}
