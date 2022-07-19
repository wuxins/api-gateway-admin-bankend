package com.cooljs.modules.space.service.impl;

import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.space.entity.SpaceInfoEntity;
import com.cooljs.modules.space.mapper.SpaceInfoMapper;
import com.cooljs.modules.space.service.SpaceInfoService;
import org.springframework.stereotype.Service;

/**
 * 文件空间信息
 */
@Service
public class SpaceInfoServiceImpl extends BaseServiceImpl<SpaceInfoMapper, SpaceInfoEntity> implements SpaceInfoService {
}