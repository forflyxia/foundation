package cn.tcc.foundation.cache.redis.cluster;

import java.util.concurrent.ConcurrentHashMap;

public class RedisClusterProvider {

    private static final ConcurrentHashMap<String, RedisClusterConfig> REDIS_CONFIG_MAP = new ConcurrentHashMap<>();

    public static RedisClusterConfig get(String clusterName) {
        RedisClusterConfig config = REDIS_CONFIG_MAP.get(clusterName);
        return config;
    }

    public static void set(String clusterName, RedisClusterConfig config) throws Exception {
        if (config.isEnable()) {
            REDIS_CONFIG_MAP.putIfAbsent(clusterName, config);
        }
    }
}
