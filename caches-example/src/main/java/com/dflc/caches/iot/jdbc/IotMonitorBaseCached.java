//package com.dflc.caches.iot.jdbc;
//
//
//import com.dflc.service.cache.persistence.jooq.tables.pojos.IotMonitorBase;
//import org.legomd.cache.ignite.core.CachedConfiguration;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 缓存工厂
// */
//@Configuration
//public class IotMonitorBaseCached extends CachedConfiguration<String, IotMonitorBase> {
//
//    public IotMonitorBaseCached() {
//        this(String.class, IotMonitorBase.class, IotMonitorBaseCachedStore.class);
//    }
//
//    public IotMonitorBaseCached(Class key, Class clazz, Class store) {
//        super(key, clazz, store);
//    }
//
//}
