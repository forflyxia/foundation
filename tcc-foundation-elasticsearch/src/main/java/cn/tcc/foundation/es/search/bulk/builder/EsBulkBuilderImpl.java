package cn.tcc.foundation.es.search.bulk.builder;

import cn.tcc.foundation.es.search.bulk.builder.executor.EsBulkExecutorImpl;
import cn.tcc.foundation.es.search.reponse.EsResponse;

import java.util.Map;

public class EsBulkBuilderImpl<T> implements EsBulkBuilder<T> {

    private String clusterName;
    private String indexName;
    private String typeName;
    private Map<String, T> itemsMap;

    @Override
    public String getClusterName() {
        return clusterName;
    }

    @Override
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public String getIndexName() {
        return indexName;
    }

    @Override
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public Map<String, T> getItemsMap() {
        return itemsMap;
    }

    @Override
    public void setItemsMap(Map<String, T> map) {
        this.itemsMap = map;
    }

    @Override
    public EsResponse<T> builder(Class<?> clazz) {
        try {
            return new EsBulkExecutorImpl(this).execute(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
