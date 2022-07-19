package com.cooljs.modules.dict.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import lombok.Data;

@Data
@CoolTable(value = "dict_type", comment = "字典类型")
public class DictTypeEntity extends BaseEntity<DictTypeEntity> {

    @Column(comment = "名称", notNull = true)
    private String name;

    @Column(comment = "标识", notNull = true, value = "keyName")
    private String key;

}
