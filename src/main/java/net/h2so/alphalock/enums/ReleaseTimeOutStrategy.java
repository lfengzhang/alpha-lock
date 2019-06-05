package net.h2so.alphalock.enums;

import net.h2so.alphalock.core.Lock;
import net.h2so.alphalock.exception.AlphaLockTimeOutException;
import net.h2so.alphalock.handler.ReleaseTimeOutHandler;
import net.h2so.alphalock.model.LockInfo;
import org.aspectj.lang.JoinPoint;

/**
 * @Description 释放锁超时策略
 * @Auther mikicomo
 * @Date 2019-06-05 17:08
 */
public enum ReleaseTimeOutStrategy implements ReleaseTimeOutHandler {
    /**
     * 忽略不做处理
     */
    IGNORE() {
        @Override
        public void handle(LockInfo lockInfo, Lock lock, JoinPoint joinPoint) {
            //do nothing
        }
    },

    /**
     * 失败, 抛出异常
     */
    FAILURE() {
        @Override
        public void handle(LockInfo lockInfo, Lock lock, JoinPoint joinPoint) {
            String errorMsg = String.format("Failed to Release Lock [%s] with leaseTime %d second", lockInfo.getName(), lockInfo.getLeaseTime());
            throw new AlphaLockTimeOutException(errorMsg);
        }
    };
}
