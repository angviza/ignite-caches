package com.dflc.service.cache.ignite.core;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.SpringResource;
import org.jetbrains.annotations.Nullable;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public abstract class CachedStoreAdapter<K, V> extends CacheStoreAdapter<K, V> {
    @SpringResource(resourceName = "dataSource")
    private DataSource dataSource;

    protected String TABLE_NAME;
    protected String TYPE_ID = "int8";
    protected String SQL_DEL = "DELETE FROM ";
    protected String SQL_QUERY = "SELECT * FROM ";
    protected String SQL_WRITE;

    public CachedStoreAdapter() {
        init();
        SQL_DEL = SQL_DEL.concat(TABLE_NAME);
        SQL_QUERY = SQL_QUERY.concat(TABLE_NAME);
    }
    public CachedStoreAdapter(DataSource dataSource){
        this();
        this.dataSource=dataSource;
    }

    protected abstract void init();

    protected abstract void stmt(PreparedStatement st, V p) throws SQLException;

    @Data
    @AllArgsConstructor
    @Accessors(chain = true, fluent = true)
    protected static class CacheEntity<K, V> {
        K k;
        V v;
    }

    protected abstract CacheEntity fromRs(ResultSet rs) throws SQLException;

    @Override
    public void loadCache(IgniteBiInClosure<K, V> igniteBiInClosure, @Nullable Object... objects) throws CacheLoaderException {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(SQL_QUERY.concat(" limit 1000"));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CacheEntity ce = fromRs(rs);
                igniteBiInClosure.apply((K) ce.k(), (V) ce.v());
            }
        } catch (Exception e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }

    }

    @Override
    public V load(K aLong) throws CacheLoaderException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(SQL_QUERY.concat(" where id = ?"))) {
                st.setLong(1, (Long) aLong);
                ResultSet rs = st.executeQuery();
                return rs.next() ? (V) fromRs(rs).v() : null;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }

    @Override
    public Map<K, V> loadAll(Iterable<? extends K> keys) throws CacheLoaderException {
        List<K> ids = new ArrayList<>();
        keys.forEach(ids::add);
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(SQL_QUERY.concat(" where id=ANY(?)"))) {
                st.setArray(1, conn.createArrayOf(TYPE_ID, ids.toArray()));
                ResultSet rs = st.executeQuery();
                Map<K, V> maps = new HashMap<>();
                while (rs.next()) {
                    CacheEntity ce = fromRs(rs);
                    maps.put((K) ce.k(), (V) ce.v());
                }
                return maps;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }


    @Override
    public void write(Cache.Entry<? extends K, ? extends V> entry) throws CacheWriterException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement st = conn.prepareStatement(SQL_WRITE);
            stmt(st, entry.getValue());
            int i = st.executeUpdate();
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to add person  to table store.", e);
        }
    }


    @Override
    public void writeAll(Collection<Cache.Entry<? extends K, ? extends V>> collection) throws CacheWriterException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement st = conn.prepareStatement(SQL_WRITE);
            for (Cache.Entry<? extends K, ? extends V> entry : collection) {
                stmt(st, entry.getValue());
                st.addBatch();
            }
            st.executeBatch();
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to add person  to table store.", e);
        }
    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement st = conn.prepareStatement(SQL_DEL.concat(" WHERE id=?"));
            st.setLong(1, (Long) o);
            st.execute();
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to deleteAll person  to table store.", e);
        }
    }


    @Override
    public void deleteAll(Collection<?> collection) throws CacheWriterException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement st = conn.prepareStatement(SQL_DEL.concat(" WHERE id=ANY(?)"));
            st.setArray(1, conn.createArrayOf(TYPE_ID, collection.toArray()));
            st.execute();
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to deleteAll person  to table store.", e);
        }
    }

    /**
     * @param b
     * @deprecated
     */
    @Override
    public void sessionEnd(boolean b) throws CacheWriterException {

    }
}
