package com.dflc.caches.iot;


import com.dflc.service.cache.persistence.jooq.tables.pojos.IotMonitorBase;
import org.legomd.cache.ignite.core.CachedStoreJdbcAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 缓存工厂
 */

public class IotMonitorBaseCachedStore extends CachedStoreJdbcAdapter<Long, IotMonitorBase> {

    @Override
    public void init() {
        TABLE_NAME = "iot_monitor_base";
//        KEY = "name";
        COLUMNS = new String[]{"id", "name", "model"};
    }

    @Override
    protected void stmt(PreparedStatement st, IotMonitorBase p) throws SQLException {
        if (p.getId() <= 0) {
            throw new SQLException("id not init");
        }
        st.setLong(1, p.getId());
        st.setString(2, p.getName());
        st.setString(3, p.getModel());
    }

    @Override
    protected CacheEntity fromRs(ResultSet rs) throws SQLException {
        IotMonitorBase iot = new IotMonitorBase();
        iot.setId(rs.getLong(1));
        iot.setName(rs.getString(2));
        iot.setModel(rs.getString(3));
        return new CacheEntity(iot.getId(), iot);
    }

}


//    private static String toCmn(Method cm) {
//        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, cm.getName().substring(3));
//    }
//
//    private static void test() {
//        Method[] fs = IotMonitorBase.class.getDeclaredMethods();
//        Method[] methods = Arrays.stream(fs).filter(r -> r.getName().startsWith("set") && r.getReturnType().equals(IotMonitorBase.class)).toArray(Method[]::new);
//        String[] clms = Arrays.stream(methods).sorted((r1, r2) -> r1.getName().hashCode() > r2.getName().hashCode() ? 1 : -1).map(IotMonitorBaseCachedStore::toCmn).toArray(String[]::new);
//
//        System.out.println(String.join(",", clms));
//        String[] clms1 = new String[clms.length];
//        Arrays.fill(clms1, "?");
//        System.out.println(String.join(",", clms1));
//    }