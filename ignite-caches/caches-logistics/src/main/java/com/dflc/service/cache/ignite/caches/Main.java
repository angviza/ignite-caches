package com.dflc.service.cache.ignite.caches;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    static String key = "a";
    static String name = "test";
    static Ignite ignite;
    static ExpiryPolicy ep;

    static AtomicInteger i=new AtomicInteger(1);

    private static IgniteCache<Object, Object> cache() {
        IgniteCache<Object, Object> cache = ignite.getOrCreateCache(name);
        return ep == null ? cache : cache.withExpiryPolicy(ep);
    }

    private static void get(int sec) {
        log(cache().get(key));
        sleep(sec);
    }

    private static void get(ExpiryPolicy ep, int sec) {
        log(cache().withExpiryPolicy(ep).get(key));
        sleep(sec);
    }

    private static void set(Object v, int sec) {
        log(v);
        cache().put(key, v);
        sleep(sec);
    }

    private static void set(Object v, ExpiryPolicy ep, int sec) {
        log(v);
        cache().withExpiryPolicy(ep).put(key, v);
        sleep(sec);
    }

    private static void log(Object msg) {
        System.out.println(new Date().toLocaleString() + " 【" + key + "】"+i.getAndAdd(1)+" = " + msg);
    }

    private static void sleep(long sec) {
        try {
            if (sec > 0) {
                Thread.sleep(1000 * sec);
            }
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        try {

            try (Ignite ignite = Ignition.start("config/ignite.xml")) {
                Main.ignite = ignite;


//                ExpiryPolicy ep = null;
//                ep = CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 3)).create();
//                ep = ModifiedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 3)).create();
                ep = AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 3)).create();
//                ep = TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 3)).create();

                set("test value 1", 1);
                set("test value 2", 1);
                get(1);
                get(1);
//                ep = null;
                get(1);
                get(1);
                get(2);
                get(3);
                get(4);
                get(5);
                get(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
