package cn.tcc.foundation.es.search.query;

import cn.tcc.foundation.es.search.EsCallback;

public interface EsFilter<T> extends EsHighlight<T>{

    <TValue> EsQuery<T> filterTerm(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> filterRange(EsCallback<T> c, TValue from, TValue to);

    <TValue> EsQuery<T> filterRangeGTE(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> filterRangeLTE(EsCallback<T> c, TValue value);
}
