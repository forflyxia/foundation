package cn.tcc.foundation.appconfig.acm;

import cn.tcc.foundation.core.util.DateUtil;
import com.alibaba.edas.acm.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public abstract class AbstractConfig<T> {

    private static final String CONFIG_GROUP = "DEFAULT_GROUP";
    private static final LocalDateTime MIN_DATE_TIME = LocalDateTime.of(1900, 1, 1, 1, 0, 0);
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConfig.class.getTypeName());

    private final int DefaultCacheSeconds = 60;
    private final Object CacheUpdatingSyncRoot = new Object();
    /**
     * 缓存配置值（最近一次读取的配置值）
     */
    private T cacheValue = null;
    /**
     * 最近一次尝试读取配置的时间
     */
    private LocalDateTime lastReadTime = MIN_DATE_TIME;
    /**
     * 缓存是否正在更新
     */
    private volatile Boolean isCacheUpdating = false;

    /**
     * 配置键
     *
     * @return
     */
    protected abstract String getConfigKey();

    /**
     * 修正配置值（无效 或者 超出指定范围）
     *
     * @param value
     * @return
     */
    protected abstract T reviseValue(T value);

    /**
     * 缓存时间
     *
     * @return
     */
    protected int getCacheSeconds() {
        return DefaultCacheSeconds;
    }

    /**
     * 缓存是否需要更新((没有其他线程正在更新缓存 并且 缓存已过期) 或者 配置项从未成功读取过)
     *
     * @return
     */
    private Boolean shouldUpdateCache() {
        return (!this.isCacheUpdating && this.isCacheExpired())         // 没有其他线程正在更新 并且 缓存已过期
                || !this.isValueAlreadyRead();                              // 配置项从未成功读取过
    }

    /**
     * 是否缓存过期（现在时间距离最后一次读取的时间已超过1分钟）
     *
     * @return
     */
    private Boolean isCacheExpired() {
        int cacheSeconds = this.getCacheSeconds();
        if (cacheSeconds < DefaultCacheSeconds) {
            LOGGER.error(String.format("配置类'{%s}'中缓存过期时间CacheSeconds设定'{%s}'无效，现取值为{%s}毫秒. 请修复设定!", this.getClass().getTypeName(), cacheSeconds, DefaultCacheSeconds));
            cacheSeconds = DefaultCacheSeconds;
        }
        return DateUtil.seconds(this.lastReadTime, LocalDateTime.now()) >= cacheSeconds;
    }

    /**
     * 配置项是否成功读取过
     *
     * @return
     */
    protected Boolean isValueAlreadyRead() {
        return this.lastReadTime != MIN_DATE_TIME;
    }

    /**
     * 配置值
     *
     * @param clazz
     * @return
     */
    public T getValue(Class<?> clazz) {
        /*
            当需要更新缓存时, 重新读取配置
            为防止多线程重复读取, 采用double-check + lock对ShouldUpdateCache进行双重检查
        */
        if (this.shouldUpdateCache()) {
            synchronized (this.CacheUpdatingSyncRoot) {
                if (this.shouldUpdateCache()) {
                    try {
                        this.isCacheUpdating = true;
                        this.cacheValue = getConfigValue(clazz);
                        this.lastReadTime = LocalDateTime.now();
                    } finally {
                        this.isCacheUpdating = false;
                    }
                }
            }
        }
        return this.cacheValue;
    }

    /**
     * 读取配置
     *
     * @param clazz
     * @return
     */
    private T getConfigValue(Class<?> clazz) {
        T configValue = null;
        try {
            Object value = ConfigService.getConfig(this.getConfigKey(), getConfigGroup(), getReadTimeoutMs(), clazz);
            configValue = (T) value;
        } catch (Exception ex) {
            LOGGER.error(String.format("读取ACM配置中配置项'{%s}'发生错误: '{%s}'!", this.getConfigKey(), ex.getMessage()));
        }

        this.cacheValue = reviseValue(configValue);
        return configValue;
    }

    /**
     * 配置键
     *
     * @return
     */
    protected String getConfigGroup() {
        return CONFIG_GROUP;
    }

    /**
     * 配置键
     *
     * @return
     */
    protected long getReadTimeoutMs() {
        return 3 * 1000;
    }

    /**
     * 修正配置值（无效 或者 超出指定范围）
     *
     * @param value
     * @param defaultValue
     * @param minValue
     * @param maxValue
     * @param title
     * @return
     */
    protected int reviseValue(int value, int defaultValue, int minValue, int maxValue, String title) {
        Integer revised = null;

        if (value == 0) {
            revised = defaultValue;
        } else if (value < minValue) {
            revised = minValue;
        } else if (value > maxValue) {
            revised = maxValue;
        }

        if (revised != null) {
            LOGGER.error(String.format("配置项'{%s}'值{%s}无效, 应当介于{%s} - {%s}之间, 现修正为{%s}!", this.getConfigKey(), value, minValue, maxValue, revised.intValue()));
            value = revised.intValue();
        }
        return value;
    }
}

