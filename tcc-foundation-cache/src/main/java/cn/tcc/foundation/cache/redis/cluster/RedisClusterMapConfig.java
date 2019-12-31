package cn.tcc.foundation.cache.redis.cluster;

import java.io.Serializable;
import java.util.Map;

public class RedisClusterMapConfig implements Serializable {

    private Map<String, RedisClusterConfig> clusters;

    public Map<String, RedisClusterConfig> getClusters() {
        return clusters;
    }

    public void setClusters(Map<String, RedisClusterConfig> clusters) {
        this.clusters = clusters;
    }

    @Override
    public String toString() {
        return "RedisClusterMapConfig{" + "clusters=" + clusters + '}';
    }
}
