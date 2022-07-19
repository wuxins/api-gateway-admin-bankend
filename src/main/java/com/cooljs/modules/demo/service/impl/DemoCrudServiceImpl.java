package com.cooljs.modules.demo.service.impl;

import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.demo.entity.DemoCrudEntity;
import com.cooljs.modules.demo.mapper.DemoCrudMapper;
import com.cooljs.modules.demo.service.DemoCrudService;
import org.springframework.stereotype.Service;

/**
 * 测试CURD
 */
@Service
public class DemoCrudServiceImpl extends BaseServiceImpl<DemoCrudMapper, DemoCrudEntity> implements DemoCrudService {
}