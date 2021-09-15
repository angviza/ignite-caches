package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.Device;
import org.legomd.cache.ignite.core.CachedConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存工厂
 */
@Configuration
public class DeviceCached extends CachedConfiguration<String, Device> {

    public DeviceCached() {
        this(String.class, Device.class, DeviceCachedStore.class);
    }

    public DeviceCached(Class key, Class clazz, Class store) {
        super(key, clazz, store);
    }

}
