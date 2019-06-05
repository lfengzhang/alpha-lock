package net.h2so.alphalock.aspect;

import net.h2so.alphalock.annotation.AlphaLock;
import net.h2so.alphalock.core.Lock;
import net.h2so.alphalock.core.LockFactory;
import net.h2so.alphalock.core.LockInfoAnalyseHandler;
import net.h2so.alphalock.model.LockInfo;
import net.h2so.alphalock.model.LockStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 切面加锁处理类
 * @Auther mikicomo
 * @Date 2019-05-31 17:00
 */
@Component
@Aspect
public class LockAspectHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ThreadLocal<Lock> currentThreadLock = new ThreadLocal<>();
    private ThreadLocal<LockStatus> currentThreadLockStat = new ThreadLocal<>();

    @Autowired
    private LockInfoAnalyseHandler lockInfoAnalyseHandler;

    @Autowired
    private LockFactory lockFactory;

    /**
     * 执行前置处理流
     *
     * @param joinPoint
     * @param alphaLock
     * @return
     * @throws Throwable
     */
    @Before(value = "@annotation(alphaLock)")
    public void before(JoinPoint joinPoint, AlphaLock alphaLock) throws Throwable {
        logger.debug("Aspect moment => before ... ");
        LockInfo lockInfo = lockInfoAnalyseHandler.get(joinPoint, alphaLock);
        currentThreadLockStat.set(new LockStatus(lockInfo, false));
        Lock lock = lockFactory.getLock(lockInfo);
        boolean lockRes = lock.acquire();

        if (!lockRes) {
            logger.warn("Timeout while acquiring Lock({})", lockInfo.getName());
            //执行指定的策略
            alphaLock.lockTimeOutStrategy().handle(lockInfo, lock, joinPoint);
        }

        currentThreadLock.set(lock);
        currentThreadLockStat.get().setHold(true);
    }

    /**
     * 执行完毕后finally处理流
     *
     * @param joinPoint
     * @param alphaLock
     * @throws Throwable
     */
    @AfterReturning(value = "@annotation(alphaLock)")
    public void afterReturning(JoinPoint joinPoint, AlphaLock alphaLock) throws Throwable {
        logger.debug("Aspect moment => afterReturning ... ");

        /**
         * 1、释放锁
         * 2、清理线程
         */
        releaseLock(alphaLock, joinPoint);
        cleanUpThread();
    }

    /**
     * 异常流处理
     *
     * @param joinPoint
     * @param alphaLock
     * @param ex
     * @throws Throwable
     */
    @AfterThrowing(value = "@annotation(alphaLock)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, AlphaLock alphaLock, Throwable ex) throws Throwable {
        logger.debug("Aspect moment => afterThrowing ... cause:{}", ex.getCause());

        /**
         * 1、释放锁
         * 2、清理线程
         * 3、抛出异常
         */
        releaseLock(alphaLock, joinPoint);
        cleanUpThread();
        throw ex;
    }

    /**
     * 释放锁
     *
     * @param alphaLock
     * @param joinPoint
     * @throws Throwable
     */
    private void releaseLock(AlphaLock alphaLock, JoinPoint joinPoint) throws Throwable {
        logger.debug("Aspect action => releaseLock");

        LockInfo lockInfo = lockInfoAnalyseHandler.get(joinPoint, alphaLock);
        Lock lock = lockFactory.getLock(lockInfo);

        LockStatus lockStat = currentThreadLockStat.get();
        if (lockStat.getHold()) {
            boolean releaseRes = currentThreadLock.get().release();
            lockStat.setHold(false);
            if (!releaseRes) {
                logger.error("Aspect action => releaseLock fail");
                //执行指定的策略
                alphaLock.releaseTimeOutStrategy().handle(lockInfo, lock, joinPoint);
            }
        }
    }

    /**
     * 清理现场线程
     */
    private void cleanUpThread() {
        currentThreadLockStat.remove();
        currentThreadLock.remove();
    }

}
