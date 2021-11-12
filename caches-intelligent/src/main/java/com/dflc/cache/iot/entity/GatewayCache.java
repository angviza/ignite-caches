package com.dflc.cache.iot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.legomd.cache.core.Cachedable;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayCache extends Cachedable {
    protected Long id;

    protected String name;

    protected String code;

    protected Integer state;

    private Long orgId;

    private String seq;

    private Date onlineTime;

    private String username;

    private String password;

    @Override
    public String cacheKey() {
        return seq;
    }

}
