package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.GatewayCache;
import org.legomd.cache.ignite.core.CachedStoreJdbcAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 缓存工厂
 */

public class GatewayCachedStore extends CachedStoreJdbcAdapter<String, GatewayCache> {

    @Override
    public void init() {
        TABLE_NAME = "iot_monitor_gateway_state";
        KEY = "seq";
        TYPE_KEY = "varchar";
        COLUMNS = new String[]{"seq", "st", "et", "status", "o"};
        SQL_DEL = "UPDATE %s SET status = -1 ";
    }

    @Override
    protected void stmt(PreparedStatement st, GatewayCache p) throws SQLException {
        int i = 1;
        st.setString(i++, p.getSeq());
        st.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.ofInstant(p.getUpTime().toInstant(), ZoneId.of("Asia/Shanghai"))));
        st.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.ofInstant(p.getDownTime().toInstant(), ZoneId.of("Asia/Shanghai"))));
        st.setInt(i++, p.getState());
        //st.setInt(i++, p.getO());
    }

    @Override
    protected CacheEntity fromRs(ResultSet rs) throws SQLException {
        GatewayCache gt = new GatewayCache();
        int i = 1;
        gt.setSeq(rs.getString(i++));
        gt.setUpTime(rs.getTimestamp(i++));
        gt.setDownTime(rs.getTimestamp(i++));
        gt.setState(rs.getInt(i++));
        //gt.setO(rs.getInt(i++));
        return new CacheEntity(gt.getSeq(), gt);
    }

}

