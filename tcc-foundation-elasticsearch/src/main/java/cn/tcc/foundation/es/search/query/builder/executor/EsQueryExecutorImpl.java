package cn.tcc.foundation.es.search.query.builder.executor;

import cn.tcc.foundation.core.serialization.GsonSerializer;
import cn.tcc.foundation.es.cluster.EsHighLevelClient;
import cn.tcc.foundation.es.search.EsExecutor;
import cn.tcc.foundation.es.search.query.builder.EsQueryBuilder;
import cn.tcc.foundation.es.search.reponse.EsQueryResponse;
import cn.tcc.foundation.es.search.reponse.Paging;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import java.util.ArrayList;

public class EsQueryExecutorImpl<T> implements EsExecutor<T> {

    private EsQueryBuilder queryBuilder;

    public EsQueryExecutorImpl(EsQueryBuilder builder) {
        queryBuilder = builder;
    }

    @Override
    public EsQueryResponse<T> execute(Class<?> clazz) throws Exception {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if (queryBuilder.getPageIndex() != null) {
            sourceBuilder.from((queryBuilder.getPageIndex() - 1) * queryBuilder.getPageSize());
        }
        if (queryBuilder.getPageSize() != null) {
            sourceBuilder.size(queryBuilder.getPageSize());
        }

        for (Object o : queryBuilder.getMustClauses()) {
            QueryBuilder must = (QueryBuilder)o;
            queryBuilder.getRootBoolQueryBuilder().must(must);
        }
        queryBuilder.getMustClauses().clear();

        for (Object o : queryBuilder.getMustNotClauses()) {
            QueryBuilder mustnot = (QueryBuilder)o;
            queryBuilder.getRootBoolQueryBuilder().mustNot(mustnot);
        }
        queryBuilder.getMustNotClauses().clear();

        for (Object o : queryBuilder.getShouldClauses()) {
            QueryBuilder should = (QueryBuilder)o;
            queryBuilder.getRootBoolQueryBuilder().should(should);
        }
        queryBuilder.getShouldClauses().clear();

        for (Object o : queryBuilder.getFilterClauses()) {
            QueryBuilder filter = (QueryBuilder)o;
            queryBuilder.getRootBoolQueryBuilder().filter(filter);
        }
        queryBuilder.getFilterClauses().clear();

        sourceBuilder.query(queryBuilder.getRootBoolQueryBuilder());
        if (queryBuilder.getHighlightBuilder() != null) {
            sourceBuilder.highlighter(queryBuilder.getHighlightBuilder());
        }
        for (Object o : queryBuilder.getAggregationBuilders()) {
            AggregationBuilder aggregationBuilder = (AggregationBuilder) o;
            sourceBuilder.aggregation(aggregationBuilder);
        }
        for (Object o : queryBuilder.getSortBuilders()) {
            SortBuilder<?> sort = (SortBuilder<?>) o;
            sourceBuilder.sort(sort);
        }

        System.out.println(sourceBuilder.toString());

        SearchRequest request = new SearchRequest();
        request.indices(queryBuilder.getIndexName());
        request.types(queryBuilder.getTypeName());
        request.source(sourceBuilder);
        RestHighLevelClient client = EsHighLevelClient.getClient(queryBuilder.getClusterName());
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        return getSuccessResponse(response, clazz);
    }

    private EsQueryResponse<T> getSuccessResponse(SearchResponse response, Class<?> clazz) {
        EsQueryResponse<T> queryResponse = new EsQueryResponse<>();
        queryResponse.setTookInMillis(response.getTook().millis());
        if (response.getHits() != null) {
            queryResponse.setItems(new ArrayList<>());
            for (SearchHit documentFields : response.getHits()) {
                String source = documentFields.getSourceAsString();
                T item = (T) GsonSerializer.fromJson(source, clazz);
                queryResponse.getItems().add(item);
            }
            queryResponse.setPaging(new Paging());
            queryResponse.getPaging().setTotal(response.getHits().getTotalHits());
            if (queryBuilder.getPageSize() != null) {
                queryResponse.getPaging().setPageSize(queryBuilder.getPageSize());
                long pages = (long)Math.ceil((double)queryResponse.getPaging().getTotal() / queryResponse.getPaging().getPageSize());
                queryResponse.getPaging().setPages(pages);
            }
            if (queryBuilder.getPageIndex() != null) {
                queryResponse.getPaging().setCurrentPageIndex(queryBuilder.getPageIndex());
            }
        }
        if (response.getAggregations() != null && !response.getAggregations().asList().isEmpty()) {
            queryResponse.setAggregations(EsQueryResponseConvertor.convert(response));
        }
        return queryResponse;
    }
}
