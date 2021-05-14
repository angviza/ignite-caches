package com.dflc.service.cache.ignite.core;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.configuration.FactoryBuilder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.apache.ignite.cache.CacheWriteSynchronizationMode.FULL_SYNC;


public class CachedConfiguration<K, V> extends CacheConfiguration<K, V> {


    protected Class<?>[] indexTypes;
    protected List<QueryEntity> qes;
    protected CacheMode cacheMode = CacheMode.PARTITIONED;
    protected CacheAtomicityMode atomicityMode = CacheAtomicityMode.ATOMIC;
    protected int backups = 2;
    protected boolean readThrough = true;
    protected boolean writeThrough = true;
    protected boolean writeBehindEnabled = true;
    protected int writeBehindFlushSize = 1024;
    protected int writeBehindBatchSize = 512;
    protected int writeBehindFlushThreadCount = 1;
    protected int writeBehindFlushFrequency = 5;
    protected CacheWriteSynchronizationMode writeSynchronizationMode = FULL_SYNC;
    protected String sqlSchema = "PUBLIC";

    public CachedConfiguration(Class key, Class clazz, Class store) {
        setName(clazz.getSimpleName());
        setCacheMode(cacheMode);
        setAtomicityMode(atomicityMode);
        setBackups(backups);

        setReadThrough(readThrough);
        setWriteThrough(writeThrough);
        setWriteBehindEnabled(writeBehindEnabled);
        setWriteBehindFlushSize(writeBehindFlushSize);
        setWriteBehindBatchSize(writeBehindBatchSize);
        setWriteBehindFlushThreadCount(writeBehindFlushThreadCount);
        setWriteBehindFlushFrequency(writeBehindFlushFrequency);
        setWriteSynchronizationMode(writeSynchronizationMode);
        setSqlSchema(sqlSchema);


        qes = new ArrayList<>();

        QueryEntity qe = new QueryEntity();
        qe.setKeyType(key.getName());
        qe.setValueType(clazz.getName());
        fields = new LinkedHashMap<>();
        for (Field field : clazz.getFields()) {
            fields.put(field.getName(), field.getType());
        }
        qe.setFields(fields);
        qes.add(qe);

        setCacheStoreFactory(FactoryBuilder.factoryOf(store));
        setIndexedTypes(indexTypes);
        setQueryEntities(qes);
    }


    protected LinkedHashMap fields;

    protected <T> void setFields(String key, Class t) {
        fields.put(key, t.getName());
    }


}
