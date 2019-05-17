package net.h2so.alphalock.handler;

import net.h2so.alphalock.core.Lock;
import net.h2so.alphalock.model.LockInfo;
import org.aspectj.lang.JoinPoint;

/**
 * @Description 释放锁超时处理
 * @Auther mikicomo
 * @Date 2019-05-17 17:52
 */
public interface ReleaseTimeOutHandler {

    void handle(LockInfo lockInfo, Lock lock, JoinPoint joinPoint);

}
