package cn.tcc.foundation.core.util;

import cn.tcc.foundation.core.lite.util.StringUtil;

/**
 * @author shawn.xiao
 * @date 2018/1/12
 */
public class NumberUtil {

    public static boolean isPositiveInteger(String number) {
        boolean result = false;
        try {
            if (!StringUtil.isNullOrEmpty(number)) {
                Integer n = Integer.parseInt(number);
                if (n != null && n > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            //
        }
        return result;
    }
}
