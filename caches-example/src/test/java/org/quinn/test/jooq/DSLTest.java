//package org.quinn.test.jooq;
//
//import com.dflc.caches.iot.jooq.IotMonitorBaseCachedJooq;
//import org.apache.ignite.Ignite;
//import org.apache.ignite.IgniteCache;
//import org.apache.ignite.Ignition;
//
//public class DSLTest {
//    public static void main(String[] args) {
//
//        try (Ignite ignite = Ignition.start("config/ignite.xml")) {
//            IgniteCache cache = ignite.getOrCreateCache(new IotMonitorBaseCachedJooq());
//            cache.loadCache(null);
//        }
//    }
//}
