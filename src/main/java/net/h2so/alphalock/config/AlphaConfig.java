package net.h2so.alphalock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description 默认配置
 * @Auther mikicomo
 * @Date 2019-06-04 14:57
 */
@Component
public class AlphaConfig {
    /**
     * redis config
     */
    @Value("${alphaLock.address:#{null}}")
    /*redis地址(单机)*/
    private String address;

    @Value("${alphaLock.password:#{null}}")
    /*redis密码*/
    private String password;

    @Value("${alphaLock.clusterNodeAddress:#{null}}")
    /*redis地址(集群)*/
    private String[] clusterNodeAddress;

    @Value("${alphaLock.database:15}")
    /*数据库分片数量*/
    private Integer database;

    @Value("${alphaLock.redis.codec:org.redisson.codec.JsonJacksonCodec}")
    /*默认编码*/
    private String codec;

    /**
     * lock config
     */
    @Value("${alphaLock.maxWaitTime:60}")
    private long maxWaitTime;

    @Value("${alphaLock.leaseTime:60}")
    private long leaseTime;

    public AlphaConfig() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getClusterNodeAddress() {
        return clusterNodeAddress;
    }

    public void setClusterNodeAddress(String[] clusterNodeAddress) {
        this.clusterNodeAddress = clusterNodeAddress;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
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
}
