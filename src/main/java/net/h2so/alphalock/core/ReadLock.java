package net.h2so.alphalock.core;

import net.h2so.alphalock.model.LockInfo;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Description ReadLock
 * @Auther mikicomo
 * @Date 2019-05-31 16:03
 */
public class ReadLock implements Lock {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RReadWriteLock rLock;

    private final LockInfo lockInfo;

    private RedissonClient redissonClient;

    public ReadLock(RedissonClient redissonClient, LockInfo info) {
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
            return rLock.readLock().tryLock(lockInfo.getMaxWaitTime(), lockInfo.getLeaseTime(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("acquire ReadLock fail ... msg: {}, lockName:{}", e.getCause(), lockInfo.getName());
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
        if (rLock.readLock().isHeldByCurrentThread()) {
            try {
                return rLock.readLock().forceUnlockAsync().get();
            } catch (InterruptedException e) {
                logger.error("release ReadLock fail ... msg: {}, lockName:{}", e.getCause(), lockInfo.getName());
                return false;
            } catch (ExecutionException e) {
                logger.error("release ReadLock fail ... msg: {}, lockName:{}", e.getCause(), lockInfo.getName());
                return false;
            }
        }
        return false;
    }
}
