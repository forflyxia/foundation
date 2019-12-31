package cn.tcc.foundation.es.search.query;

import cn.tcc.foundation.es.constant.SortTypeEnum;
import cn.tcc.foundation.es.search.EsCallback;


public interface EsSort<T> extends EsPaging<T> {

    EsSort<T> sort(EsCallback<T> c, SortTypeEnum sortType);

    EsSort<T> sort(EsCallback<T> c, SortTypeEnum sortType, boolean iskeyword);
}
