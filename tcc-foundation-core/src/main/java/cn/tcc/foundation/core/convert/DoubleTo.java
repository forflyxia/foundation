package cn.tcc.foundation.core.convert;

import java.math.BigDecimal;

public class DoubleTo {

    public static BigDecimal toBigDecimal(Double value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal newValue = BigDecimal.valueOf(value);
        return newValue.setScale(2, BigDecimal.ROUND_UP);
    }
}
