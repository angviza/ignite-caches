package com.dflc.caches.iot;


import com.dflc.service.cache.persistence.jooq.tables.pojos.IotMonitorBase;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.meta.mysql.information_schema.tables.Columns;
import org.legomd.cache.ignite.core.CachedStoreAdapter;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 缓存工厂
 */

public class IotMonitorBaseCachedStore extends CachedStoreAdapter<String, IotMonitorBase> {

    public IotMonitorBaseCachedStore() {
        super();
    }

    public IotMonitorBaseCachedStore(DataSource dataSource) {
        super(dataSource);
    }

    public static void main(String[] args) {
        DSL.using(ds, SQLDialect.POSTGRES);
        DSL.selectOne().from(com.dflc.service.cache.persistence.jooq.tables.IotMonitorBase.IOT_MONITOR_BASE).fetch();
        String cms[]=Arrays.stream(com.dflc.service.cache.persistence.jooq.tables.IotMonitorBase.IOT_MONITOR_BASE.fields()).map(Field::getName).toArray(String[]::new);
        System.out.println(String.join(",",cms));
        String[]cms1=new String[cms.length];
        Arrays.fill(cms,"?");
        System.out.println(String.join(",",cms));

//       Field[]fs= com.dflc.service.cache.persistence.jooq.tables.IotMonitorBase.IOT_MONITOR_BASE.fields();
//       for (var f:fs){
//           System.out.println(f.getName());
//           System.out.println(f.getType());
//       }
    }
    private void test(){
        String[] cms="id, name".split(",");
        String[] cms1=new String[cms.length];
        int i=0;
        for (String cm:cms) {
            cms1[i++]=String.format("%s = EXCLUDED.%s",cm,cm);
        }
        System.out.println(String.join(",",cms1));
    }
    @Override
    public void init() {

        TABLE_NAME = "iot_monitor_base";
        COLUMNS = "id, name";
        SQL_WRITE = String.format("INSERT INTO iot_monitor_base(%s) VALUES (?,?) ON CONFLICT(%s) DO UPDATE SET name = EXCLUDED.name", COLUMNS,getp(), KEY);


    }

    private String getp() {
        String[] cms = COLUMNS.split("[,]");
        Arrays.fill(cms, "?");
        return String.join(",", cms);
    }

    @Override
    protected void stmt(PreparedStatement st, IotMonitorBase p) throws SQLException {
        if (p.getId() <= 0) {
            throw new SQLException("id not inti");
        }
        st.setLong(1, p.getId());
        st.setLong(2, p.getOrgId());
//        DSL.alterTable()
//        st.setString(2, p.getName());
//        p.getClass().getField("");
//                st.getClass().getMethod("setLong",Long.class);
    }

    @Override
    protected CacheEntity fromRs(ResultSet rs) throws SQLException {
//        CrmUser person = new CrmUser(rs.getLong(1), rs.getLong(2), rs.getString(3));
//        return new CacheEntity(person.getId(), person);
        IotMonitorBase iot = new IotMonitorBase();
        iot.setId(rs.getLong(1));
        iot.setName(rs.getString(2));
//        iot.setStatus(rs.getShort());
        return new CacheEntity(iot.getId(), iot);
    }

}
