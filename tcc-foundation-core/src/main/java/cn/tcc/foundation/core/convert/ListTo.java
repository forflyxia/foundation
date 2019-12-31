package cn.tcc.foundation.core.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hbxia on 2017/4/10.
 */
public class ListTo {

    /**
     *
     * @param items
     * @param <T>
     * @return
     */
    public static <T> Map<Integer, List<T>> toGroup(List<T> items, int pageCount) {
        if (items.isEmpty()) {
            return new HashMap<>();
        }

        int pageSize = (int)Math.ceil(items.size() / (double)pageCount);
        HashMap<Integer, List<T>> map = new HashMap<>();
        for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
            List<T> pageItems = toPager(items, pageIndex, pageSize);
            map.put(pageIndex, pageItems);
        }
        return map;
    }

    /**
     *
     * @param items
     * @param pageIndex
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<T> toPager(List<T> items, int pageIndex, int pageSize) {
            int fromIndex = (pageIndex-1) * pageSize;
            int toIndex = pageIndex * pageSize;

            if (fromIndex > items.size()) {
                fromIndex = items.size();
            }
            if (toIndex > items.size()) {
                toIndex = items.size();
            }
            return items.subList(fromIndex, toIndex);
    }

    /**
     *
     * @param items
     * @param <T>
     * @return
     */
    public static <T> String toString(List<T> items) {
        List<String> stringItems = new ArrayList<>();
        for (T t : items) {
            stringItems.add(t.toString());
        }
        return String.join("," ,stringItems);
    }

    public static <T> String[] toStringArray(List<T> items) {
        String[] array = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            array[i] = items.get(i).toString();
        }
        return array;
    }
}
