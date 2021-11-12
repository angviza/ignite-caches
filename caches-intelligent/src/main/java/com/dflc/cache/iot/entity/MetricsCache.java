package com.dflc.cache.iot.entity;

import com.dflc.iot.model.avro.metrics.Metrics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.legomd.cache.core.Cachedable;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsCache extends Cachedable {
    private String seq;
    private Instant t;

    // device id
    private int d;
    // device type
    private int ty;
    // last increase value
    private double v;
    // total value
    private double tol;
    // org id
    private int o;

    @Override
    public String cacheKey() {
        return seq;
    }

    public MetricsCache update(Instant t, double v, double tol) {
        this.t = t;
        this.v = v;
        this.tol = tol;
        return this;
    }

    public Metrics toAvro() {
        return new Metrics(t, d, ty, v, tol, o);
    }
}
