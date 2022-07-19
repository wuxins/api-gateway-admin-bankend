package com.cooljs.modules.base.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Index;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

@Data
@CoolTable(value = "base_sys_menu", comment = "系统菜单表")
public class BaseSysMenuEntity extends BaseEntity<BaseSysMenuEntity> {
    @Index
    @Column(comment = "父菜单ID", type = MySqlTypeConstant.BIGINT)
    private Long parentId;

    @Column(comment = "菜单名称")
    private String name;

    @Column(comment = "权限")
    private String perms;

    @Column(comment = "类型 0：目录 1：菜单 2：按钮", type = MySqlTypeConstant.TINYINT, defaultValue = "0")
    private Integer type;

    @Column(comment = "图标")
    private String icon;

    @Column(comment = "排序", defaultValue = "0")
    private Integer orderNum;

    @Column(comment = "菜单地址")
    private String router;

    @Column(comment = "视图地址")
    private String viewPath;

    @Column(comment = "路由缓存", defaultValue = "true")
    private Boolean keepAlive;

    @Column(comment = "是否显示", defaultValue = "true")
    private Boolean isShow;

    @TableField(exist = false)
    private String parentName;
}
