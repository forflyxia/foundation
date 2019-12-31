package cn.tcc.foundation.es.search.reponse.aggregation;

import java.util.List;

public class AggregationItem {

    private List<AggregationItemBucket> buckets;
    private boolean keyed;
    private String name;


    public List<AggregationItemBucket> getBuckets() {
        return buckets;
    }

    public void setBuckets(List<AggregationItemBucket> buckets) {
        this.buckets = buckets;
    }

    public boolean isKeyed() {
        return keyed;
    }

    public void setKeyed(boolean keyed) {
        this.keyed = keyed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
