package com.cooljs.modules.space.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 图片空间信息分类
 */
@Data
@CoolTable(value = "space_type", comment = "图片空间信息分类")
public class SpaceTypeEntity extends BaseEntity<SpaceTypeEntity> {
    @Column(comment = "类别名称", notNull = true)
    private String name;

    @Column(comment = "父分类ID", type = MySqlTypeConstant.TINYINT)
    private Integer parentId;
}
