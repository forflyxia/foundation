package cn.tcc.foundation.cache.redis.cluster;

public class RedisClusterConfig {

    private boolean enable;

    private String clusterType;

    private RedisSingleServerConfig singleServerConfig;

    private Integer threads;
    private Integer nettyThreads;
    private String transportMode;
    private String codec;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public RedisSingleServerConfig getSingleServerConfig() {
        return singleServerConfig;
    }

    public void setSingleServerConfig(RedisSingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public String getClusterType() {
        return clusterType;
    }

    public void setClusterType(String clusterType) {
        this.clusterType = clusterType;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(Integer nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }
}
