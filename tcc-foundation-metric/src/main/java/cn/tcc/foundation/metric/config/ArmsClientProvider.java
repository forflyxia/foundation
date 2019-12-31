package cn.tcc.foundation.metric.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shawn.xiao 2019/1/2
 **/
public class ArmsClientProvider {

    private static final ConcurrentHashMap<String, ArmsConfig> ARMS_MAP = new ConcurrentHashMap<>();

    public static ArmsConfig get(String armsName) throws Exception {
        ArmsConfig arms = ARMS_MAP.get(armsName);
        return arms;
    }

    public static void set(String name, ArmsConfig armsConfig) throws Exception {
        ARMS_MAP.putIfAbsent(name, armsConfig);
        // warm up
        ArmsClient.getClient(name);
    }
}
