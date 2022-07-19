package com.cooljs.modules.demo.service.impl;

import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.demo.entity.DemoGoodsEntity;
import com.cooljs.modules.demo.mapper.DemoGoodsMapper;
import com.cooljs.modules.demo.service.DemoGoodsService;
import org.springframework.stereotype.Service;

/**
 * 测试商品信息
 */
@Service
public class DemoGoodsServiceImpl extends BaseServiceImpl<DemoGoodsMapper, DemoGoodsEntity> implements DemoGoodsService {
}