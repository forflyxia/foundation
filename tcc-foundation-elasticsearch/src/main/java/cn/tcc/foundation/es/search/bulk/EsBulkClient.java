package cn.tcc.foundation.es.search.bulk;

import cn.tcc.foundation.es.search.EsEntity;
import cn.tcc.foundation.es.search.EsExecutor;
import cn.tcc.foundation.es.search.bulk.builder.EsBulkBuilder;
import cn.tcc.foundation.es.search.bulk.builder.EsBulkBuilderImpl;
import cn.tcc.foundation.es.search.reponse.EsBulkResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsBulkClient<T> implements EsBulkFrom<T>, EsBulkSource<T> {

    private EsBulkBuilder<T> bulkBuilder = new EsBulkBuilderImpl<>();

    public EsBulkClient(String clusterName) {
        bulkBuilder.setClusterName(clusterName);
    }

    @Override
    public EsBulkSource<T> from(String index, String type) {
        bulkBuilder.setIndexName(index);
        bulkBuilder.setTypeName(type);
        return this;
    }

    @Override
    public EsExecutor<T> source(Map<String, T> map) {
        bulkBuilder.setItemsMap(map);
        return this;
    }

    @Override
    public EsExecutor<T> source(List<T> items) {
        Map<String, T> map = new HashMap<>();
        for (T item : items) {
            if (EsEntity.class.isAssignableFrom(item.getClass())) {
                EsEntity entity = (EsEntity) item;
                map.put(entity.getKey().toString(), item);
            }
        }
        return source(map);
    }

    @Override
    public EsBulkResponse<T> execute(Class<?> clazz) throws Exception {
        return (EsBulkResponse<T>) bulkBuilder.builder(clazz);
    }
}
