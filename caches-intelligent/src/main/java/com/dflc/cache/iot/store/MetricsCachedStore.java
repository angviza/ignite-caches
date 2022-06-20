package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.MetricsCache;
import io.vertx.core.json.JsonObject;
import org.legomd.cache.ignite.core.CachedStoreJdbcAdapter;
import org.postgresql.util.PGobject;

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
        COLUMNS = new String[]{"seq", "t", "d", "ty", "o", "status", "up", "down", "m"};
        SQL_DEL = "UPDATE %s SET status = -1 ";
    }

    @Override
    protected void stmt(PreparedStatement st, MetricsCache p) throws SQLException {
        int i = 1;
        st.setString(i++, p.getSeq());
        st.setTimestamp(i++, p.getT() == null ? null : Timestamp.from(p.getT()));
        st.setInt(i++, p.getD());
        st.setInt(i++, p.getTy());
        st.setInt(i++, p.getO());
        st.setInt(i++, p.getStatus());

        st.setTimestamp(i++, p.getUp() == null ? null : Timestamp.from(p.getUp()));
        st.setTimestamp(i++, p.getDown() == null ? null : Timestamp.from(p.getDown()));
        PGobject jo = new PGobject();
        jo.setType("jsonb");
        var m = p.getM();
        jo.setValue(m == null ? "{}" : m.toString());
        st.setObject(i++, jo);
    }

    @Override
    protected CacheEntity<String, MetricsCache> fromRs(ResultSet rs) throws SQLException {
        MetricsCache mc = new MetricsCache();
        int i = 1;
        mc.setSeq(rs.getString(i++));
        mc.setT(rs.getTimestamp(i++).toInstant());
        mc.setD(rs.getInt(i++));
        mc.setTy(rs.getInt(i++));
        mc.setO(rs.getInt(i++));
        mc.setStatus(rs.getInt(i++));
        var up = rs.getTimestamp(i++);
        if (up != null)
            mc.setUp(up.toInstant());
        var down = rs.getTimestamp(i++);
        if (down != null)
            mc.setDown(down.toInstant());

        String m = rs.getString(i++);
        mc.setM(m == null ? new JsonObject() : new JsonObject(m));
        return new CacheEntity<>(mc.getSeq(), mc);
    }
}

