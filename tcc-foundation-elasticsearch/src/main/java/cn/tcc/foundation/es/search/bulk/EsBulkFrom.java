package cn.tcc.foundation.es.search.bulk;

public interface EsBulkFrom<T> {

    EsBulkSource<T> from(String index, String type);

}
