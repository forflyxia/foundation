package cn.tcc.foundation.cache.redis.client;

import cn.tcc.foundation.cache.redis.XAccessLimit;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class AccessLimitClient extends BaseRedisClient implements XAccessLimit {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccessLimitClient.class.getTypeName());

    private String clusterName;
    private Integer interval;        // 限流间隔时间
    private long threshold;         // 间隔时间内允许通过的令牌数

    /**
     * @param clusterName
     * @param interval    unit seconds
     */
    public AccessLimitClient(String clusterName, Integer interval) {
        this.clusterName = clusterName;
        this.interval = interval;
        this.threshold = interval * 10;
    }

    public AccessLimitClient(String clusterName, Integer interval, long threshold) {
        this(clusterName, interval);
        this.threshold = threshold;
    }

    @Override
    public boolean fixLimit(String key) {
        RedissonClient client = getClient(clusterName);
        if (client == null) {
            return true;
        }

        RAtomicLong rAtomicLong = client.getAtomicLong(key);
        if (rAtomicLong.get() == 0) {
            rAtomicLong.expire(interval, TimeUnit.SECONDS);
        }
        long currentValue = rAtomicLong.incrementAndGet();
        // 超出阈值
        return currentValue <= threshold;
    }

    @Override
    public boolean smoothLimit(String key) {
        RedissonClient client = getClient(clusterName);
        if (client == null) {
            return true;
        }

        RRateLimiter rateLimiter = client.getRateLimiter(key);
        // 最大流速 = 每interval秒钟产生threshold个令牌
        rateLimiter.trySetRate(RateType.OVERALL, threshold, interval, RateIntervalUnit.SECONDS);
        // 获取1个令牌
        return rateLimiter.tryAcquire(1);
    }
}
