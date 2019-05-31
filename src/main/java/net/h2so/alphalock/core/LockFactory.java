package net.h2so.alphalock.core;

import net.h2so.alphalock.model.LockInfo;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description Lock 工厂类
 * @Auther mikicomo
 * @Date 2019-05-31 16:03
 */
public class LockFactory {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RedissonClient redissonClient = Redisson.create();

    /**
     * 获取锁
     *
     * @param lockInfo
     * @return
     */
    public Lock getLock(LockInfo lockInfo) {

        logger.debug("start getLock... lock-type:[{}], lock-name:[{}] ", lockInfo.getType(), lockInfo.getName());

        switch (lockInfo.getType()) {
            case Read:
                return new ReadLock(redissonClient, lockInfo);
            case Write:
                return new WriteLock(redissonClient, lockInfo);
            case Fair:
                return new FairLock(redissonClient, lockInfo);
            default:
                return new ReentrantLock(redissonClient, lockInfo);
        }
    }
}
