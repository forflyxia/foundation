package cn.tcc.foundation.es.search.bulk;

import cn.tcc.foundation.es.search.EsExecutor;

import java.util.List;
import java.util.Map;

public interface EsBulkSource<T> extends EsExecutor<T> {

    EsExecutor<T> source(List<T> items);

    EsExecutor<T> source(Map<String, T> map);
}
