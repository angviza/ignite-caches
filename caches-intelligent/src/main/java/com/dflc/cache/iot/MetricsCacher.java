package com.dflc.cache.iot;

import com.dflc.cache.iot.entity.DeviceCache;
import com.dflc.cache.iot.entity.IndexCache;
import com.dflc.cache.iot.entity.MetricsCache;
import io.vertx.core.json.JsonObject;
import org.legomd.cache.ignite.IgniteCacheBind;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class MetricsCacher extends IgniteCacheBind<MetricsCache> {

    private static MetricsCacher ins;
    DeviceCacher deviceCacher;

    private final ConcurrentHashMap<String, MetricsCache> metricsLocalCache = new ConcurrentHashMap<>();

    public MetricsCache get(String seq) {
        MetricsCache mc = metricsLocalCache.get(seq);
        if (mc == null) {
            mc = getOrFrom(seq);
            if (mc != null)
                metricsLocalCache.put(seq, mc);
        }
        return mc;
    }
    public static MetricsCache getCache(String seq) {
        return get().get(seq);
    }
    public static MetricsCacher get() {
        if (ins == null) {
            ins = new MetricsCacher();
        }
        return ins;
    }


    public MetricsCache saveLocal(MetricsCache value) {
        return metricsLocalCache.put(value.getSeq(), value);
    }

    public MetricsCache save(MetricsCache value) {
        saveLocal(value);
        set(value);
        return value;
    }

    private MetricsCacher() {
        setType(MetricsCache.class);
        deviceCacher = DeviceCacher.get();
    }

    @Override
    public MetricsCache getFrom(Object key) {
        String seq = key.toString();
        DeviceCache device = deviceCacher.getDevice(seq);
        if (device != null) {
            return new MetricsCache(seq, Instant.now(), null, null, device.getId_(), device.getTypeId(),  device.getOrgSeqId(), 0, new JsonObject());
        }
        return null;

    }

}