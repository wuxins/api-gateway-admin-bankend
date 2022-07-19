package com.cooljs.modules.demo.service.impl;

import com.cooljs.core.cache.CoolCache;
import com.cooljs.modules.demo.service.DemoCacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@CacheConfig(cacheNames = {"sys"})
@Service
public class DemoCacheServiceImpl implements DemoCacheService {
    @Resource
    CoolCache coolCache;

    @Cacheable(key = "targetClass + methodName +#p0")
    @Override
    public Object test(String id) {
        coolCache.set("a", 1);
        System.out.println(coolCache.get("a", Integer.class));
        System.out.println("我被调用了");
        return "我被缓存了";
    }
}
