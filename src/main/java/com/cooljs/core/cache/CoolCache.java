package com.cooljs.core.cache;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.cooljs.core.util.ConvertUtil;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.Arrays;

/**
 * 缓存工具类
 */
@EnableCaching
@Configuration
@Component
public class CoolCache {

    // 过期时间(单位： 秒)
    @Value("${spring.cache.type}")
    private String type;

    // ehcache
    public Ehcache ehcache;

    // redis
    public RedisCacheWriter redisCache;

    private Cache cache;

    private final String cacheName = "comm";

    @Resource
    private CacheManager cacheManager;


    @PostConstruct
    private void init() {
        cache = cacheManager.getCache(cacheName);
        this.type = type.toLowerCase();
        assert cache != null;
        if (type.equals("ehcache")) {
            ehcache = (Ehcache) cache.getNativeCache();
        }
        if (type.equals("redis")) {
            redisCache = (RedisCacheWriter) cache.getNativeCache();
        }
    }

    /**
     * 删除缓存
     *
     * @param keys 一个或多个key
     */
    public void del(String... keys) {
        if (type.equals("ehcache")) {
            Arrays.stream(keys).forEach(ehcache::remove);
        }
        if (type.equals("redis")) {
            Arrays.stream(keys).forEach(key -> {
                redisCache.remove(cacheName, key.getBytes());
            });
        }
    }


    /**
     * 普通缓存获取
     *
     * @param key 键
     */
    public Object get(String key) {
        if (type.equals("ehcache")) {
            Element element = ehcache.get(key);
            return element != null ? element.getObjectValue() : null;
        }
        if (type.equals("redis")) {
            byte[] bytes = redisCache.get(cacheName, key.getBytes());
            if (bytes != null && bytes.length > 0) {
                return ConvertUtil.toObject(bytes);
            }
        }
        return null;
    }


    /**
     * 获得对象
     *
     * @param key       键
     * @param valueType 值类型
     */
    public <T> T get(String key, Class<T> valueType) {
        Object result = get(key);
        if (result != null && JSONUtil.isJsonObj(result.toString())) {
            return JSONUtil.parseObj(result).toBean(valueType);
        }
        return result != null ? (T) result : null;
    }


    /**
     * 获得缓存类型
     */
    public String getMode() {
        return this.type;
    }

    /**
     * 获得原生缓存实例
     */
    public Object getMetaCache() {
        return this.cache;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        set(key, value, 0);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param ttl   时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long ttl) {
        if (type.equals("ehcache")) {
            Element element = new Element(key, value);
            if (ttl > 0) {
                element.setTimeToLive(Integer.parseInt(ttl + ""));
            }
            ehcache.put(element);
            ehcache.flush();
        }
        if (type.equals("redis")) {
            redisCache.put(cacheName, key.getBytes(), ObjectUtil.serialize(value), Duration.ofSeconds(ttl));
        }
    }

}
