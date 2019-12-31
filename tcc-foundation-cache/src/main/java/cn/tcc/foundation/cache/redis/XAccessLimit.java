package cn.tcc.foundation.cache.redis;

public interface XAccessLimit {

    boolean fixLimit(String key);

    boolean smoothLimit(String key);
}
