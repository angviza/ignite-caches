package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.GatewayCache;
import org.legomd.cache.ignite.core.CachedStoreJdbcAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 缓存工厂
 */

public class GatewayCachedStore extends CachedStoreJdbcAdapter<String, GatewayCache> {

    @Override
    public void init() {
        TABLE_NAME = "iot_monitor_gateway";
        KEY = "seq";
        TYPE_KEY = "varchar";
        COLUMNS = new String[]{"seq","id", "name", "code","username","password","state"};
        SQL_DEL = "UPDATE %s SET status = -1 ";
    }

    @Override
    protected void stmt(PreparedStatement st, GatewayCache p) throws SQLException {
        if (p.getId() <= 0) {
            throw new SQLException("id not init");
        }

        int i = 1;
        st.setString(i++, p.getSeq());
        st.setLong(i++, p.getId());
        st.setString(i++, p.getName());
        st.setString(i++, p.getCode());
        st.setString(i++, p.getUsername());
        st.setString(i++, p.getPassword());
        st.setInt(i++,p.getState());

    }

    @Override
    protected CacheEntity fromRs(ResultSet rs) throws SQLException {
        GatewayCache gt = new GatewayCache();
        int i = 1;
        gt.setSeq(rs.getString(i++));
        gt.setId(rs.getLong(i++));
        gt.setName(rs.getString(i++));
        gt.setCode(rs.getString(i++));
        gt.setUsername(rs.getString(i++));
        gt.setPassword(rs.getString(i++));
        gt.setState(rs.getInt(i++));
        return new CacheEntity(gt.getSeq(), gt);
    }

}

