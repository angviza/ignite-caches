package com.dflc.cache.iot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.legomd.cache.core.Cachedable;

import java.time.Instant;

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
    // last increase value
    private double v;
    // total value
    private double tol;
    // org id
    private int o;
    // status
    private int status;

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

}
