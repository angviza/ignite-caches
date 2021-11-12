package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.DeviceCache;
import com.dflc.cache.iot.entity.GatewayCache;
import org.legomd.cache.ignite.core.CachedConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存工厂
 */
@Configuration
public class GatewayCachedConfiguration extends CachedConfiguration<String, DeviceCache> {

    public GatewayCachedConfiguration() {
        this(String.class, GatewayCache.class, GatewayCachedStore.class);
    }

    public GatewayCachedConfiguration(Class key, Class clazz, Class store) {
        super(key, clazz, store);
    }

}
