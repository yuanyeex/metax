/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.event.netcap;

import com.yuanyeex.metax.event.Event;
import com.yuanyeex.metax.event.EventNameEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author oex.zh
 * @version DnsResolvePacket.java, v 0.1 2022-08-27 23:08 oex.zh
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class DnsResolveEvent extends Event {
    /**
     * Queried dns
     */
    private String query;
    /**
     * Dns record type
     */
    private String recordType;
    /**
     * Dns class
     */
    private String dnsClass;
    /**
     * response data
     */
    private String response;
    /**
     * ttl
     */
    private Integer ttl;
    /**
     * host queries the dns
     */
    private String host;

    public DnsResolveEvent() {
        super(EventNameEnum.DNS_PACKET.name());}
}
