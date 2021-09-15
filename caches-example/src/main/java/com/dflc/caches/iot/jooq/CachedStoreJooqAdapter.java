//package com.dflc.caches.iot.jooq;
//
//import com.dflc.service.cache.persistence.jooq.tables.daos.IotMonitorBaseDao;
//import com.dflc.service.cache.persistence.jooq.tables.pojos.IotMonitorBase;
//import org.apache.ignite.lang.IgniteBiInClosure;
//import org.jooq.impl.DefaultConfiguration;
//import org.legomd.cache.ignite.core.CachedStoreAdapter;
//
//import javax.cache.Cache;
//import javax.cache.integration.CacheLoaderException;
//import javax.cache.integration.CacheWriterException;
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//public class CachedStoreJooqAdapter extends CachedStoreAdapter<Long, IotMonitorBase> {
//
//    IotMonitorBaseDao dao;
//
//
//    protected IotMonitorBaseDao getDao() {
//        if (dao == null) {
//            DefaultConfiguration cfg = new DefaultConfiguration();
//            cfg.setDataSource(getDataSource());
//            dao = new IotMonitorBaseDao(cfg);
//        }
//        return dao;
//    }
//
//    @Override
//    public void loadCache(IgniteBiInClosure<Long, IotMonitorBase> clo, Object... args) {
//        getDao().fetchByStatus((short) 1).forEach(r -> clo.apply(r.getId(), r));
//    }
//
//    @Override
//    public IotMonitorBase load(Long key) throws CacheLoaderException {
//        return getDao().fetchOneById(key);
//    }
//
//
//    @Override
//    public void write(Cache.Entry<? extends Long, ? extends IotMonitorBase> entry) throws CacheWriterException {
//        getDao().merge(entry.getValue());
//    }
//
//    @Override
//    public void writeAll(Collection<Cache.Entry<? extends Long, ? extends IotMonitorBase>> entries) {
//        getDao().merge(entries.stream().map(Cache.Entry::getValue).collect(Collectors.toList()));
//    }
//
//    @Override
//    public void delete(Object key) throws CacheWriterException {
//        getDao().deleteById((Long) key);
//    }
//
//    @Override
//    public void deleteAll(Collection<?> keys) {
//        getDao().deleteById(keys.toArray(Long[]::new));
//    }
//}
