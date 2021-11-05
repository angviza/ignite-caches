package com.dflc.cache.iot.store;


import com.dflc.cache.iot.entity.Device;
import org.legomd.cache.ignite.core.CachedStoreJdbcAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 缓存工厂
 */

public class DeviceCachedStore extends CachedStoreJdbcAdapter<String, Device> {

    @Override
    public void init() {
        TABLE_NAME = "iot_monitor_base";
        KEY = "seq";
        TYPE_KEY = "varchar";
        COLUMNS = new String[]{"seq","id", "name", "code","id_","org_id","org_seq_id","state"};
        SQL_DEL = "UPDATE %s SET status = -1 ";
    }

    @Override
    protected void stmt(PreparedStatement st, Device p) throws SQLException {
        if (p.getId() <= 0) {
            throw new SQLException("id not init");
        }
        st.setString(1, p.getSeq());
        st.setLong(2, p.getId());
        st.setString(3, p.getName());
        st.setString(4, p.getCode());
        st.setInt(5, p.getId_());
        st.setLong(6, p.getOrgId());
        st.setInt(7, p.getOrgSeqId());
        st.setInt(8,p.getState());
    }

    @Override
    protected CacheEntity fromRs(ResultSet rs) throws SQLException {
        Device iot = new Device();
        iot.setSeq(rs.getString(1));
        iot.setId(rs.getLong(2));
        iot.setName(rs.getString(3));
        iot.setCode(rs.getString(4));
        iot.setId_(rs.getInt(5));
        iot.setOrgId(rs.getLong(6));
        iot.setOrgSeqId(rs.getInt(7));
        iot.setState(rs.getInt(8));
        return new CacheEntity(iot.getSeq(), iot);
    }

}

