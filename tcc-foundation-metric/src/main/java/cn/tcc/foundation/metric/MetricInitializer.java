package cn.tcc.foundation.metric;

import cn.tcc.foundation.core.XApplicationInitializer;
import cn.tcc.foundation.metric.config.ArmsClientProvider;
import cn.tcc.foundation.metric.config.ArmsConfig;
import cn.tcc.foundation.metric.config.ArmsMapConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.servlet.ServletException;
import java.io.InputStream;
import java.util.Map;

public class MetricInitializer implements XApplicationInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetricInitializer.class.getTypeName());
    private static final String ES_CLUSTERS_PATH_FORMAT = "arms/metric-%s.yml";

    @Override
    public void onStartup() throws ServletException {
        String profile = System.getProperty("spring.profiles.active");
        String path = String.format(ES_CLUSTERS_PATH_FORMAT, profile);
        Constructor constructor = new Constructor(ArmsMapConfig.class);
        Yaml yaml = new Yaml(constructor);
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        ArmsMapConfig data = yaml.loadAs(input, ArmsMapConfig.class);
        System.out.println(data);
        System.out.println(yaml.dump(data));

        try {
            for (Map.Entry<String, ArmsConfig> entry : data.getMetrics().entrySet()) {
                ArmsClientProvider.set(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            LOGGER.error("MetricInitializer onStartup error.", e.getMessage());
            throw new ServletException("MetricInitializer onStartup error.");
        }
        System.out.println("MetricInitializer.onStartup done.");
    }
}
