package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.MetricsCache;
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

public class MetricsCachedStore extends CachedStoreJdbcAdapter<String, MetricsCache> {

    @Override
    public void init() {
        TABLE_NAME = "iot_monitor_metrics";
        KEY = "seq";
        TYPE_KEY = "varchar";
        COLUMNS = new String[]{"seq","t", "d", "ty", "v", "tol", "o" ,"status"};
        SQL_DEL = "UPDATE %s SET status = -1 ";
    }

    @Override
    protected void stmt(PreparedStatement st, MetricsCache p) throws SQLException {
        int i = 1;
        st.setString(i++, p.getSeq());
        st.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.ofInstant(p.getT(), ZoneId.of("Asia/Shanghai"))));
        st.setInt(i++, p.getD());
        st.setInt(i++, p.getTy());
        st.setDouble(i++, p.getV());
        st.setDouble(i++, p.getTol());
        st.setInt(i++, p.getO());
        st.setInt(i++, p.getStatus());
    }

    @Override
    protected CacheEntity fromRs(ResultSet rs) throws SQLException {
        MetricsCache mc = new MetricsCache();
        int i = 1;
        mc.setSeq(rs.getString(i++));
        mc.setT(rs.getTimestamp(i++).toInstant());
        mc.setD(rs.getInt(i++));
        mc.setTy(rs.getInt(i++));
        mc.setV(rs.getDouble(i++));
        mc.setTol(rs.getDouble(i++));
        mc.setO(rs.getInt(i++));
        mc.setStatus(rs.getInt(i++));
        return new CacheEntity(mc.getSeq(), mc);
    }

}

