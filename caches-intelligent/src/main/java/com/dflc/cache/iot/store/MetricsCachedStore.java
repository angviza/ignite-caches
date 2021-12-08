package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.MetricsCache;
import org.legomd.cache.ignite.core.CachedStoreJdbcAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 缓存工厂
 */

public class MetricsCachedStore extends CachedStoreJdbcAdapter<String, MetricsCache> {

    @Override
    public void init() {
        TABLE_NAME = "iot_monitor_metrics";
        KEY = "seq";
        TYPE_KEY = "varchar";
        COLUMNS = new String[]{"seq", "t", "d", "ty", "v", "tol", "o", "status", "up", "down"};
        SQL_DEL = "UPDATE %s SET status = -1 ";
    }

    @Override
    protected void stmt(PreparedStatement st, MetricsCache p) throws SQLException {
        int i = 1;
        st.setString(i++, p.getSeq());
//        st.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.ofInstant(p.getT(), ZoneId.of("Asia/Shanghai"))));
        st.setTimestamp(i++, p.getT() == null ? null : Timestamp.from(p.getT()));
        st.setInt(i++, p.getD());
        st.setInt(i++, p.getTy());
        st.setDouble(i++, p.getV());
        st.setDouble(i++, p.getTol());
        st.setInt(i++, p.getO());
        st.setInt(i++, p.getStatus());

        st.setTimestamp(i++, p.getUp() == null ? null : Timestamp.from(p.getUp()));
        st.setTimestamp(i, p.getDown() == null ? null : Timestamp.from(p.getDown()));
    }

    @Override
    protected CacheEntity<String, MetricsCache> fromRs(ResultSet rs) throws SQLException {
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
        var up = rs.getTimestamp(i++);
        if (up != null)
            mc.setUp(up.toInstant());
        var down = rs.getTimestamp(i++);
        if (down != null)
            mc.setDown(down.toInstant());
        return new CacheEntity<String, MetricsCache>(mc.getSeq(), mc);
    }
}

