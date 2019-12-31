package cn.tcc.foundation.es.search.bulk.builder;

import cn.tcc.foundation.es.search.EsBuilder;

import java.util.Map;

public interface EsBulkBuilder<T> extends EsBuilder<T> {

    Map<String, T> getItemsMap();

    void setItemsMap(Map<String, T> map);
}
