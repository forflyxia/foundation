package cn.tcc.foundation.es.search.query;

import cn.tcc.foundation.es.constant.SortTypeEnum;
import cn.tcc.foundation.es.search.EsCallback;
import cn.tcc.foundation.es.search.EsExecutor;
import cn.tcc.foundation.es.search.query.builder.EsQueryBuilder;
import cn.tcc.foundation.es.search.query.builder.EsQueryBuilderImpl;
import cn.tcc.foundation.es.search.reponse.EsQueryResponse;
import cn.tcc.foundation.es.util.LambdaBodyParser;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

public class EsQueryClient<T> implements EsQueryFrom<T>, EsQuery<T> {

    private EsQueryBuilder<T> queryBuilder = new EsQueryBuilderImpl<>();

    public EsQueryClient(String clusterName) {
        queryBuilder.setClusterName(clusterName);
    }

    @Override
    public EsQuery<T> from(String index, String type) {
        queryBuilder.setIndexName(index);
        queryBuilder.setTypeName(type);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustMatch(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        queryBuilder.getMustClauses().add(QueryBuilders.matchQuery(methodName, value));
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustNotMatch(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        queryBuilder.getMustNotClauses().add(QueryBuilders.matchQuery(methodName, value));
        return this;
    }

    @Override
    public <TValue> EsQuery<T> shouldMatch(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        queryBuilder.getShouldClauses().add(QueryBuilders.matchQuery(methodName, value));
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustTerm(EsCallback<T> c, TValue value) {
        return mustTerm(c, value, false);
    }

    @Override
    public <TValue> EsQuery<T> mustTerm(EsCallback<T> c, TValue value, boolean iskeyword) {
        String methodName = LambdaBodyParser.getMethodName(c);
        if (iskeyword) {
            methodName = String.format("%s.keyword", methodName);
        }
        queryBuilder.getMustClauses().add(QueryBuilders.termQuery(methodName, value));
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustRange(EsCallback<T> c, TValue from, TValue to) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.from(from);
        rangeQueryBuilder.to(to);
        queryBuilder.getMustClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustRangeGTE(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.gte(value);
        queryBuilder.getMustClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustRangeLTE(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.lte(value);
        queryBuilder.getMustClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustNotTerm(EsCallback<T> c, TValue value) {
        return mustNotTerm(c, value, false);
    }

    @Override
    public <TValue> EsQuery<T> mustNotTerm(EsCallback<T> c, TValue value, boolean iskeyword) {
        String methodName = LambdaBodyParser.getMethodName(c);
        if (iskeyword) {
            methodName = String.format("%s.keyword", methodName);
        }
        queryBuilder.getMustNotClauses().add(QueryBuilders.termQuery(methodName, value));
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustNotRange(EsCallback<T> c, TValue from, TValue to) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.from(from);
        rangeQueryBuilder.to(to);
        queryBuilder.getMustNotClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustNotRangeGTE(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.gte(value);
        queryBuilder.getMustNotClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> mustNotRangeLTE(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.lte(value);
        queryBuilder.getMustNotClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> shouldTerm(EsCallback<T> c, TValue value) {
        return shouldTerm(c, value, false);
    }

    @Override
    public <TValue> EsQuery<T> shouldTerm(EsCallback<T> c, TValue value, boolean iskeyword) {
        String methodName = LambdaBodyParser.getMethodName(c);
        if (iskeyword) {
            methodName = String.format("%s.keyword", methodName);
        }
        queryBuilder.getShouldClauses().add(QueryBuilders.termQuery(methodName, value));
        return this;
    }

    @Override
    public <TValue> EsQuery<T> shouldRange(EsCallback<T> c, TValue from, TValue to) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.from(from);
        rangeQueryBuilder.to(to);
        queryBuilder.getShouldClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> shouldRangeGTE(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.gte(value);
        queryBuilder.getShouldClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> shouldRangeLTE(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.lte(value);
        queryBuilder.getShouldClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public EsQuery<T> boxToBool() throws Exception {
        if (queryBuilder.getMustClauses().isEmpty() && queryBuilder.getMustNotClauses().isEmpty() && queryBuilder.getShouldClauses().isEmpty() && queryBuilder.getFilterClauses().isEmpty()) {
            throw new Exception("nothing clauses to box.");
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (QueryBuilder must : queryBuilder.getMustClauses()) {
            boolQueryBuilder.must(must);
        }
        queryBuilder.getMustClauses().clear();

        for (QueryBuilder mustnot : queryBuilder.getMustNotClauses()) {
            boolQueryBuilder.mustNot(mustnot);
        }
        queryBuilder.getMustNotClauses().clear();

        for (QueryBuilder should : queryBuilder.getShouldClauses()) {
            boolQueryBuilder.should(should);
        }
        queryBuilder.getShouldClauses().clear();

        for (QueryBuilder filter : queryBuilder.getFilterClauses()) {
            boolQueryBuilder.filter(filter);
        }
        queryBuilder.getFilterClauses().clear();

        queryBuilder.getRootBoolQueryBuilder().must(boolQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> filterTerm(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        queryBuilder.getFilterClauses().add(QueryBuilders.termQuery(methodName, value));
        return this;
    }

    @Override
    public <TValue> EsQuery<T> filterRange(EsCallback<T> c, TValue from, TValue to) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.from(from);
        rangeQueryBuilder.to(to);
        queryBuilder.getFilterClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> filterRangeGTE(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.gte(value);
        queryBuilder.getFilterClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public <TValue> EsQuery<T> filterRangeLTE(EsCallback<T> c, TValue value) {
        String methodName = LambdaBodyParser.getMethodName(c);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(methodName);
        rangeQueryBuilder.lte(value);
        queryBuilder.getFilterClauses().add(rangeQueryBuilder);
        return this;
    }

    @Override
    public EsHighlight<T> highlight(EsCallback<T>... callbacks) {
        checkHighlightBuilder();
        for (EsCallback<T> c : callbacks) {
            String methodName = LambdaBodyParser.getMethodName(c);
            HighlightBuilder.Field field = new HighlightBuilder.Field(methodName);
            queryBuilder.getHighlightBuilder().field(field);
        }
        return this;
    }

    @Override
    public EsSort<T> sort(EsCallback<T> c, SortTypeEnum sortType) {
        return sort(c, sortType, false);
    }

    @Override
    public EsSort<T> sort(EsCallback<T> c, SortTypeEnum sortType, boolean iskeyword) {
        String methodName = LambdaBodyParser.getMethodName(c);
        if (iskeyword) {
            methodName = String.format("%s.keyword", methodName);
        }
        SortBuilder<?> sortBuilder = SortBuilders.fieldSort(methodName);
        SortOrder order = sortType.equals(SortTypeEnum.ASC) ? SortOrder.ASC : SortOrder.DESC;
        sortBuilder.order(order);
        queryBuilder.getSortBuilders().add(sortBuilder);
        return this;
    }

    @Override
    public <TValue> EsAggregation<T> aggRange(EsCallback<T> c, TValue from, TValue to) {
        String methodName = LambdaBodyParser.getMethodName(c);
        String aggName = String.format("rangeby_%s", methodName);
        RangeAggregationBuilder aggregation = AggregationBuilders.range(aggName).field(methodName);
        aggregation.addRange(new Double(from.toString()), new Double(to.toString()));
        queryBuilder.getAggregationBuilders().add(aggregation);
        return this;
    }

    @Override
    public EsAggregation<T> aggTerm(EsCallback<T>... callbacks) {
        for (EsCallback<T> c : callbacks) {
            String methodName = LambdaBodyParser.getMethodName(c);
            String aggName = String.format("termby_%s", methodName);
            TermsAggregationBuilder aggregation = AggregationBuilders.terms(aggName).field(methodName);
            queryBuilder.getAggregationBuilders().add(aggregation);
        }
        return this;
    }

    @Override
    public EsExecutor<T> paging(Integer pageIndex, Integer pageSize) {
        queryBuilder.setPageIndex(pageIndex);
        queryBuilder.setPageSize(pageSize);
        return this;
    }

    @Override
    public EsQueryResponse<T> execute(Class<?> clazz) {
        return (EsQueryResponse<T>) queryBuilder.builder(clazz);
    }

    private void checkHighlightBuilder() {
        if (queryBuilder.getHighlightBuilder() == null) {
            queryBuilder.setHighlightBuilder(new HighlightBuilder());
        }
    }
}
