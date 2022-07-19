package com.cooljs.modules.task.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cooljs.core.base.BaseService;
import com.cooljs.modules.task.entity.TaskInfoEntity;
import com.cooljs.modules.task.entity.TaskLogEntity;

/**
 * 任务信息
 */
public interface TaskInfoService extends BaseService<TaskInfoEntity> {

    /**
     * 初始化任务
     */
    void init();

    /**
     * 执行一次
     *
     * @param taskId 任务ID
     */
    void once(Long taskId);

    /**
     * 停止任务
     *
     * @param taskId 任务ID
     */
    void stop(Long taskId);

    /**
     * 任务日志
     *
     * @param taskId 任务ID
     * @param status 任务状态
     * @return 日志列表
     */
    Object log(Page<TaskLogEntity> page, Long taskId, Integer status);

    /**
     * 开始任务
     *
     * @param taskId 任务ID
     * @param type   任务类型
     */
    void start(Long taskId, Integer type);
}
