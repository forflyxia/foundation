package cn.tcc.foundation.es.search;

import cn.tcc.foundation.es.search.reponse.EsResponse;

public interface EsBuilder<T> {

    String getClusterName();

    void setClusterName(String clusterName);

    String getIndexName();

    void setIndexName(String indexName);

    String getTypeName();

    void setTypeName(String typeName);

    EsResponse<T> builder(Class<?> clazz);
}
