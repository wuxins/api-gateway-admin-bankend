package com.cooljs.modules.gateway.base;

import com.cooljs.core.code.CodeModel;
import com.cooljs.core.code.CodeTypeEnum;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

/**
 * 代码生成
 */
public class MainCodeGenerator {
    public static void main(String[] args) {

        String module = "gateway";
        Map<String, Class<? extends BaseEntity<? extends BaseEntity<?>>>> maps = Maps.newHashMap();
//        maps.put("API定义", GatewayApiEntity.class);
//        maps.put("API版本信息", GatewayApiVersionEntity.class);
//        maps.put("环境信息", GatewayEnvEntity.class);
//        maps.put("分组信息", GatewayGroupEntity.class);
//        maps.put("租户信息", GatewayTenantEntity.class);
//        maps.put("上游服务信息", GatewayUpstreamServiceEntity.class);
//        maps.put("API租户", GatewayApiTenantEntity.class);

        Set<String> names = maps.keySet();
        names.forEach(name -> {
            GatewayCodeGenerator codeGenerator = new GatewayCodeGenerator();
            codeGenerator.init();
            CodeModel codeModel = new CodeModel();
            codeModel.setType(CodeTypeEnum.ADMIN);
            codeModel.setModule(module);
            codeModel.setName(name);
            codeModel.setEntity(maps.get(name));
            codeGenerator.controller(codeModel);
            codeGenerator.mapper(codeModel);
            codeGenerator.service(codeModel);
        });
        System.out.println("代码生成成功");
    }
}
