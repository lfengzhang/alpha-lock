package net.h2so.alphalock.core;

import net.h2so.alphalock.model.LockInfo;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Description WriteLock
 * @Auther mikicomo
 * @Date 2019-05-31 16:03
 */
public class WriteLock implements Lock {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RReadWriteLock rLock;

    private final LockInfo lockInfo;

    private RedissonClient redissonClient;

    public WriteLock(RedissonClient redissonClient, LockInfo info) {
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
        try {
            rLock = redissonClient.getReadWriteLock(lockInfo.getName());
            return rLock.writeLock().tryLock(lockInfo.getMaxWaitTime(), lockInfo.getLeaseTime(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("acquire WriteLock fail ... msg: {}, lockName:{}", e.getCause(), lockInfo.getName());
            return false;
        }
    }

    /**
     * 释放锁
     *
     * @return
     */
    @Override
    public boolean release() {
        if (rLock.writeLock().isHeldByCurrentThread()) {
            try {
                return rLock.writeLock().forceUnlockAsync().get();
            } catch (InterruptedException e) {
                logger.error("release WriteLock fail ... msg: {}, lockName:{}", e.getCause(), lockInfo.getName());
                return false;
            } catch (ExecutionException e) {
                logger.error("release WriteLock fail ... msg: {}, lockName:{}", e.getCause(), lockInfo.getName());
                return false;
            }
        }
        return false;
    }
}
