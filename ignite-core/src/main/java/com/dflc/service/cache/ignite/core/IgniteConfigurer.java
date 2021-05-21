package com.dflc.service.cache.ignite.core;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpring;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

public class IgniteConfigurer {
    private static final String igCfg = "ignite.xml";
    private static final String igniteCfg = "config/ignite.xml";
    private static final String igniteDefCfg = "default-ignite.xml";

    public static IgniteConfiguration config() {
        return config("", false, "127.0.0.1", 47500, 2, 1024, true);
    }

//    public static Ignite ignite(String name, boolean client, String addr, int port, int portRange, int slowClientQueueLimit, boolean persistenceEnabled, CacheConfiguration... cacheConfigurations) {
//        IgniteConfiguration cfg = config(name, client, addr, port, portRange, slowClientQueueLimit, persistenceEnabled, cacheConfigurations);
//        return ignite(cfg, null);
//    }

    public static IgniteConfiguration config(String name, boolean client, String addr, int port, int portRange, int slowClientQueueLimit, boolean persistenceEnabled, CacheConfiguration... cacheConfigurations) {
        IgniteConfiguration cfg = new IgniteConfiguration();

        cfg.setIgniteInstanceName(name);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setDeploymentMode(DeploymentMode.CONTINUOUS);
        cfg.setClientMode(client);

        cfg.setIncludeEventTypes(
                EventType.EVT_TASK_STARTED,
                EventType.EVT_TASK_FINISHED,
                EventType.EVT_TASK_FAILED,
                EventType.EVT_TASK_TIMEDOUT,
                EventType.EVT_TASK_SESSION_ATTR_SET,
                EventType.EVT_TASK_REDUCED,

                EventType.EVT_CACHE_OBJECT_PUT,
                EventType.EVT_CACHE_OBJECT_READ,
                EventType.EVT_CACHE_OBJECT_REMOVED

        );


        //--------------------
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setLocalPort(port);
        discoverySpi.setLocalPortRange(portRange);

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList(addr.concat(":").concat(port + (portRange == 0 ? "" : ".." + (port + portRange)))));
        discoverySpi.setIpFinder(ipFinder);

        cfg.setDiscoverySpi(discoverySpi);

        //========================
        TcpCommunicationSpi commSpi = new TcpCommunicationSpi();

        commSpi.setSlowClientQueueLimit(slowClientQueueLimit);
        commSpi.setLocalAddress(addr);
        commSpi.setLocalPortRange(portRange);
        commSpi.setLocalPort(port - 1000);
        cfg.setCommunicationSpi(commSpi);
        cfg.setCacheConfiguration(cacheConfigurations);
        //data storage configuration
        if (persistenceEnabled) {
            DataStorageConfiguration storageCfg = new DataStorageConfiguration();
            storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
            cfg.setDataStorageConfiguration(storageCfg);
        }
        return cfg;
    }


    public static Ignite ignite(IgniteConfiguration cfg, ApplicationContext... acs) {
        Ignite ignite = null;
        ApplicationContext ac = null;
        try {
            if (acs != null && acs.length == 1) {
                ac = acs[0];
            }
            if (cfg != null) {
                if (ac == null) {
                    ignite = Ignition.start(cfg);
                } else {
                    ignite = IgniteSpring.start(cfg, ac);
                }
            } else {
                ignite = ignite(igniteCfg, ac);
                if (ignite == null) {
                    ignite = ignite(igCfg, ac);
                }
                if (ignite == null) {
                    ignite = ignite(igniteDefCfg, ac);
                }
            }
            igniteConfig(ignite);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ignite;
    }

    private static Ignite ignite(String igCfg, ApplicationContext ac) {
        Ignite ignite = null;
        try {
            if (ac == null) {
                ignite = Ignition.start(igCfg);
            } else {
                ignite = IgniteSpring.start(igCfg, ac);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ignite;
    }

    public static Ignite igniteConfig(Ignite ignite) {
        try {
            ignite.cluster().state(ClusterState.ACTIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ignite;
    }


}
