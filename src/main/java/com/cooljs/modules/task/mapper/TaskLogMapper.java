package com.cooljs.modules.task.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cooljs.modules.task.entity.TaskLogEntity;
import org.apache.ibatis.annotations.Param;

public interface TaskLogMapper extends BaseMapper<TaskLogEntity> {
    /**
     * 日志
     *
     * @param taskId  任务ID
     * @param status  状态
     * @param wrapper 查询条件
     * @param page    查询条件
     * @return 日志列表
     */
    Page<TaskLogEntity> log(@Param("page") Page<TaskLogEntity> page, @Param("taskId") Long taskId, @Param("status") Integer status, @Param(Constants.WRAPPER) Wrapper<TaskLogEntity> wrapper);
}
