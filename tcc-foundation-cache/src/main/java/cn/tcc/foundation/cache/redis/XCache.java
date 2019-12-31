package cn.tcc.foundation.cache.redis;

import java.util.List;
import java.util.Map;

public interface XCache<TIn, TOut> {

    Map<TIn, TOut> getFromCache(List<TIn> items, XCacheCallback<TIn, TOut> callback);

    Map<TIn, TOut> getFromCache(List<TIn> items, XCacheCallback<TIn, TOut> callback, long ttl);

    boolean removeFromCache(List<TIn> items);

}
