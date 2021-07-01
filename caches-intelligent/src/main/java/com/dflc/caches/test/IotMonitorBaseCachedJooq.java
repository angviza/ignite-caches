package com.dflc.caches.test;


import com.dflc.service.cache.persistence.jooq.tables.pojos.IotMonitorBase;
import org.legomd.cache.ignite.core.CachedConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存工厂1
 */
@Configuration
public class IotMonitorBaseCachedJooq extends CachedConfiguration<Long, IotMonitorBase> {

    public IotMonitorBaseCachedJooq() {
        this(Long.class, IotMonitorBase.class, CachedStoreJooqAdapter.class);
    }

    public IotMonitorBaseCachedJooq(Class key, Class clazz, Class store) {
        super(key, clazz, store);
    }

}
