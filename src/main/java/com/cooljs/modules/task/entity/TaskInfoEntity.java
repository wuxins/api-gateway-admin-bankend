package com.cooljs.modules.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

import java.util.Date;

@Data
@CoolTable(value = "task_info", comment = "任务信息")
public class TaskInfoEntity extends BaseEntity<TaskInfoEntity> {
    /**
     * 任务调度参数key
     */
    @TableField(exist = false)
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    @Column(comment = "名称", notNull = true)
    private String name;

    @Column(comment = "任务ID")
    private String jobId;

    @Column(comment = "最大执行次数 不传为无限次")
    private Integer repeatCount;

    @Column(comment = "每间隔多少毫秒执行一次 如果cron设置了 这项设置就无效")
    private Integer every;

    @Column(comment = "状态 0:停止 1：运行", defaultValue = "1", notNull = true)
    private Integer status;

    @Column(comment = "服务实例名称", notNull = true)
    private String service;

    @Column(comment = "状态 0:cron 1：时间间隔", defaultValue = "0")
    private Integer taskType;

    @Column(comment = "状态 0:系统 1：用户", defaultValue = "0", type = MySqlTypeConstant.TINYINT)
    private Integer type;

    @Column(comment = "任务数据")
    private String data;

    @Column(comment = "备注")
    private String remark;

    @Column(comment = "cron")
    private String cron;

    @Column(comment = "下一次执行时间")
    private Date nextRunTime;

    @Column(comment = "开始时间")
    private Date startDate;

    @Column(comment = "结束时间")
    private Date endDate;
}
