package com.dflc.cache.iot.entity;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.legomd.cache.core.Cachedable;
import org.legomd.util.param.ParamUtil;

import java.time.Instant;
import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MetricsCache extends Cachedable {
    private String seq;
    private Instant t;
    private Instant up;
    private Instant down;

    // device id
    private int d;
    // device type
    private int ty;
    // org id
    private int o;
    // status
    private int status;

    private JsonObject m;

    @Override
    public String cacheKey() {
        return seq;
    }

    public MetricsCache set(Object k, Object v) {
        m.put(String.valueOf(k), v);
        return this;
    }

    public MetricsCache putAll(Map<String, Object> map) {
        m.getMap().putAll(map);
        return this;
    }

    public <T> T get(Object k_, Object def) {
        String k = String.valueOf(k_);
        return (T) m.getValue(k, def);

    }

    public <T> T get(Object k_) {
        String k = String.valueOf(k_);
        return (T) m.getValue(k);
    }

    public <T> T get() {
        return (T) m.stream().findFirst().get().getValue();
    }

}
