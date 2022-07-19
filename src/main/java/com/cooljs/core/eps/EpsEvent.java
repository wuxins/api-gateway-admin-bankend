package com.cooljs.core.eps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 事件监听
 */
@Slf4j
@Component
@Profile({"local"})
public class EpsEvent {
    @Resource
    private CoolEps coolEps;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        coolEps.init();
        log.info("构建eps信息");
    }
}
