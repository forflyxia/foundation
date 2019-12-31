package cn.tcc.foundation.es.search.reponse;

import java.util.Map;

public class EsBulkResponse<T> implements EsResponse<T> {

    private long tookInMillis;
    private Map<String, T> successItemsMap;

    @Override
    public long getTookInMillis() {
        return this.tookInMillis;
    }

    @Override
    public void setTookInMillis(long tookInMillis) {
        this.tookInMillis = tookInMillis;
    }

    public Map<String, T> getSuccessItemsMap() {
        return successItemsMap;
    }

    public void setSuccessItemsMap(Map<String, T> successItemsMap) {
        this.successItemsMap = successItemsMap;
    }
}
