package org.quinn.test.ignite.exp;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

import javax.cache.expiry.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Accessors(chain = true, fluent = true)
public class IgniteTest {
    String key = "a";
    String name = "test";
    static Ignite ignite;
    ExpiryPolicy ep;

    AtomicInteger i = new AtomicInteger(1);


    public static void main(String[] args) {
        try {
            Ignite ignite = Ignition.start("config/ignite.xml");
            IgniteTest.ignite = ignite;
            IgniteTest test1 = new IgniteTest().key("\r\n【a】");
            IgniteTest test2 = new IgniteTest().key("\r\n.                                               【b】 ");
            new Thread(() -> {
                test1.test();
            }).start();
            new Thread(() -> {
                test2.test();
            }).start();
            new Thread(() -> {
                sleep(ThreadLocalRandom.current().nextInt(5));
                test1.crtexp(1);
                test1.set(" test a 1", 0);
                test1.acsexp(2);
                sleep(ThreadLocalRandom.current().nextInt(5));
                test2.crtexp(1);
                test2.set(" test b 1", 0);

            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void test() {
//        acsexp(3);

//        set("test value 1", 1);//1
//        set("test value 2", 1);//2
        System.out.println();
        System.out.println();
        for (int i = 0; i <= 10; i++) {
            System.out.print(i + "  ");
            get(i > 5 ? i - 5 : 1);
        }
        System.out.println();
//        get(1);//3
//        get(1);//4
//        get(1);//5
//        get(1);//6
//        get(1);//7
//        get(2);//8
//        get(3);//9
//        get(4);//10
//        get(5);//11
//        get(1);//12
    }

    private IgniteCache<Object, Object> cache() {
        IgniteCache<Object, Object> cache = ignite.getOrCreateCache(name);
        return ep == null ? cache : cache.withExpiryPolicy(ep);
    }

    private void get(int sec) {
        get(key, sec);
    }

    private void get(Object key, int sec) {
        sleep(sec);
        log(cache().get(key));
    }

//    private static void get(ExpiryPolicy ep, int sec) {
//        log(cache().withExpiryPolicy(ep).get(key));
//        sleep(sec);
//    }

    private void set(Object v, int sec) {
        set(key, v, sec);
    }

    private void set(Object key, Object v, int sec) {
//        sleep(sec);
        log(v);
        cache().put(key, v);
    }

//    private static void set(Object v, ExpiryPolicy ep, int sec) {
//        log(v);
//        cache().withExpiryPolicy(ep).put(key, v);
//        sleep(sec);
//    }

    private void log(Object msg) {
        System.out.println(key + " " +new Date().toLocaleString() + " " + i.getAndAdd(1) + " = " + msg);
    }

    private static void sleep(long sec) {
        try {
            if (sec > 0) {
                Thread.sleep(1000 * sec);
            }
        } catch (InterruptedException e) {
        }
    }

    private void crtexp(int sec) {
        ep = CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sec)).create();
    }

    private void mdfexp(int sec) {
        ep = ModifiedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sec)).create();
    }

    private void acsexp(int sec) {
        ep = AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sec)).create();
    }

    private void tchexp(int sec) {
        ep = TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sec)).create();
    }
}
