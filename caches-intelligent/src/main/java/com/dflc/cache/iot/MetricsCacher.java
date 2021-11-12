package com.dflc.cache.iot;

import com.dflc.cache.iot.entity.DeviceCache;
import com.dflc.cache.iot.entity.MetricsCache;
import org.legomd.cache.ignite.IgniteCacheBind;

import java.util.concurrent.ConcurrentHashMap;

public class MetricsCacher extends IgniteCacheBind<MetricsCache> {

    private static MetricsCacher ins;
    DeviceCacher deviceCacher;
    private final ConcurrentHashMap<String, MetricsCache> metricsLocalCache = new ConcurrentHashMap<>();

    public MetricsCache getCache(String seq) {
        MetricsCache mc = metricsLocalCache.get(seq);
        if (mc == null) {
            mc = getOrFrom(seq);
            if (mc != null)
                metricsLocalCache.put(seq, mc);
        }
        return mc;
    }

    public static MetricsCacher i() {
        if (ins == null) {
            ins = new MetricsCacher();
        }
        return ins;
    }

//    @Override
//    public <E extends Cacheable> boolean set(E value) {
//        metricsLocalCache.put(value.cacheKey().toString(), (MetricsCache) value);
//        return super.set(value);
//    }

    public  MetricsCache saveMetrics(MetricsCache value) {
        metricsLocalCache.put(value.getSeq(), value);
        set(value);
        return value;
    }

    private MetricsCacher() {
        setType(MetricsCache.class);
        deviceCacher = DeviceCacher.getDeviceCache();
    }

    @Override
    public MetricsCache getFrom(Object key) {
        String seq = key.toString();
        DeviceCache device = deviceCacher.getDevice(seq);
        if (device != null) {
            return new MetricsCache(seq, null, device.getId_(), device.getTypeId(), 0, 0, device.getOrgSeqId());
        }
        return null;
    }
}
