package cn.tcc.foundation.es.search.query;

import cn.tcc.foundation.es.search.EsExecutor;

public interface EsPaging<T> extends EsExecutor<T> {

    EsExecutor<T> paging(Integer pageIndex, Integer pageSize);
}
