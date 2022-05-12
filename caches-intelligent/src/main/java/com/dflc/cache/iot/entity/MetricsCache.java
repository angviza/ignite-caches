package com.dflc.cache.iot.entity;

import io.vertx.core.json.JsonObject;
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
    private Double v;
    // total value
    private Double tol;
    // org id
    private int o;
    // status
    private int status;

    //当前预警值
    private double wv;

    //当前预警次数
    private int wc;
    private Object metrics;

    @Override
    public String cacheKey() {
        return seq;
    }

    public MetricsCache update(Instant t, Double v, Double tol) {
        this.t = t;
        this.v = v;
        this.tol = tol;
        return this;
    }

}
