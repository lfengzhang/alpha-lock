package net.h2so.alphalock.core;

import net.h2so.alphalock.model.LockInfo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Description ReentrantLock
 * @Auther mikicomo
 * @Date 2019-05-31 16:03
 */
public class ReentrantLock implements Lock {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RLock rLock;

    private final LockInfo lockInfo;

    private RedissonClient redissonClient;

    public ReentrantLock(RedissonClient redissonClient, LockInfo lockInfo) {
        this.redissonClient = redissonClient;
        this.lockInfo = lockInfo;
    }

    /**
     * 获得锁
     *
     * @return
     */
    @Override
    public boolean acquire() {
        try {
            rLock = redissonClient.getLock(lockInfo.getName());
            return rLock.tryLock(lockInfo.getMaxWaitTime(), lockInfo.getLeaseTime(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("acquire ReentrantLock fail ... msg: {}, lockName:{}", e.getCause(), lockInfo.getName());
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
        if (rLock.isHeldByCurrentThread()) {
            try {
                return rLock.forceUnlockAsync().get();
            } catch (InterruptedException e) {
                logger.error("release ReentrantLock fail ... msg: {}, lockName:{}", e.getCause(), lockInfo.getName());
                return false;
            } catch (ExecutionException e) {
                logger.error("release ReentrantLock fail ... msg: {}, lockName:{}", e.getCause(), lockInfo.getName());
                return false;
            }
        }
        return false;
    }
}
