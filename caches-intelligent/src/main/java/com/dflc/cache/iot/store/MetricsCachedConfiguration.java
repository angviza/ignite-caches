package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.MetricsCache;
import org.legomd.cache.ignite.core.CachedConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存工厂
 */
@Configuration
public class MetricsCachedConfiguration extends CachedConfiguration<String, MetricsCache> {

    public MetricsCachedConfiguration() {
        this(String.class, MetricsCache.class, MetricsCachedStore.class);
    }

    public MetricsCachedConfiguration(Class key, Class clazz, Class store) {
        super(key, clazz, store);
    }

}
