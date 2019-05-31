package net.h2so.alphalock.core;

import net.h2so.alphalock.model.LockInfo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @Description FairLock
 * @Auther mikicomo
 * @Date 2019-05-31 16:03
 */
public class FairLock implements Lock {

    private RLock rLock;

    private final LockInfo lockInfo;

    private RedissonClient redissonClient;

    public FairLock(RedissonClient redissonClient, LockInfo info) {
        this.redissonClient = redissonClient;
        this.lockInfo = info;
    }

    /**
     * 获得锁
     *
     * @return
     */
    @Override
    public boolean acquire() {
        return false;
    }

    /**
     * 释放锁
     *
     * @return
     */
    @Override
    public boolean release() {
        return false;
    }
}
