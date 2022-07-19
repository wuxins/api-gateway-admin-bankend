package com.cooljs.modules.task.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cooljs.core.base.BaseServiceImpl;
import com.cooljs.modules.task.entity.TaskInfoEntity;
import com.cooljs.modules.task.entity.TaskLogEntity;
import com.cooljs.modules.task.mapper.TaskInfoMapper;
import com.cooljs.modules.task.mapper.TaskLogMapper;
import com.cooljs.modules.task.service.TaskInfoService;
import com.cooljs.modules.task.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskInfoServiceImpl extends BaseServiceImpl<TaskInfoMapper, TaskInfoEntity> implements TaskInfoService {

    @Resource
    private Scheduler scheduler;

    @Resource
    private TaskLogMapper taskLogMapper;

    @Override
    public void init() {
        try {
            List<TaskInfoEntity> list = list();
            list.forEach(scheduleJob -> {
                CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
                if (cronTrigger == null) {
                    ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
                } else {
                    ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
                }
                updateById(scheduleJob);
            });
        } catch (Exception e) {
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void once(Long taskId) {
        ScheduleUtils.run(scheduler, getById(taskId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stop(Long taskId) {
        ScheduleUtils.pauseJob(scheduler, taskId + "");
        TaskInfoEntity taskInfoEntity = getById(taskId);
        taskInfoEntity.setStatus(0);
        updateById(taskInfoEntity);
        modifyAfter(JSONUtil.parseObj(taskInfoEntity), taskInfoEntity);
    }

    @Override
    public Object log(Page<TaskLogEntity> page, Long taskId, Integer status) {
        LambdaQueryWrapper<TaskLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("1 = 1");
        queryWrapper.eq(taskId != null, TaskLogEntity::getTaskId, taskId);
        queryWrapper.eq(status != null, TaskLogEntity::getStatus, status);
        queryWrapper.orderByDesc(TaskLogEntity::getCreateTime);
        return taskLogMapper.log(page, taskId, status, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void start(Long taskId, Integer type) {
        TaskInfoEntity taskInfoEntity = getById(taskId);
        taskInfoEntity.setStatus(1);
        if (type != null) {
            taskInfoEntity.setType(type);
        }
        boolean isExists = false;
        try {
            isExists = scheduler.checkExists(ScheduleUtils.getJobKey(taskId + ""));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        if (isExists) {
            ScheduleUtils.updateScheduleJob(scheduler, taskInfoEntity);
            ScheduleUtils.resumeJob(scheduler, taskId + "");
        } else {
            ScheduleUtils.createScheduleJob(scheduler, taskInfoEntity);
        }
        updateById(taskInfoEntity);
        modifyAfter(JSONUtil.parseObj(taskInfoEntity), taskInfoEntity);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(JSONObject requestParams, TaskInfoEntity scheduleJob) {
        super.add(scheduleJob);
        scheduleJob.setJobId(scheduleJob.getId() + "");

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        updateById(scheduleJob);
        super.modifyAfter(requestParams, scheduleJob);
        return scheduleJob.getId();
    }

    @Override
    public void update(JSONObject requestParams, TaskInfoEntity entity) {
        updateById(entity);
        if (entity.getStatus() == 1) {
            start(entity.getId(), entity.getType());
        } else {
            stop(entity.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(JSONObject requestParams, Long... ids) {
        Convert.toList(String.class, ids).forEach(jobId -> {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        });
        super.delete(requestParams, ids);
    }
}
