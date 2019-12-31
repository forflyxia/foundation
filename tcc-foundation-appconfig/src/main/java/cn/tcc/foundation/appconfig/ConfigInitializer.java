package cn.tcc.foundation.appconfig;

import cn.tcc.foundation.core.XApplicationInitializer;
import cn.tcc.foundation.appconfig.acm.ConfigClient;
import cn.tcc.foundation.appconfig.acm.config.AcmConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ConfigInitializer implements XApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigInitializer.class.getTypeName());
    private static final String ACM_CONFIG_PATH_CONFIG = "acm/config-%s.yml";
    private static final String ENV_KEY = "spring.profiles.active";

    @Override
    public void onStartup() {

        String profile = System.getProperty(ENV_KEY);
        String path = String.format(ACM_CONFIG_PATH_CONFIG, profile);
        Constructor constructor = new Constructor(AcmConfig.class);
        Yaml yaml = new Yaml(constructor);
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        AcmConfig config = yaml.loadAs(input, AcmConfig.class);
        System.out.println(yaml.dump(config));
        ConfigClient.init(config);
        System.out.println("ConfigInitializer onStartup done.");
    }
}
