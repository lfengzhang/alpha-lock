package net.h2so.alphalock.aspect;

import net.h2so.alphalock.annotation.AlphaLock;
import net.h2so.alphalock.core.Lock;
import net.h2so.alphalock.core.LockFactory;
import net.h2so.alphalock.core.LockInfoAnalyseHandler;
import net.h2so.alphalock.exception.AlphaLockTimeOutException;
import net.h2so.alphalock.model.LockInfo;
import net.h2so.alphalock.model.LockStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 切面加锁处理类
 * @Auther mikicomo
 * @Date 2019-05-31 17:00
 */
@Aspect
public class LockAspectHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ThreadLocal<Lock> currentThreadLock = new ThreadLocal<>();
    private ThreadLocal<LockStatus> currentThreadLockStat = new ThreadLocal<>();

    private LockInfoAnalyseHandler lockInfoAnalyseHandler = new LockInfoAnalyseHandler();

    private LockFactory lockFactory = new LockFactory();

    /**
     * 执行前置处理流
     *
     * @param joinPoint
     * @param alphaLock
     * @return
     * @throws Throwable
     */
    @Before(value = "@annotation(alphaLock)")
    public Object before(ProceedingJoinPoint joinPoint, AlphaLock alphaLock) throws Throwable {
        logger.debug("Aspect moment => before ... ");
        LockInfo lockInfo = lockInfoAnalyseHandler.get(joinPoint, alphaLock);
        currentThreadLockStat.set(new LockStatus(lockInfo, false));
        Lock lock = lockFactory.getLock(lockInfo);
        boolean lockRes = lock.acquire();

        if (!lockRes) {
            logger.warn("Timeout while acquiring Lock({})", lockInfo.getName());
            //todo 暂时做抛出异常处理, 后续增加超时处理策略
            throw new AlphaLockTimeOutException("Timeout while acquiring Lock");
        }

        currentThreadLock.set(lock);
        currentThreadLockStat.get().setHold(true);
        return joinPoint.proceed();
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
        LockStatus lockStat = currentThreadLockStat.get();
        if (lockStat.getHold()) {
            boolean releaseRes = currentThreadLock.get().release();
            lockStat.setHold(false);
            if (!releaseRes) {
                logger.error("Aspect action => releaseLock fail");
                /*todo 释放锁超时处理机制处理, 暂时不做处理*/
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
