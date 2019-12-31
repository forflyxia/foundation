package cn.tcc.foundation.es.cluster;

import java.util.Map;

public class EsClusterMapConfig {

    private Map<String, EsCluster> clusters;

    public Map<String, EsCluster> getClusters() {
        return clusters;
    }

    public void setClusters(Map<String, EsCluster> clusters) {
        this.clusters = clusters;
    }

    @Override
    public String toString() {
        return "EsClusterMapConfig{" + "clusters=" + clusters + '}';
    }
}
