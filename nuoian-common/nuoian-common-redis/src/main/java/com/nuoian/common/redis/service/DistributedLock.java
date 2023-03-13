package com.nuoian.common.redis.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author :吴宇森
 * @Date :2020/08/21 09:46
 * @Description :基于redis实现分布式锁
 * @Package :com.nuoian.common.redis.service
 * @Version : 1.0
 */
@Component
public class DistributedLock {

    private final StringRedisTemplate redisTemplate;

    public DistributedLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 功能描述:
     * 〈获取Redis分布式锁〉
     *
     * @param lockKey  分布式锁KEY
     * @param lockTime 分布式锁超时时间 单位：毫秒
     * @return : boolean
     * @author : 吴宇森
     * @date : 2020/08/21 09:48
     */
    public boolean getDistributedLock(String lockKey, Long lockTime) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, "LOCK", lockTime, TimeUnit.MILLISECONDS);
        return flag != null && flag;
    }

    /**
     * 功能描述:
     * 〈释放Redis分布式锁〉
     *
     * @param lockKey 分布式锁KEY
     * @return : void
     * @author : 吴宇森
     * @date : 2020/08/21 09:53
     */
    public void freedDistributedLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}
