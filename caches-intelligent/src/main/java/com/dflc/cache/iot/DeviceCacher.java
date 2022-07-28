package com.dflc.cache.iot;

import com.dflc.cache.iot.entity.DeviceCache;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.legomd.cache.ignite.IgniteCacheBind;

import java.util.List;

public class DeviceCacher extends IgniteCacheBind<DeviceCache> {

    private static DeviceCacher dm =null;

    private DeviceCacher() {
        shared();
        setType(DeviceCache.class);
    }

    public static DeviceCacher get() {
        if (dm == null) {
            dm = new DeviceCacher();
        }
        return dm;
    }

    SqlFieldsQuery sql = new SqlFieldsQuery("select count(1) from IotMonitorBase");

    public FieldsQueryCursor<List<?>> query(SqlFieldsQuery sql) {
       // ignite.resetLostPartitions(Arrays.asList("Device"));
        return cache().query(sql);
    }

    public DeviceCache getDevice(String seq) {
        return dm.get(seq);
    }

    public Boolean delDevice(String seq) {
        return dm.del(seq);
    }

    public DeviceCache saveDevice(DeviceCache device){
        set(device);
        return dm.get(device.getSeq());
    }

}