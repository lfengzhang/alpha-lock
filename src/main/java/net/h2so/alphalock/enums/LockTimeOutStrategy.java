package net.h2so.alphalock.enums;


import net.h2so.alphalock.core.Lock;
import net.h2so.alphalock.exception.AlphaLockTimeOutException;
import net.h2so.alphalock.handler.LockTimeOutHandler;
import net.h2so.alphalock.model.LockInfo;
import org.aspectj.lang.JoinPoint;

/**
 * @Description 加锁超时策略
 * @Auther mikicomo
 * @Date 2019-06-05 17:07
 */
public enum LockTimeOutStrategy implements LockTimeOutHandler {

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
            String errorMsg = String.format("Failed to acquire Lock [%s] with timeout %d second", lockInfo.getName(), lockInfo.getMaxWaitTime());
            throw new AlphaLockTimeOutException(errorMsg);
        }
    },

    /**
     * 重试
     */
    RETRY() {

        private static final int MAX_RETRY_NUM = 3;

        @Override
        public void handle(LockInfo lockInfo, Lock lock, JoinPoint joinPoint) {
            /**
             * 默认重试三次
             * todo 重试次数支持可配置
             */
            int num = 0;

            while (!lock.acquire()) {

                if (MAX_RETRY_NUM < num) {
                    String errorMsg = String.format("ReTry acquire Lock(%s) %d times but fail",
                            lockInfo.getName(), MAX_RETRY_NUM);
                    throw new AlphaLockTimeOutException(errorMsg);
                }
                num++;
            }
        }
    };

}
