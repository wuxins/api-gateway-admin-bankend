package com.cooljs.modules.space.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 文件空间信息
 */
@Data
@CoolTable(value = "space_info", comment = "文件空间信息")
public class SpaceInfoEntity extends BaseEntity<SpaceInfoEntity> {
    @Column(comment = "地址", notNull = true)
    private String url;

    @Column(comment = "类型", notNull = true)
    private String type;

    @Column(comment = "分类ID", type = MySqlTypeConstant.TINYINT)
    private Integer classifyId;
}
