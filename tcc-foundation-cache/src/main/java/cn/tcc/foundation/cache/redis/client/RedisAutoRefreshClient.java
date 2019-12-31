package cn.tcc.foundation.cache.redis.client;

import cn.tcc.foundation.cache.redis.CacheEntry;
import cn.tcc.foundation.cache.redis.XCacheCallback;
import cn.tcc.foundation.core.convert.DateTimeTo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RedisAutoRefreshClient<TIn, TOut> extends RedisClient<TIn, TOut> {

    private long interval;

    public RedisAutoRefreshClient(String clusterName, String partitionName, long cacheInterval) {
        super(clusterName, partitionName);
        this.interval = cacheInterval;
    }

    @Override
    protected void refresh(Map<TIn, CacheEntry<TOut>> existedMap, XCacheCallback<TIn, TOut> callback) {
        Set<TIn> expiredKeys = new HashSet<>();
        for (Map.Entry<TIn, CacheEntry<TOut>> entry : existedMap.entrySet()) {
            LocalDateTime refreshTime = DateTimeTo.toLocalDateTime(entry.getValue().getCacheTime()).plusSeconds(interval);
            if (refreshTime.compareTo(LocalDateTime.now()) <= 0) {
                expiredKeys.add(entry.getKey());
            }
        }
        if (!expiredKeys.isEmpty()) {
            internalSaveToCache(expiredKeys, callback);
        }
    }
}
