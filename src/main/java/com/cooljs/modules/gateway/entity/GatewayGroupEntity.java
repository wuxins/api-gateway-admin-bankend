package com.cooljs.modules.gateway.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.modules.gateway.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import lombok.Data;

/**
 * 分组
 */
@Data
@CoolTable(value = "group", comment = "分组")
public class GatewayGroupEntity extends BaseEntity<GatewayGroupEntity> {

    @Column(comment = "分组名称", notNull = true, length = 64)
    private String name;

    @Column(comment = "分组编码", notNull = true, value = "group_code", length = 16)
    private String groupCode;
}
