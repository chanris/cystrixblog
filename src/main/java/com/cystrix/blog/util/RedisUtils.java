package com.cystrix.blog.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
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
     * 获得存活时间 单位秒
     * @param key
     * @return
     */
    public Long getExpireTime(String key) {
        return redisTemplate.getExpire(key);
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
     * key不存在返回null
     */
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     *
     * @param itf 接口标记
     * @param actionKey 行为标记
     * @param period 时间段（单位秒）
     * @param maxCount 最大请求数（规定时间段内）
     * @return boolean
     */
    public boolean isActionAllowed(String itf, String actionKey, int period, int maxCount) {
        String key = String.format("ratelimit:%s:%s", itf, actionKey);
        long currentTimestamp = Instant.now().toEpochMilli();
        long periodTimestamp = currentTimestamp - TimeUnit.SECONDS.toMillis(period);
        try {
            // 删除当前滑动窗口之外的请求记录
            redisTemplate.opsForZSet().removeRangeByScore(key, 0, periodTimestamp);
            Long count = redisTemplate.opsForZSet().count(key, periodTimestamp, currentTimestamp);
            if (count != null && count >= maxCount) {
                return false;
            }
            redisTemplate.opsForZSet().add(key, String.valueOf(currentTimestamp), currentTimestamp);
            // 在这里设置过期时间，因为如果在时间窗口内没有新的请求，则key没有必要保留太久
            redisTemplate.expire(key, period, TimeUnit.SECONDS);
        } catch (Exception e) {
            // 这里应该处理异常，比如记录日志或其他错误处理机制
            return false;
        }
        return true;
    }

}
