package com.cooljs.modules.base.mapper.sys;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cooljs.modules.base.entity.sys.BaseSysUserEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户
 */
public interface BaseSysUserMapper extends BaseMapper<BaseSysUserEntity> {

    /**
     * 分页查询用户信息
     *
     * @param page    分页信息
     * @param wrapper 查询条件
     * @return 用户信息
     */
    Page<BaseSysUserEntity> page(@Param("page") Page<BaseSysUserEntity> page, @Param(Constants.WRAPPER) Wrapper<BaseSysUserEntity> wrapper);
}
