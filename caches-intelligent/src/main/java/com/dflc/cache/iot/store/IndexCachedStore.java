package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.IndexCache;
import org.legomd.cache.ignite.core.CachedStoreJdbcAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 缓存工厂
 */

public class IndexCachedStore extends CachedStoreJdbcAdapter<String, IndexCache> {

    @Override
    public void init() {
        TABLE_NAME = "iot_monitor_index";
        KEY = "code";
        TYPE_KEY = "varchar";
        COLUMNS = new String[]{"code", "id", "name", "unit", "tag"};
        SQL_DEL = "UPDATE %s SET status = -1 ";
    }

    @Override
    protected void stmt(PreparedStatement st, IndexCache p) throws SQLException {
        if (p.getId() <= 0) {
            throw new SQLException("id not init");
        }
        int i = 1;
        st.setString(i++, p.getCode());
        st.setLong(i++, p.getId());
        st.setString(i++, p.getName());
        st.setString(i++, p.getUnit());
        st.setString(i++, p.getTag());
    }

    @Override
    protected CacheEntity fromRs(ResultSet rs) throws SQLException {
        IndexCache iot = new IndexCache();
        int i = 1;
        iot.setCode(rs.getString(i++));
        iot.setId(rs.getLong(i++));
        iot.setName(rs.getString(i++));
        iot.setUnit(rs.getString(i++));
        iot.setTag(rs.getString(i++));
        return new CacheEntity(iot.getCode(), iot);
    }

}

