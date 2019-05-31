package net.h2so.alphalock.enums;

/**
 * @Description 锁类型
 * @Auther mikicomo
 * @Date 2019-05-17 16:38
 */
public enum LockType {

    Reentrant("可重入锁"),

    Read("读锁"),

    Write("写锁"),

    Fair("公平锁");

    String desc;

    LockType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
