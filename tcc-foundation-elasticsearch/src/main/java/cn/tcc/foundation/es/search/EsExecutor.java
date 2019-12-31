package cn.tcc.foundation.es.search;

import cn.tcc.foundation.es.search.reponse.EsResponse;

public interface EsExecutor<T> {

    EsResponse<T> execute(Class<?> clazz) throws Exception;
}
