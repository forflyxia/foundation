package cn.tcc.foundation.es.search.query.builder;

import cn.tcc.foundation.es.search.EsBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;

public interface EsQueryBuilder<T> extends EsBuilder<T> {

    BoolQueryBuilder getRootBoolQueryBuilder();

    void setRootBoolQueryBuilder(BoolQueryBuilder rootBoolQueryBuilder);

    List<QueryBuilder> getMustClauses();

    void setMustClauses(List<QueryBuilder> mustClauses);

    List<QueryBuilder> getMustNotClauses();

    void setMustNotClauses(List<QueryBuilder> mustNotClauses);

    List<QueryBuilder> getFilterClauses();

    void setFilterClauses(List<QueryBuilder> filterClauses);

    List<QueryBuilder> getShouldClauses();

    void setShouldClauses(List<QueryBuilder> shouldClauses);

    HighlightBuilder getHighlightBuilder();

    void setHighlightBuilder(HighlightBuilder highlightBuilder);

    List<AggregationBuilder> getAggregationBuilders();

    void setAggregationBuilders(List<AggregationBuilder> aggregationBuilders);

    List<SortBuilder<?>> getSortBuilders();

    void setSortBuilders(List<SortBuilder<?>> sortBuilders);

    Integer getPageIndex();

    void setPageIndex(Integer pageIndex);

    Integer getPageSize();

    void setPageSize(Integer pageSize);
}
