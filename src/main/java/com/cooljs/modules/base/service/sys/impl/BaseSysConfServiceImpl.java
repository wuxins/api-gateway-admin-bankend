package com.cooljs.modules.base.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.base.entity.sys.BaseSysConfEntity;
import com.cooljs.modules.base.mapper.sys.BaseSysConfMapper;
import com.cooljs.modules.base.service.sys.BaseSysConfService;
import org.springframework.stereotype.Service;

/**
 * 系统配置
 */
@Service
public class BaseSysConfServiceImpl extends BaseServiceImpl<BaseSysConfMapper, BaseSysConfEntity> implements BaseSysConfService {
    @Override
    public void updateValue(String key, String value) {
        LambdaUpdateWrapper<BaseSysConfEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(BaseSysConfEntity::getCValue, value).eq(BaseSysConfEntity::getCKey, key);
        update(wrapper);
    }

    @Override
    public String getValue(String key) {
        BaseSysConfEntity baseSysConfEntity = getOne(Wrappers.<BaseSysConfEntity>lambdaQuery().eq(BaseSysConfEntity::getCKey, key));
        if (baseSysConfEntity != null) {
            return baseSysConfEntity.getCValue();
        }
        return null;
    }
}