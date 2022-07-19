package com.cooljs.modules.base.mapper.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cooljs.modules.base.entity.sys.BaseSysLogEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 系统日志
 */
public interface BaseSysLogMapper extends BaseMapper<BaseSysLogEntity> {
    Page<BaseSysLogEntity> page(@Param("page") Page<BaseSysLogEntity> page, @Param(Constants.WRAPPER) QueryWrapper<BaseSysLogEntity> queryWrapper);
}
