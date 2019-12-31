package cn.tcc.foundation.metric.config;

import com.aliyun.openservices.log.Client;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shawn.xiao 2019/1/2
 **/
public class ArmsClient {
    private static final ConcurrentHashMap<String, Client> ARMS_CLIENT_MAP = new ConcurrentHashMap();

    public static Client getClient(String armsName) throws Exception {
        Client client = ARMS_CLIENT_MAP.get(armsName);
        if (client == null) {
            ArmsConfig armsConfig = ArmsClientProvider.get(armsName);
            if (armsConfig == null) {
                throw new Exception("arms info not exists.");
            }
            client = createClient(armsConfig);
            ARMS_CLIENT_MAP.putIfAbsent(armsName, client);
        }
        return client;
    }

    private static Client createClient(ArmsConfig armsConfig) {
        return new Client(armsConfig.getEndpoint(),
                armsConfig.getAccessKeyId(),
                armsConfig.getAccessKeySecret());
    }


}
