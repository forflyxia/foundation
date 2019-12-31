package cn.tcc.foundation.appconfig.acm;

import cn.tcc.foundation.appconfig.acm.config.AcmConfig;
import com.alibaba.edas.acm.ConfigService;
import com.alibaba.edas.acm.exception.ConfigException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author shawn.xiao
 * @date 2018/3/22
 */
public class ConfigClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigClient.class.getTypeName());
    private static final String DEFAULT_GROUP = "DEFAULT_GROUP";
    private static final long DEFAULT_TIMEOUT_MS = 3000;
    private static final Cache<String, String> CACHE_BUILDER = CacheBuilder.newBuilder().maximumSize(200).expireAfterAccess(30, TimeUnit.MINUTES).build();

    public static String getConfigNoCache(final String key) {
        try {
            return ConfigService.getConfig(key, DEFAULT_GROUP, DEFAULT_TIMEOUT_MS);
        } catch (ConfigException e) {
            System.out.println("getConfigNoCache:" + e.getErrMsg());
            return null;
        }
    }

    public static void refresh(final String key) {
        CACHE_BUILDER.invalidate(key);
    }

    public static String getConfig(final String key) {
        String value = CACHE_BUILDER.getIfPresent(key);
        if (value == null) {
            value = getConfigNoCache(key);
            if (value != null) {
                CACHE_BUILDER.put(key, value);
            }
        }
        return value;
    }

    public static String getConfig(final String key, final String group) {
        try {
            return ConfigService.getConfig(key, group, DEFAULT_TIMEOUT_MS);
        } catch (ConfigException e) {
            System.out.println("getConfigWithGroup:" + e.getErrMsg());
            return null;
        }
    }

    public static Properties getConfig2Properties(final String key) {
        try {
            return ConfigService.getConfig2Properties(key, DEFAULT_GROUP, DEFAULT_TIMEOUT_MS);
        } catch (ConfigException e) {
            System.out.println("getConfig2Properties:" + e.getErrMsg());
            return null;
        }
    }

    public static Properties getConfig2Properties(final String key, final String group) {
        try {
            return ConfigService.getConfig2Properties(key, group, DEFAULT_TIMEOUT_MS);
        } catch (ConfigException e) {
            System.out.println("getConfig2PropertiesWithGroup:" + e.getErrMsg());
            return null;
        }
    }

    public static Object getConfig(final String key, final Class<?> clazz) {
        try {
            return ConfigService.getConfig(key, DEFAULT_GROUP, DEFAULT_TIMEOUT_MS, clazz);
        } catch (ConfigException e) {
            System.out.println("getConfigWithClass:" + e.getErrMsg());
            return null;
        }
    }

    public static Object getConfig(final String key, final String group, final Class<?> clazz) {
        try {
            return ConfigService.getConfig(key, group, DEFAULT_TIMEOUT_MS, clazz);
        } catch (ConfigException e) {
            System.out.println("getConfigWithGroupClass:" + e.getErrMsg());
            return null;
        }
    }

    public static void init(AcmConfig acmConfig) {
        ConfigService.init(acmConfig.getDomain(), acmConfig.getNamespace(), acmConfig.getAccessKey(), acmConfig.getSecretKey());
        warmUp();
    }

    private static void warmUp() {
        try {
            ConfigService.getConfig("foundation.appconfig.warmup", DEFAULT_GROUP, 1);
        } catch (ConfigException e) {
            LOGGER.warn("foundation.appconfig.warmup");
        }
    }
}
