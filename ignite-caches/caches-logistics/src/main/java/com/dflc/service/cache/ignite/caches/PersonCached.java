package com.dflc.service.cache.ignite.caches;

import com.dflc.service.cache.ignite.core.CachedConfiguration;
import com.dflc.service.cache.persistence.jooq.tables.pojos.CrmUser;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存工厂
 */
@Configuration
public class PersonCached extends CachedConfiguration<Long, CrmUser> {

    public PersonCached() {
        this(Long.class, CrmUser.class, PersonStore.class);
    }

    public PersonCached(Class key, Class clazz, Class store) {
        super(key, clazz, store);
    }

}
