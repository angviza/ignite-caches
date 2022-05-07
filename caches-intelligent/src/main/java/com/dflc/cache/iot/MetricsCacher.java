package com.dflc.cache.iot;

import com.dflc.cache.iot.entity.DeviceCache;
import com.dflc.cache.iot.entity.IndexCache;
import com.dflc.cache.iot.entity.MetricsCache;
import org.legomd.cache.ignite.IgniteCacheBind;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

public class MetricsCacher extends IgniteCacheBind<MetricsCache> {

    private static MetricsCacher ins;
    DeviceCacher deviceCacher;
    IndexCacher indexCacher;

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

    public MetricsCache saveLocal(MetricsCache value) {
        return metricsLocalCache.put(value.getSeq(), value);
    }
    public MetricsCache saveMetrics(MetricsCache value) {
        saveLocal(value);
        set(value);
        return value;
    }

    private MetricsCacher() {
        setType(MetricsCache.class);
        deviceCacher = DeviceCacher.getDeviceCache();
    }

    @Override
    public MetricsCache getFrom(Object key) {
        String seq_metrics = key.toString();
        String seq = seq_metrics;
        String indexCode = null;

        if (seq_metrics.contains("-")) {
            int _i = seq_metrics.lastIndexOf("-");
            seq = seq_metrics.substring(0, _i);
            indexCode = seq_metrics.substring(_i + 1);
        }

        DeviceCache device = deviceCacher.getDevice(seq);

        IndexCache  index = indexCode==null?null:indexCacher.getIndex(indexCode);

        if (device != null) {
            MetricsCache metricsCache = new MetricsCache(seq, null, null, null, device.getId_(), device.getIndexId(), 0, 0, device.getOrgSeqId(), 0, 0, 0);
            if (index != null) {
                metricsCache.setTy(Integer.valueOf(index.getId().toString()));
            }
            return metricsCache;
        }
        return null;
    }

}
