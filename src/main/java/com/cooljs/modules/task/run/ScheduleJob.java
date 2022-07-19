package com.cooljs.modules.task.run;


import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cooljs.core.base.BaseEntity;
import com.cooljs.modules.task.entity.TaskInfoEntity;
import com.cooljs.modules.task.entity.TaskLogEntity;
import com.cooljs.modules.task.service.TaskInfoLogService;
import com.cooljs.modules.task.service.TaskInfoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 定时任务
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        //获取spring bean
        TaskInfoLogService taskInfoLogService = SpringUtil.getBean(TaskInfoLogService.class);
        //获取spring bean
        TaskInfoService taskInfoService = SpringUtil.getBean(TaskInfoService.class);

        Scheduler scheduler = SpringUtil.getBean(Scheduler.class);

        TaskInfoEntity taskInfoEntity = taskInfoService.getById(context.getJobDetail().getKey().getName().split("_")[1]);

        LambdaUpdateWrapper<TaskInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BaseEntity::getId, taskInfoEntity.getId());

        //数据库保存执行记录
        TaskLogEntity taskLogEntity = new TaskLogEntity();


        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            log.info("任务准备执行，任务ID：" + taskInfoEntity.getJobId());

            // 解析执行
            String service = taskInfoEntity.getService();
            String[] arr = service.split("\\.");
            String methodName = arr[1].substring(0, arr[1].indexOf("("));
            String params = service.substring(service.indexOf("(") + 1, service.indexOf(")"));
            taskLogEntity.setTaskId(taskInfoEntity.getId());

            ScheduleRunnable task = new ScheduleRunnable(arr[0].replaceAll(" ", ""),
                    methodName, params);
            Future<?> future = executorService.submit(task);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            //状态 0：失败 1：成功
            taskLogEntity.setStatus(1);
            taskLogEntity.setDetail("任务执行完毕，任务ID：" + taskInfoEntity.getJobId() + "  总共耗时：" + times + "毫秒");
            log.info(taskLogEntity.getDetail());
        } catch (Exception e) {
            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;

            taskLogEntity.setDetail("任务执行失败，任务ID：" + taskInfoEntity.getJobId() + "  总共耗时：" + times + "毫秒" + "失败原因：" + e.getMessage());
            log.error("任务执行失败，任务ID：" + taskInfoEntity.getJobId(), e);

            //状态 0：失败 1：成功
            taskLogEntity.setStatus(0);
        } finally {
            taskInfoLogService.add(taskLogEntity);
        }
        ThreadUtil.execAsync(() -> {
            ThreadUtil.sleep(2000);
            try {
                if (!scheduler.checkExists(context.getTrigger().getJobKey())) {
                    updateWrapper.set(context.getTrigger().getNextFireTime() == null, TaskInfoEntity::getStatus, 0);
                } else {
                    updateWrapper.set(context.getTrigger().getNextFireTime() != null, TaskInfoEntity::getNextRunTime, context.getTrigger().getNextFireTime());
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            taskInfoService.update(updateWrapper);
        });
    }
}
