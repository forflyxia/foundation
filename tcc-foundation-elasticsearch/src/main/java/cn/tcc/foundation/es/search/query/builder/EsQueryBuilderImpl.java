package cn.tcc.foundation.es.search.query.builder;


import cn.tcc.foundation.es.search.query.builder.executor.EsQueryExecutorImpl;
import cn.tcc.foundation.es.search.reponse.EsResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.ArrayList;
import java.util.List;

public class EsQueryBuilderImpl<T> implements EsQueryBuilder<T> {

    private String clusterName;
    private String indexName;
    private String typeName;
    private BoolQueryBuilder rootBoolQueryBuilder = new BoolQueryBuilder();
    private List<QueryBuilder> mustClauses = new ArrayList();
    private List<QueryBuilder> mustNotClauses = new ArrayList();
    private List<QueryBuilder> filterClauses = new ArrayList();
    private List<QueryBuilder> shouldClauses = new ArrayList();
    private HighlightBuilder highlightBuilder;
    private List<AggregationBuilder> aggregationBuilders = new ArrayList<>();
    private List<SortBuilder<?>> sortBuilders = new ArrayList<>();
    private Integer pageIndex;
    private Integer pageSize;

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

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public HighlightBuilder getHighlightBuilder() {
        return this.highlightBuilder;
    }

    @Override
    public void setHighlightBuilder(HighlightBuilder highlightBuilder) {
        this.highlightBuilder = highlightBuilder;
    }

    public List<AggregationBuilder> getAggregationBuilders() {
        return this.aggregationBuilders;
    }

    public void setAggregationBuilders(List<AggregationBuilder> aggregationBuilders) {
        this.aggregationBuilders = aggregationBuilders;
    }

    public List<SortBuilder<?>> getSortBuilders() {
        return this.sortBuilders;
    }

    public void setSortBuilders(List<SortBuilder<?>> sortBuilders) {
        this.sortBuilders = sortBuilders;
    }

    @Override
    public EsResponse<T> builder(Class<?> clazz) {
        try {
            return new EsQueryExecutorImpl(this).execute(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BoolQueryBuilder getRootBoolQueryBuilder() {
        return rootBoolQueryBuilder;
    }

    public void setRootBoolQueryBuilder(BoolQueryBuilder rootBoolQueryBuilder) {
        this.rootBoolQueryBuilder = rootBoolQueryBuilder;
    }

    public List<QueryBuilder> getMustClauses() {
        return mustClauses;
    }

    public void setMustClauses(List<QueryBuilder> mustClauses) {
        this.mustClauses = mustClauses;
    }

    public List<QueryBuilder> getMustNotClauses() {
        return mustNotClauses;
    }

    public void setMustNotClauses(List<QueryBuilder> mustNotClauses) {
        this.mustNotClauses = mustNotClauses;
    }

    public List<QueryBuilder> getFilterClauses() {
        return filterClauses;
    }

    public void setFilterClauses(List<QueryBuilder> filterClauses) {
        this.filterClauses = filterClauses;
    }

    public List<QueryBuilder> getShouldClauses() {
        return shouldClauses;
    }

    public void setShouldClauses(List<QueryBuilder> shouldClauses) {
        this.shouldClauses = shouldClauses;
    }
}
