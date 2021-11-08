package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.DeviceCache;
import org.legomd.cache.ignite.core.CachedConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存工厂
 */
@Configuration
public class DeviceCachedConfiguration extends CachedConfiguration<String, DeviceCache> {

    public DeviceCachedConfiguration() {
        this(String.class, DeviceCache.class, DeviceCachedStore.class);
    }

    public DeviceCachedConfiguration(Class key, Class clazz, Class store) {
        super(key, clazz, store);
    }

}
