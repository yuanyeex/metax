/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.event.netcap;

import com.yuanyeex.metax.event.Event;
import com.yuanyeex.metax.event.EventNameEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author oex.zh
 * @version NetPacket.java, v 0.1 2022-08-27 22:59 oex.zh
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class TcpTrafficEvent extends Event {
    /**
     * src address
     */
    private String srcAddr;
    /**
     * src port
     */
    private Integer srcPort;
    /**
     * dst address
     */
    private String dstAddr;
    /**
     * dest port
     */
    private Integer dstPort;
    /**
     * tcp length
     */
    private Long length;

    public TcpTrafficEvent() {super(EventNameEnum.TCP_TRAFFIC.name());}
}
