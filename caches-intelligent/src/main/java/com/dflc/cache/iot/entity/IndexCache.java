package com.dflc.cache.iot.entity;

import lombok.Data;
import org.legomd.cache.core.Cachedable;

import java.util.Date;

@Data
public class IndexCache extends Cachedable {

    protected Long id;
    protected String name;
    private String code;
    private String unit;
    private String tag;

    @Override
    public String cacheKey() {
        return this.getCode();
    }

    public void clean() {
        this.id = null;
        this.name = null;
        this.unit = null;
        this.tag = null;
        this.code = null;
    }
}
