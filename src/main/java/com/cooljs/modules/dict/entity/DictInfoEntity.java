package com.cooljs.modules.dict.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import lombok.Data;

@Data
@CoolTable(value = "dict_info", comment = "字典信息")
public class DictInfoEntity extends BaseEntity<DictInfoEntity> {

    @Column(comment = "类型ID", notNull = true)
    private Long typeId;

    @Column(comment = "父ID")
    private Long parentId;

    @Column(comment = "名称", notNull = true)
    private String name;

    @Column(comment = "排序", defaultValue = "0")
    private Integer orderNum;

    @Column(comment = "备注")
    private String remark;

}
