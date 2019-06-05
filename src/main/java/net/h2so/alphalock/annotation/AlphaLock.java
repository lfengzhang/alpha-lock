package net.h2so.alphalock.annotation;

import net.h2so.alphalock.enums.LockTimeOutStrategy;
import net.h2so.alphalock.enums.LockType;
import net.h2so.alphalock.enums.ReleaseTimeOutStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description AlphaLock 加锁注解
 * @Auther mikicomo
 * @Date 2019-05-17 16:31
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AlphaLock {

    /**
     * 锁的名称
     *
     * @return
     */
    String name() default "";

    /**
     * 锁类型-默认可重入锁
     *
     * @return lockType
     */
    LockType lockType() default LockType.Reentrant;

    /**
     * 加锁最长等待时间
     *
     * @return
     */
    long maxWaitTime() default Long.MIN_VALUE;

    /**
     * 租约时长
     *
     * @return leaseTime
     */
    long leaseTime() default Long.MIN_VALUE;

    /**
     * 释放锁超时策略
     *
     * @return
     */
    ReleaseTimeOutStrategy releaseTimeOutStrategy() default ReleaseTimeOutStrategy.IGNORE;

    /**
     * 加锁超时策略
     *
     * @return
     */
    LockTimeOutStrategy lockTimeOutStrategy() default LockTimeOutStrategy.IGNORE;

    /**
     * 业务key值
     *
     * @return
     */
    String[] keys() default {};
}
