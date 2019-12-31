package cn.tcc.foundation.core.serialization;

import com.alibaba.fastjson.JSON;


/**
 * Created by hbxia on 2017/2/22.
 */
public class FastJsonSerializer {

    /**
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T extends Object> String toString(T obj) {
        String result = JSON.toJSONString(obj);
        return result;
    }

    /**
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends Object> T toObject(String text, Class<T> clazz) {
        T t = JSON.parseObject(text, clazz);
        return t;
    }
}
