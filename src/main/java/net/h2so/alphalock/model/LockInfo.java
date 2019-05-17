package net.h2so.alphalock.model;

import net.h2so.alphalock.enums.LockType;

/**
 * @Description 锁信息
 * @Auther mikicomo
 * @Date 2019-05-17 17:59
 */
public class LockInfo {

    /**
     * 锁类型
     */
    private LockType type;

    /**
     * 锁名称
     */
    private String name;

    /**
     * 加锁等待时间
     */
    private long maxWaitTime;

    /**
     * 租约时长
     */
    private long leaseTime;

    public LockInfo() {
    }

    public LockInfo(LockType type, String name, long maxWaitTime, long leaseTime) {
        this.type = type;
        this.name = name;
        this.maxWaitTime = maxWaitTime;
        this.leaseTime = leaseTime;
    }

    public LockType getType() {
        return type;
    }

    public void setType(LockType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public long getLeaseTime() {
        return leaseTime;
    }

    public void setLeaseTime(long leaseTime) {
        this.leaseTime = leaseTime;
    }

    @Override
    public String toString() {
        return "LockInfo{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", maxWaitTime=" + maxWaitTime +
                ", leaseTime=" + leaseTime +
                '}';
    }
}
