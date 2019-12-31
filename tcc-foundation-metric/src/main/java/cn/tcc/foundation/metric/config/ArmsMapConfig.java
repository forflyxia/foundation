package cn.tcc.foundation.metric.config;

import java.util.Map;

/**
 * @author shawn.xiao 2019/1/2
 **/
public class ArmsMapConfig {

    private Map<String, ArmsConfig> metrics;

    public Map<String, ArmsConfig> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, ArmsConfig> metrics) {
        this.metrics = metrics;
    }

    @Override
    public String toString() {
        return "ArmsMapConfig{" +
                "metrics=" + metrics +
                '}';
    }
}
