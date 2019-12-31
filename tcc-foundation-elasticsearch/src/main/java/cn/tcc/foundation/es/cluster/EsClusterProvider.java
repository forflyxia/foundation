package cn.tcc.foundation.es.cluster;

import java.util.concurrent.ConcurrentHashMap;

public class EsClusterProvider {

    private static final ConcurrentHashMap<String, EsCluster> ES_CLUSTER_MAP = new ConcurrentHashMap<>();

    public static EsCluster get(String clusterName) throws Exception {
        EsCluster esCluster = ES_CLUSTER_MAP.get(clusterName);
        return esCluster;
    }

    public static void set(String name, EsCluster cluster) throws Exception {
        ES_CLUSTER_MAP.putIfAbsent(name, cluster);
        // warm up
        EsHighLevelClient.getClient(name);
    }
}
