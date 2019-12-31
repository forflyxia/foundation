package cn.tcc.foundation.es.search.query;

import cn.tcc.foundation.es.search.EsCallback;


public interface EsQuery<T> extends CombineQuery<T> {

    /* must */
    <TValue> EsQuery<T> mustMatch(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> mustTerm(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> mustTerm(EsCallback<T> c, TValue value, boolean iskeyword);

    <TValue> EsQuery<T> mustRange(EsCallback<T> c, TValue from, TValue to);

    <TValue> EsQuery<T> mustRangeGTE(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> mustRangeLTE(EsCallback<T> c, TValue value);


    /* must not */
    <TValue> EsQuery<T> mustNotMatch(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> mustNotTerm(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> mustNotTerm(EsCallback<T> c, TValue value, boolean iskeyword);

    <TValue> EsQuery<T> mustNotRange(EsCallback<T> c, TValue from, TValue to);

    <TValue> EsQuery<T> mustNotRangeGTE(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> mustNotRangeLTE(EsCallback<T> c, TValue value);


    /* should */
    <TValue> EsQuery<T> shouldMatch(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> shouldTerm(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> shouldTerm(EsCallback<T> c, TValue value, boolean iskeyword);

    <TValue> EsQuery<T> shouldRange(EsCallback<T> c, TValue from, TValue to);

    <TValue> EsQuery<T> shouldRangeGTE(EsCallback<T> c, TValue value);

    <TValue> EsQuery<T> shouldRangeLTE(EsCallback<T> c, TValue value);
}
