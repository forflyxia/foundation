package cn.tcc.foundation.cache.redis.client;

import cn.tcc.foundation.cache.redis.CacheEntry;
import cn.tcc.foundation.cache.redis.XCache;
import cn.tcc.foundation.cache.redis.XCacheCallback;
import org.redisson.api.RBucket;
import org.redisson.api.RBuckets;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RedisClient<TIn, TOut> extends BaseRedisClient implements XCache<TIn, TOut> {

    private final static Logger LOGGER = LoggerFactory.getLogger("foundation.cache");

    private String clusterName;
    private String partitionName;
    private long defaultTtl = 24 * 60 * 60 * 1000;

    public RedisClient(String clusterName, String partitionName) {
        this.clusterName = clusterName;
        this.partitionName = partitionName;
    }

    public RedisClient(String clusterName, String partitionName, long cacheTtl) {
        this(clusterName, partitionName);
        this.defaultTtl = cacheTtl;
    }

    @Override
    public Map<TIn, TOut> getFromCache(List<TIn> keys, XCacheCallback<TIn, TOut> callback) {
        return getFromCache(keys, callback, this.defaultTtl);
    }

    @Override
    public Map<TIn, TOut> getFromCache(List<TIn> keys, XCacheCallback<TIn, TOut> callback, long ttl) {
        Map<TIn, CacheEntry<TOut>> cachedMap = internalGetFromCache(keys);
        Map<TIn, TOut> existedMap = getExistedMap(cachedMap);
        Set<TIn> existedKeys = existedMap.keySet();
        Set<TIn> nonExistedKeys = keys.stream().filter(t -> !existedKeys.contains(t)).collect(Collectors.toSet());
        Map<TIn, TOut> nonExistedMap = internalSaveToCache(nonExistedKeys, callback, ttl);
        refresh(cachedMap, callback);
        return mergeMap(existedMap, nonExistedMap);
    }

    @Override
    public boolean removeFromCache(List<TIn> items) {
        RedissonClient client = getClient(clusterName);
        if (client == null) {
            return false;
        }

        for (TIn item : items) {
            String key = buildCacheKey(item);
            RBucket<CacheEntry<TOut>> c = client.getBucket(key);
            c.delete();
        }
        return true;
    }

    protected void refresh(Map<TIn, CacheEntry<TOut>> existedMap, XCacheCallback<TIn, TOut> callback) {

    }

    protected Map<TIn, TOut> internalSaveToCache(Set<TIn> nonExistedKeys, XCacheCallback<TIn, TOut> callback) {
        return internalSaveToCache(nonExistedKeys, callback, this.defaultTtl);
    }

    private Map<TIn, TOut> internalSaveToCache(Set<TIn> nonExistedKeys, XCacheCallback<TIn, TOut> callback, long ttl) {
        if (nonExistedKeys.isEmpty()) {
            return new HashMap<>();
        }
        Map<TIn, TOut> nonExistedMap = callback.get(new ArrayList<>(nonExistedKeys));
        if (nonExistedMap.isEmpty()) {
            return new HashMap<>();
        }

        RedissonClient client = getClient(clusterName);
        if (client == null) {
            return nonExistedMap;
        }

        for (Map.Entry<TIn, TOut> entry : nonExistedMap.entrySet()) {

            String key = buildCacheKey(entry.getKey());
            CacheEntry value = new CacheEntry(entry.getValue(), new Date());

            RBucket rBucket = client.getBucket(key);
            rBucket.setAsync(value, ttl, TimeUnit.MILLISECONDS);
        }
        return nonExistedMap;
    }

    private Map<TIn, TOut> getExistedMap(Map<TIn, CacheEntry<TOut>> cachedMap) {
        Map<TIn, TOut> resultMap = new HashMap<>();
        for (Map.Entry<TIn, CacheEntry<TOut>> entry : cachedMap.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue().getValue());
        }
        return resultMap;
    }

    private Map<TIn, CacheEntry<TOut>> internalGetFromCache(List<TIn> keys) {

        String[] stringKeys = new String[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            stringKeys[i] = buildCacheKey(keys.get(i));
        }

        RedissonClient client = getClient(clusterName);
        if (client == null) {
            return new HashMap<>();
        }

        RBuckets rBuckets = client.getBuckets();
        Map<String, CacheEntry<TOut>> cachedMap = rBuckets.get(stringKeys);
        Map<TIn, CacheEntry<TOut>> resultMap = new HashMap<>();
        for (TIn key : keys) {
            String cacheKey = buildCacheKey(key);
            if (cachedMap.containsKey(cacheKey)) {
                resultMap.put(key, cachedMap.get(cacheKey));
            }
        }
        return resultMap;
    }

    private String buildCacheKey(TIn key) {
        return String.format("%s_%s", partitionName, key.toString());
    }

    private Map<TIn, TOut> mergeMap(Map<TIn, TOut> existedMap, Map<TIn, TOut> nonExistedMap) {
        existedMap.putAll(nonExistedMap);
        return existedMap;
    }
}
