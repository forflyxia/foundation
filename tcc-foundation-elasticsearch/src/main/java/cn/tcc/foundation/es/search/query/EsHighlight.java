package cn.tcc.foundation.es.search.query;

import cn.tcc.foundation.es.search.EsCallback;


public interface EsHighlight<T> extends EsAggregation<T> {

    EsHighlight<T> highlight(EsCallback<T>... c);
}
