package com.cooljs.modules.base.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.core.cache.CoolCache;
import com.cooljs.modules.base.entity.sys.BaseSysParamEntity;
import com.cooljs.modules.base.mapper.sys.BaseSysParamMapper;
import com.cooljs.modules.base.service.sys.BaseSysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统参数配置
 */
@Service
public class BaseSysParamServiceImpl extends BaseServiceImpl<BaseSysParamMapper, BaseSysParamEntity> implements BaseSysParamService {
    @Resource
    private CoolCache coolCache;

    @Override
    public String htmlByKey(String key) {
        String data = dataByKey(key);
        return "<html><body>" + (StrUtil.isNotEmpty(data) ? data : "key notfound") + "</body></html>";
    }

    @Override
    public String dataByKey(String key) {
        BaseSysParamEntity baseSysParamEntity = coolCache.get(key, BaseSysParamEntity.class);
        if (baseSysParamEntity == null) {
            baseSysParamEntity = getOne(Wrappers.<BaseSysParamEntity>lambdaQuery().eq(BaseSysParamEntity::getKeyName, key));
        }
        if (baseSysParamEntity != null) {
            coolCache.set("param:" + baseSysParamEntity.getKeyName(), baseSysParamEntity);
            return baseSysParamEntity.getData();
        }
        return null;
    }

    @Override
    public void modifyAfter(JSONObject requestParams, BaseSysParamEntity baseSysParamEntity) {
        List<BaseSysParamEntity> list = this.list();
        list.forEach(e -> {
            coolCache.set("param:" + e.getKeyName(), e);
        });
    }
}