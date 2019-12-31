package cn.tcc.foundation.cache.redis;

import java.util.Date;

public class CacheEntry<T> {

    private T value;

    private Date cacheTime;

    public CacheEntry() {

    }

    public CacheEntry(T value, Date cacheTime) {
        this.value = value;
        this.cacheTime = cacheTime;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Date getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(Date cacheTime) {
        this.cacheTime = cacheTime;
    }
}
