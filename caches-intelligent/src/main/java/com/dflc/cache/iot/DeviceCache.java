package com.dflc.cache.iot;

import com.dflc.cache.iot.entity.Device;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.legomd.cache.ignite.IgniteCacheBind;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;

public class DeviceCache extends IgniteCacheBind<Device> {

    private static DeviceCache dm =null;

    private DeviceCache() {
        shared();
        setType(Device.class);
    }

    public static DeviceCache getDeviceCache() {
        if (dm == null) {
            dm = new DeviceCache();
        }
        return dm;
    }

    SqlFieldsQuery sql = new SqlFieldsQuery("select count(1) from IotMonitorBase");

    public FieldsQueryCursor<List<?>> query(SqlFieldsQuery sql) {
       // ignite.resetLostPartitions(Arrays.asList("Device"));
        return cache().query(sql);
    }

    public Device getDevice(String seq) {
        return dm.get(seq);
    }

    public Boolean delDevice(String seq) {
        return dm.del(seq);
    }

    public Device saveDevice(Device device){
        set(device);
        return dm.get(device.getSeq());
    }

}
