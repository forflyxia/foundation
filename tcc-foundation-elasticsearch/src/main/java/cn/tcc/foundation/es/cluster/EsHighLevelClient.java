package cn.tcc.foundation.es.cluster;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.concurrent.ConcurrentHashMap;

public class EsHighLevelClient {

    private static final ConcurrentHashMap<String, RestHighLevelClient> HIGHLEVEL_CLIENT_MAP = new ConcurrentHashMap<>();

    public static RestHighLevelClient getClient(String clusterName) throws Exception {
        RestHighLevelClient client = HIGHLEVEL_CLIENT_MAP.get(clusterName);
        if (client == null) {
            EsCluster esCluster = EsClusterProvider.get(clusterName);
            if (esCluster == null) {
                throw new Exception("cluster info not exists.");
            }
            client = createClient(esCluster);
            HIGHLEVEL_CLIENT_MAP.putIfAbsent(clusterName, client);
        }
        return client;
    }

    private static RestHighLevelClient createClient(EsCluster esCluster) {
        int addressSizes = esCluster.getAddress().size();
        HttpHost[] httpHosts = new HttpHost[addressSizes];
        for (int i = 0; i < addressSizes; i++) {
            String address = esCluster.getAddress().get(i);
            int index = address.indexOf(':');
            if (index >= 0) {
                String ip = address.substring(0, index);
                int port = Integer.parseInt(address.substring(index + 1));
                HttpHost httpHost = new HttpHost(ip, port, "http");
                httpHosts[i] = httpHost;
            }
        }
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
