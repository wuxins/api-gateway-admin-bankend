package com.cooljs.modules.base.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cooljs.modules.base.entity.sys.BaseSysMenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单
 */
public interface BaseSysMenuMapper extends BaseMapper<BaseSysMenuEntity> {
    /**
     * 根据角色ID获得所有菜单
     *
     * @param roleIds 角色ID集合
     * @return SysMenuEntity
     */
    List<BaseSysMenuEntity> getMenus(@Param("roleIds") Long[] roleIds);
}
