package com.dflc.caches.iot;


import com.dflc.service.cache.persistence.jooq.tables.pojos.IotMonitorBase;
import org.legomd.cache.ignite.core.CachedConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存工厂1
 */
@Configuration
public class IotMonitorBaseCached extends CachedConfiguration<Long, IotMonitorBase> {

    public IotMonitorBaseCached() {
        this(Long.class, IotMonitorBase.class, IotMonitorBaseCachedStore.class);
    }

    public IotMonitorBaseCached(Class key, Class clazz, Class store) {
        super(key, clazz, store);
    }

}
