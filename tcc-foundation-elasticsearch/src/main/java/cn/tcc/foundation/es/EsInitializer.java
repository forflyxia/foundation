package cn.tcc.foundation.es;

import cn.tcc.foundation.core.XApplicationInitializer;
import cn.tcc.foundation.es.cluster.EsCluster;
import cn.tcc.foundation.es.cluster.EsClusterMapConfig;
import cn.tcc.foundation.es.cluster.EsClusterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.servlet.ServletException;
import java.io.InputStream;
import java.util.Map;

public class EsInitializer implements XApplicationInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(EsInitializer.class.getTypeName());
    private static final String ES_CLUSTERS_PATH_FORMAT = "es/clusters-%s.yml";

    @Override
    public void onStartup() throws ServletException {
        String profile = System.getProperty("spring.profiles.active");
        String path = String.format(ES_CLUSTERS_PATH_FORMAT, profile);
        Constructor constructor = new Constructor(EsClusterMapConfig.class);
        Yaml yaml = new Yaml(constructor);
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        EsClusterMapConfig data = yaml.loadAs(input, EsClusterMapConfig.class);
        System.out.println(data);
        System.out.println(yaml.dump(data));

        try {
            for (Map.Entry<String, EsCluster> entry : data.getClusters().entrySet()) {
                EsClusterProvider.set(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            LOGGER.error("EsInitializer onStartup error.", e.getMessage());
            throw new ServletException("EsInitializer onStartup error.");
        }
        System.out.println("EsInitializer.onStartup done.");
    }
}
