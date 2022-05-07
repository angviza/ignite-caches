package com.dflc.cache.iot;

import com.dflc.cache.iot.entity.DeviceCache;
import com.dflc.cache.iot.entity.IndexCache;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.legomd.cache.ignite.IgniteCacheBind;

import java.util.List;

public class IndexCacher extends IgniteCacheBind<IndexCache> {

    private static IndexCacher dm =null;

    private IndexCacher() {
        shared();
        setType(IndexCache.class);
    }

    public static IndexCacher getIndexCache() {
        if (dm == null) {
            dm = new IndexCacher();
        }
        return dm;
    }

    SqlFieldsQuery sql = new SqlFieldsQuery("select count(1) from IotMonitorIndex");

    public FieldsQueryCursor<List<?>> query(SqlFieldsQuery sql) {
        return cache().query(sql);
    }

    public IndexCache getIndex(String code) {
        return dm.get(code);
    }

    public Boolean delIndex(String code) {
        return dm.del(code);
    }

    public IndexCache saveIndex(IndexCache index){
        set(index);
        return dm.get(index.getCode());
    }

}
