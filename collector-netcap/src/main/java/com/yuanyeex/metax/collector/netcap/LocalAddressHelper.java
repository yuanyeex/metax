/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.collector.netcap;

import java.net.InetAddress;

/**
 * @author oex.zh
 * @version LocalAddressHelper.java, v 0.1 2022-09-04 01:56 oex.zh
 */
public class LocalAddressHelper {

    private static final Object LOCK = new Object();
    private static String localAddress = null;

    public static String getLocalAddress() {
        if (localAddress == null) {
            synchronized (LOCK) {
                localAddress = resolveAddress();
            }
        }
        return localAddress;
    }

    private static String resolveAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
