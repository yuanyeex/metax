/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.model;

import lombok.Data;

/**
 * @author oex.zh
 * @version DnsResolvePacket.java, v 0.1 2022-08-27 23:08 oex.zh
 */
@Data
public class DnsResolvePacket {
    private String query;
    private String recordType;
    private String dnsClass;
    private String response;
    private int ttl;
}
