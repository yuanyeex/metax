/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.model;

import lombok.Data;

/**
 * @author oex.zh
 * @version NetPacket.java, v 0.1 2022-08-27 22:59 oex.zh
 */
@Data
public class TcpPacket {
    /**
     * src address
     */
    private String srcAddr;
    /**
     * src port
     */
    private int srcPort;
    /**
     * dst address
     */
    private String dstAddr;
    /**
     * dest port
     */
    private int dstPort;
    /**
     * tcp length
     */
    private int length;
}
