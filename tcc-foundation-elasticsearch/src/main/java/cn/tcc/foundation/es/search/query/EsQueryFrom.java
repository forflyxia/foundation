package cn.tcc.foundation.es.search.query;


public interface EsQueryFrom<T> {

    EsQuery<T> from(String index, String type);

}
