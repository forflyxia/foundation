package cn.tcc.foundation.es.search.reponse;


import cn.tcc.foundation.es.search.reponse.aggregation.AggregationItem;

import java.util.List;

public class EsQueryResponse<T> implements EsResponse<T> {

    private long tookInMillis;
    private List<AggregationItem> aggregations;
    private Paging paging;
    private List<T> items;

    public List<AggregationItem> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<AggregationItem> aggregations) {
        this.aggregations = aggregations;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public long getTookInMillis() {
        return tookInMillis;
    }

    public void setTookInMillis(long tookInMillis) {
        this.tookInMillis = tookInMillis;
    }
}
