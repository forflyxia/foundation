package cn.tcc.foundation.es.search.reponse;


public interface EsResponse<T> {

    long getTookInMillis();

    void setTookInMillis(long tookInMillis);

}
