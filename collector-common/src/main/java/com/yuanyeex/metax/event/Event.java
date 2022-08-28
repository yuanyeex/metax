/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.yuanyeex.metax.event.netcap.DnsResolveEvent;
import com.yuanyeex.metax.event.netcap.TcpTrafficEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author oex.zh
 * @version Event.java, v 0.1 2022-08-28 21:29 oex.zh
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TcpTrafficEvent.class, name = "TCP_TRAFFIC"),
        @JsonSubTypes.Type(value = DnsResolveEvent.class, name = "DNS_PACKET")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class Event {
    private final String name;
    private Long epoch;
}
