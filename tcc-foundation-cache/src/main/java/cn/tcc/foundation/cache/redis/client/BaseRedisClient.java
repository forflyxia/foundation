package cn.tcc.foundation.cache.redis.client;

import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseRedisClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaseRedisClient.class.getTypeName());

    protected RedissonClient getClient(String clusterName) {
        RedissonClient client = null;
        try {
            client = RedisClientProvider.getClient(clusterName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return client;
    }
}
