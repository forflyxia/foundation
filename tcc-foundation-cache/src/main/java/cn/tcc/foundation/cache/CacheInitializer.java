package cn.tcc.foundation.cache;

import cn.tcc.foundation.cache.redis.client.RedisClientProvider;
import cn.tcc.foundation.cache.redis.cluster.RedisClusterConfig;
import cn.tcc.foundation.cache.redis.cluster.RedisClusterMapConfig;
import cn.tcc.foundation.cache.redis.cluster.RedisClusterProvider;
import cn.tcc.foundation.core.XApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.servlet.ServletException;
import java.io.InputStream;
import java.util.Map;

public class CacheInitializer implements XApplicationInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(CacheInitializer.class.getTypeName());
    private static final String CACHE_CLUSTERS_PATH_FORMAT = "redisson/clusters-%s.yml";
    private static final String ENV_KEY = "spring.profiles.active";

    @Override
    public void onStartup() throws ServletException {

        String profile = System.getProperty(ENV_KEY);
        String path = String.format(CACHE_CLUSTERS_PATH_FORMAT, profile);
        Constructor constructor = new Constructor(RedisClusterMapConfig.class);
        Yaml yaml = new Yaml(constructor);
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        RedisClusterMapConfig config = yaml.loadAs(input, RedisClusterMapConfig.class);
        System.out.println(yaml.dump(config));

        try {
            for (Map.Entry<String, RedisClusterConfig> entry : config.getClusters().entrySet()) {
                RedisClusterProvider.set(entry.getKey(), entry.getValue());
                // warm up
                RedisClientProvider.getClient(entry.getKey());
            }
        } catch (Exception e) {
            LOGGER.error("CacheInitializer onStartup error.", e.getMessage());
            throw new ServletException("CacheInitializer onStartup error.");
        }
        System.out.println("CacheInitializer onStartup done.");
    }
}
