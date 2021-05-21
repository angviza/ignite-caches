package com.dflc.service.cache.ignite.caches;


import com.dflc.service.cache.ignite.core.CachedStoreAdapter;
import com.dflc.service.cache.persistence.jooq.tables.pojos.CrmUser;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 缓存工厂
 */

public class PersonStore extends CachedStoreAdapter<Long, CrmUser> {

    public PersonStore() {
        super();
    }

    public PersonStore(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void init() {
        TABLE_NAME = "crm_user";
        SQL_WRITE = "INSERT INTO public.crm_user(id, name) VALUES (?,?) ON CONFLICT(id) DO UPDATE SET name = EXCLUDED.name";
    }

    @Override
    protected void stmt(PreparedStatement st, CrmUser p) throws SQLException {
        if (p.getId() <= 0) {
            throw new SQLException("id not inti");
        }
        st.setLong(1, p.getId());
//        st.setLong(2, p.getOrgId());
        st.setString(2, p.getName());
    }

    @Override
    protected CacheEntity fromRs(ResultSet rs) throws SQLException {
//        CrmUser person = new CrmUser(rs.getLong(1), rs.getLong(2), rs.getString(3));
//        return new CacheEntity(person.getId(), person);
        CrmUser user = new CrmUser();
        user.setId(rs.getLong(1));
        user.setName(rs.getString(2));
        return new CacheEntity(user.getId(), user);
    }


}
