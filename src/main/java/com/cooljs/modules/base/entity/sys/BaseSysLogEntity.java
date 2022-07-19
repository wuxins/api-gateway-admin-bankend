package com.cooljs.modules.base.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Index;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

@Data
@CoolTable(value = "base_sys_log", comment = "系统日志表")
public class BaseSysLogEntity extends BaseEntity<BaseSysLogEntity> {

    @Index
    @Column(comment = "用户ID", type = MySqlTypeConstant.BIGINT)
    private Long userId;

    @Column(comment = "行为", length = 1000)
    private String action;

    @Column(comment = "IP", length = 50)
    private String ip;

    @Column(comment = "ip地址", length = 50)
    private String ipAddr;

    @Column(comment = "参数", type = MySqlTypeConstant.TEXT)
    private String params;

    // 用户名称
    @TableField(exist = false)
    private String name;
}
