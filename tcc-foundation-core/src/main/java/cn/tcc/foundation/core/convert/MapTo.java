package cn.tcc.foundation.core.convert;

import java.util.List;
import java.util.Map;

/**
 * Created by hbxia on 2017/2/22.
 */
public class MapTo {

    /**
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> String toString(Map<K, V> map) {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            String key = String.format("Key=%s", entry.getKey());
            buffer.append(key);
            buffer.append(",");

            String value = "";
            if (entry.getValue() instanceof List) {
                List<?> items = (List<?>)entry.getValue();
                value = String.format("Value=%s", ListTo.toString(items));
            } else {
                value = String.format("Value=%s", entry.getValue());
            }
            buffer.append(value);
            buffer.append(";");
        }
        return buffer.toString();
    }
}
