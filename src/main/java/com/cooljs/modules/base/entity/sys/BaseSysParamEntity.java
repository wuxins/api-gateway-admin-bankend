package com.cooljs.modules.base.entity.sys;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Index;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 参数配置
 */
@Data
@CoolTable(value = "base_sys_param", comment = "系统参数配置")
public class BaseSysParamEntity extends BaseEntity<BaseSysParamEntity> {
    @Index
    @Column(comment = "键", notNull = true)
    private String keyName;

    @Column(comment = "名称")
    private String name;

    @Column(comment = "数据", type = MySqlTypeConstant.TEXT)
    private String data;

    @Column(comment = "数据类型 0:字符串 1:数组 2:键值对", defaultValue = "0", type = MySqlTypeConstant.TINYINT)
    private Integer dataType;

    @Column(comment = "备注")
    private String remark;
}
