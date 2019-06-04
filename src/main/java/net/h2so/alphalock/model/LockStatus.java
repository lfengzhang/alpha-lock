package net.h2so.alphalock.model;

/**
 * @Description 锁状态
 * @Auther mikicomo
 * @Date 2019-06-04 14:06
 */
public class LockStatus {

    private LockInfo lockInfo;

    /**
     * 是否持有锁
     */
    private Boolean hold;

    public LockStatus(LockInfo lockInfo, Boolean hold) {
        this.lockInfo = lockInfo;
        this.hold = hold;
    }

    public LockInfo getLockInfo() {
        return lockInfo;
    }

    public void setLockInfo(LockInfo lockInfo) {
        this.lockInfo = lockInfo;
    }

    public Boolean getHold() {
        return hold;
    }

    public void setHold(Boolean hold) {
        this.hold = hold;
    }
}
