package com.cooljs.modules.base.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cooljs.modules.base.entity.sys.BaseSysRoleMenuEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 系统角色菜单
 */
public interface BaseSysRoleMenuMapper extends BaseMapper<BaseSysRoleMenuEntity> {
    /**
     * 跟菜单关联的所有用户
     *
     * @param menuId 菜单
     * @return 所有用户ID
     */
    Long[] userIds(@Param("menuId") Long menuId);
}
