package org.quinn.test.ignite.exp;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

import javax.cache.expiry.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class IgniteExpTest {
    static String key = "a";
    static String name = "test";
    static Ignite ignite;
    static ExpiryPolicy ep;

    static AtomicInteger i = new AtomicInteger(1);


    public static void main(String[] args) {
        try {
            try (Ignite ignite = Ignition.start("config/ignite.xml")) {
                IgniteExpTest.ignite = ignite;

                new Thread(
                        () -> {
                            System.out.println("");
                        }
                ).start();
                test();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test() {

        acsexp(3);
        set("test value 1", 1);//1

        get(1);//3
        get(1);//4
        get(1);//5
        get(1);//6
        get(1);//7
        key = "b";
        mdfexp(3);
        set("test value 2", 1);//2
        get(2);//8
        get(3);//9
        get(4);//10
        get(5);//11
        get(1);//12
    }

    private static IgniteCache<Object, Object> cache() {
        IgniteCache<Object, Object> cache = ignite.getOrCreateCache(name);
        return ep == null ? cache : cache.withExpiryPolicy(ep);
    }

    private static void get(int sec) {
        get(key, sec);
    }

    private static void get(Object key, int sec) {
        sleep(sec);
        log(cache().get(key));
    }

//    private static void get(ExpiryPolicy ep, int sec) {
//        log(cache().withExpiryPolicy(ep).get(key));
//        sleep(sec);
//    }

    private static void set(Object v, int sec) {
        set(key, v, sec);
    }

    private static void set(Object key, Object v, int sec) {
        sleep(sec);
        log(v);
        cache().put(key, v);
    }

//    private static void set(Object v, ExpiryPolicy ep, int sec) {
//        log(v);
//        cache().withExpiryPolicy(ep).put(key, v);
//        sleep(sec);
//    }

    private static void log(Object msg) {
        System.out.println(new Date().toLocaleString() + " 【" + key + "】" + i.getAndAdd(1) + " = " + msg);
    }

    private static void sleep(long sec) {
        try {
            if (sec > 0) {
                Thread.sleep(1000 * sec);
            }
        } catch (InterruptedException e) {
        }
    }

    private static void crtexp(int sec) {
        ep = CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sec)).create();
    }

    private static void mdfexp(int sec) {
        ep = ModifiedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sec)).create();
    }

    private static void acsexp(int sec) {
        ep = AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sec)).create();
    }

    private static void tchexp(int sec) {
        ep = TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sec)).create();
    }
}
