//package com.dflc.caches.iot;
//
//import org.jooq.SQLDialect;
//import org.jooq.impl.DSL;
//import org.jooq.impl.TableImpl;
//import org.legomd.cache.ignite.core.CachedStoreAdapter;
//
//import javax.cache.Cache;
//import javax.cache.integration.CacheLoaderException;
//import javax.cache.integration.CacheWriterException;
//
//public class CachedStoreJooqAdapter<K, V> extends CachedStoreAdapter<K, V> {
//    TableImpl table;
//
//    public CachedStoreJooqAdapter() {
//        init();
//        DSL.using(getDataSource(), SQLDialect.POSTGRES);
//        DSL.selectOne().from(table).fetch();
//    }
//
//    @Override
//    protected void init() {
//
//    }
//
//
//    @Override
//    public V load(K key) throws CacheLoaderException {
//        //
//        // DSL.selectOne().from(table).eq(new Record().)
//        return null;
//    }
//
//
//    @Override
//    public void write(Cache.Entry<? extends K, ? extends V> entry) throws CacheWriterException {
//
//    }
//
//
//    @Override
//    public void delete(Object key) throws CacheWriterException {
//        // DSL.mergeInto(table).usingDual();
//        // DSL.delete(table).where(table.getPrimaryKey().getName() + "=" + key).execute();
//    }
//}
