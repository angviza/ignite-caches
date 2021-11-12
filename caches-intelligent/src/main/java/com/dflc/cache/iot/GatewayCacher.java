package com.dflc.cache.iot;

import com.dflc.cache.iot.entity.GatewayCache;
import org.legomd.cache.core.Cacheable;
import org.legomd.cache.ignite.IgniteCacheBind;

import java.util.concurrent.ConcurrentHashMap;

public class GatewayCacher extends IgniteCacheBind<GatewayCache> {

    private static GatewayCacher ins;

    private final ConcurrentHashMap<String, GatewayCache> metricsLocalCache = new ConcurrentHashMap<>();

    public GatewayCache getCache(String seq) {
        GatewayCache mc = metricsLocalCache.get(seq);
        if (mc == null) {
            mc = getOrFrom(seq);
            if (mc != null)
                metricsLocalCache.put(seq, mc);
        }
        return mc;
    }

    public static GatewayCacher i() {
        if (ins == null) {
            ins = new GatewayCacher();
        }
        return ins;
    }

    @Override
    public <E extends Cacheable> boolean set(E value) {
        metricsLocalCache.put(value.cacheKey().toString(), (GatewayCache) value);
        return super.set(value);
    }

    @Override
    public boolean del(Object key) {
        metricsLocalCache.remove(key);
        return super.del(key);
    }

    private GatewayCacher() {
        shared();
        setType(GatewayCache.class);
    }


    @Override
    public GatewayCache getFrom(Object key) {
        return get(key);
    }
}
