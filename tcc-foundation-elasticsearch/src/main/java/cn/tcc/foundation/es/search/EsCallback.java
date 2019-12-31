package cn.tcc.foundation.es.search;

import java.io.Serializable;

public interface EsCallback<T> extends Serializable {

    Object call(T source);

}
