package cn.tcc.foundation.es.search.query.builder.executor;

import cn.tcc.foundation.es.search.reponse.aggregation.AggregationItem;
import cn.tcc.foundation.es.search.reponse.aggregation.AggregationItemBucket;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.util.ArrayList;
import java.util.List;

public class EsQueryResponseConvertor {

    public static List<AggregationItem> convert(SearchResponse response) {
        List<AggregationItem> items = new ArrayList<>();
        for (Aggregation aggregation : response.getAggregations().asList()) {
            if (ParsedRange.class.isAssignableFrom(aggregation.getClass())) {
                ParsedRange rangeAggregation = ((ParsedRange) aggregation);
                AggregationItem item = convert(rangeAggregation);
                items.add(item);
            }

            if (ParsedTerms.class.isAssignableFrom(aggregation.getClass())) {
                ParsedTerms termAggregation = ((ParsedTerms) aggregation);
                AggregationItem item = convert(termAggregation);
                items.add(item);
            }
        }
        return items;
    }

    private static AggregationItem convert(ParsedRange aggregation) {
        AggregationItem item = new AggregationItem();
        item.setName(aggregation.getName());
        if (aggregation.getBuckets() != null) {
            item.setBuckets(new ArrayList<>());
            for (Range.Bucket bucket : aggregation.getBuckets()) {
                AggregationItemBucket bucketItem = new AggregationItemBucket();
                bucketItem.setKey(bucket.getKey().toString());
                bucketItem.setFrom(bucket.getFrom().toString());
                bucketItem.setTo(bucket.getTo().toString());
                bucketItem.setDocCount(bucket.getDocCount());
                item.getBuckets().add(bucketItem);
            }
        }
        return item;
    }

    private static AggregationItem convert(ParsedTerms aggregation) {
        AggregationItem item = new AggregationItem();
        item.setName(aggregation.getName());
        if (aggregation.getBuckets() != null) {
            item.setBuckets(new ArrayList<>());
            for (Terms.Bucket bucket : aggregation.getBuckets()) {
                AggregationItemBucket bucketItem = new AggregationItemBucket();
                bucketItem.setKey(bucket.getKey().toString());
                bucketItem.setDocCount(bucket.getDocCount());
                item.getBuckets().add(bucketItem);
            }
        }
        return item;
    }
}
