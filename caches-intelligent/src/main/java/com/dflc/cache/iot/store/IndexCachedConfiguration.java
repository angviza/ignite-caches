package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.IndexCache;
import org.legomd.cache.ignite.core.CachedConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存工厂
 */
@Configuration
public class IndexCachedConfiguration extends CachedConfiguration<String, IndexCache> {

    public IndexCachedConfiguration() {
        this(String.class, IndexCache.class, IndexCachedStore.class);
    }

    public IndexCachedConfiguration(Class key, Class clazz, Class store) {
        super(key, clazz, store);
    }

}
