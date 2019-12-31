package cn.tcc.foundation.core.convert;

public class IntegerTo {

    public static Boolean toBoolean(Integer value) {
        if (value == null) {
            return false;
        }
        return value == 1;
    }

    public static Boolean toBoolean(Short value) {
        if (value == null) {
            return false;
        }
        return value == 1;
    }
}
