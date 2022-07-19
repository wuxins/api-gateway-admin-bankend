package com.cooljs.modules.demo.entity;

import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import lombok.Data;

/**
 * 商品demo
 */
@Data
@CoolTable(value = "demo_goods_entity", comment = "商品demo")
public class DemoGoodsEntity extends BaseEntity<DemoGoodsEntity> {

    @Column(comment = "标题", notNull = true)
    private String title;

    @Column(comment = "价格", notNull = true)
    private Double price;
}
