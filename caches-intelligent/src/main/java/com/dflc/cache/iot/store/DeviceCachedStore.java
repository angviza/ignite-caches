package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.DeviceCache;
import org.legomd.cache.ignite.core.CachedStoreJdbcAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 缓存工厂
 */

public class DeviceCachedStore extends CachedStoreJdbcAdapter<String, DeviceCache> {

    @Override
    public void init() {
        TABLE_NAME = "iot_monitor_base";
        KEY = "seq";
        TYPE_KEY = "varchar";
        COLUMNS = new String[]{"seq", "id", "name", "code", "id_", "org_id", "org_seq_id", "state", "index_id"};
        SQL_DEL = "UPDATE %s SET status = -1 ";
    }

    @Override
    protected void stmt(PreparedStatement st, DeviceCache p) throws SQLException {
        if (p.getId() <= 0) {
            throw new SQLException("id not init");
        }
        int i = 1;
        st.setString(i++, p.getSeq());
        st.setLong(i++, p.getId());
        st.setString(i++, p.getName());
        st.setString(i++, p.getCode());
        st.setInt(i++, p.getId_());
        st.setLong(i++, p.getOrgId());
        st.setInt(i++, p.getOrgSeqId());
        st.setInt(i++, p.getState());
        st.setInt(i++, p.getTypeId());
    }

    @Override
    protected CacheEntity fromRs(ResultSet rs) throws SQLException {
        DeviceCache iot = new DeviceCache();
        int i = 1;
        iot.setSeq(rs.getString(i++));
        iot.setId(rs.getLong(i++));
        iot.setName(rs.getString(i++));
        iot.setCode(rs.getString(i++));
        iot.setId_(rs.getInt(i++));
        iot.setOrgId(rs.getLong(i++));
        iot.setOrgSeqId(rs.getInt(i++));
        iot.setState(rs.getInt(i++));
        iot.setTypeId(rs.getInt(i++));
        return new CacheEntity(iot.getSeq(), iot);
    }

}

