package cn.tcc.foundation.es.search.query;


public interface CombineQuery<T> extends EsFilter<T>{

    EsQuery<T> boxToBool() throws Exception;
}
