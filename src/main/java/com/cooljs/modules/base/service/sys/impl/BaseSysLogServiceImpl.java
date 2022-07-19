package com.cooljs.modules.base.service.sys.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.core.util.IPUtils;
import com.cooljs.modules.base.entity.sys.BaseSysLogEntity;
import com.cooljs.modules.base.mapper.sys.BaseSysLogMapper;
import com.cooljs.modules.base.security.CoolSecurityUtil;
import com.cooljs.modules.base.service.sys.BaseSysConfService;
import com.cooljs.modules.base.service.sys.BaseSysLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 系统日志
 */
@Service
public class BaseSysLogServiceImpl extends BaseServiceImpl<BaseSysLogMapper, BaseSysLogEntity> implements BaseSysLogService {
    @Resource
    private BaseSysConfService baseSysConfService;

    @Resource
    private BaseSysLogMapper baseSysLogMapper;

    @Resource
    private CoolSecurityUtil coolSecurityUtil;

    @Resource
    private IPUtils ipUtils;

    @Override
    public Object page(JSONObject requestParams, Page<BaseSysLogEntity> page, QueryWrapper<BaseSysLogEntity> queryWrapper) {
        return baseSysLogMapper.page(page, queryWrapper);
    }

    @Override
    public void clear(boolean isAll) {
        if (isAll) {
            this.remove(Wrappers.<BaseSysLogEntity>lambdaQuery().ge(BaseSysLogEntity::getId, 0));
        } else {
            String keepDay = baseSysConfService.getValue("logKeep");
            int keepDays = Integer.parseInt(StrUtil.isNotEmpty(keepDay) ? keepDay : "30");
            Date beforeDate = DateUtil.offsetDay(new Date(), -keepDays);
            this.remove(Wrappers.<BaseSysLogEntity>lambdaQuery().lt(BaseSysLogEntity::getCreateTime, beforeDate));
        }
    }

    @Override
    public void autoClear() {
        this.clear(false);
    }

    @Async
    @Override
    public void record(HttpServletRequest request, JSONObject requestParams) {
        JSONObject newJSONObject = JSONUtil.parseObj(JSONUtil.toJsonStr(requestParams));
        BaseSysLogEntity logEntity = new BaseSysLogEntity();
        logEntity.setAction(request.getRequestURI());
        logEntity.setIp(ipUtils.getIpAddr(request));
        logEntity.setIpAddr(ipUtils.getCityInfo(logEntity.getIp()));
        if (requestParams != null) {
            JSONObject userInfo = coolSecurityUtil.userInfo(requestParams);
            if (userInfo != null) {
                logEntity.setUserId(userInfo.getLong("userId"));
            }
            newJSONObject.remove("tokenInfo");
            logEntity.setParams(JSONUtil.toJsonStr(newJSONObject));
        }
        save(logEntity);
    }
}