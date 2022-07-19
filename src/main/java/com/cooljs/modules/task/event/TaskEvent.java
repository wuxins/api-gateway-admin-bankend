package com.cooljs.modules.task.event;

import com.cooljs.modules.task.service.TaskInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 事件监听
 */
@Slf4j
@Component
public class TaskEvent {
    @Resource
    private TaskInfoService taskInfoService;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        taskInfoService.init();
        log.info("初始化任务");
    }
}
