package net.h2so.alphalock.core;

/**
 * @Description Lock 接口
 * @Auther mikicomo
 * @Date 2019-05-17 17:55
 */
public interface Lock {

    /**
     * 获得锁
     * @return
     */
    boolean acquire();

    /**
     * 释放锁
     * @return
     */
    boolean release();
}
