package cn.tcc.foundation.core.lite.util;

/**
 * @author shawn.xiao
 * @date 2018/1/2
 */
public class OsUtil {

    public static boolean isOSWindows() {
        String osName = System.getProperty("os.name");
        if (StringUtil.isNullOrEmpty(osName)) {
            return false;
        }
        return osName.startsWith("Windows");
    }
}
