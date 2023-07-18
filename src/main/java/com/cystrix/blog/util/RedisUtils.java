package com.cystrix.blog.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
@Component
public class RedisUtils {

    private final StringRedisTemplate redisTemplate;

    public RedisUtils(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置过期时间,单位秒
     */
    public void setExpireTime(String key, long second) {
        redisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     */
    public boolean isExistedKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 根据key删除redis中的缓存
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置key-value
     */
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获得key的值
     */
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
