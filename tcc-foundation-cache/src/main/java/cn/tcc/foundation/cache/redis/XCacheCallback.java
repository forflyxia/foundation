package cn.tcc.foundation.cache.redis;

import java.util.List;
import java.util.Map;

public interface XCacheCallback<TIn, TOut> {

    Map<TIn, TOut> get(List<TIn> items);

}
