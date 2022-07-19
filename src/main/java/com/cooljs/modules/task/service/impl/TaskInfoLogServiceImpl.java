package com.cooljs.modules.task.service.impl;

import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.task.entity.TaskLogEntity;
import com.cooljs.modules.task.mapper.TaskLogMapper;
import com.cooljs.modules.task.service.TaskInfoLogService;
import org.springframework.stereotype.Service;

@Service
public class TaskInfoLogServiceImpl extends BaseServiceImpl<TaskLogMapper, TaskLogEntity> implements TaskInfoLogService {
}
