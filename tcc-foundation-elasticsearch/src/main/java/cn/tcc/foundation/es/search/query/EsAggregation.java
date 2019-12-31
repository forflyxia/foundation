package cn.tcc.foundation.es.search.query;

import cn.tcc.foundation.es.search.EsCallback;


public interface EsAggregation<T> extends EsSort<T> {

    <TValue> EsAggregation<T> aggRange(EsCallback<T> c, TValue from, TValue to);

    EsAggregation<T> aggTerm(EsCallback<T>... c);
}
