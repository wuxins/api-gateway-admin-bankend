package com.cooljs.modules.task.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.cooljs.core.annotation.CoolTable;
import com.cooljs.core.base.BaseEntity;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.Index;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
import lombok.Data;

@Data
@CoolTable(value = "task_log", comment = "任务日志")
public class TaskLogEntity extends BaseEntity<TaskLogEntity> {

    @Index
    @Column(comment = "任务ID", notNull = true, type = MySqlTypeConstant.BIGINT)
    private Long taskId;

    @Column(comment = "状态 0：失败 1：成功", defaultValue = "0")
    private Integer status;

    @Column(comment = "详情", type = MySqlTypeConstant.TEXT)
    private String detail;

    // 任务名称
    @TableField(exist = false)
    private String taskName;
}
