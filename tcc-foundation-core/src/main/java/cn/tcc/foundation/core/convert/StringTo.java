package cn.tcc.foundation.core.convert;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StringTo {

    public static Date toYMDDate(String input) {
        return toyMdDate(input);
    }

    public static Date toyMdDate(String input) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(input);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toyMdHmssDate(String input) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(input);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toyMdHmsSDate(String input) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            return sdf.parse(input);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate toLocalDate(String input) {
        if(input == null || "".equals(input)){
            return null;
        }
        return LocalDate.parse(input);
    }

    public static <T> List<T> toList(String input) {
        return StringTo.toList(input, ",");
    }

    public static <T> List<T> toList(String input, String separator) {
        List<T> items = new ArrayList<>();
        String[] splitItems = input.split(separator);
        for (String item: splitItems) {
            items.add((T)item);
        }
        return items;
    }


}
