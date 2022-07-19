package com.cooljs.modules.space.service.impl;

import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.space.entity.SpaceTypeEntity;
import com.cooljs.modules.space.mapper.SpaceTypeMapper;
import com.cooljs.modules.space.service.SpaceTypeService;
import org.springframework.stereotype.Service;

/**
 * 文件空间信息
 */
@Service
public class SpaceTypeServiceImpl extends BaseServiceImpl<SpaceTypeMapper, SpaceTypeEntity> implements SpaceTypeService {
}