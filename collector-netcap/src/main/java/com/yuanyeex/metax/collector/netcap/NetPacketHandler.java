/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.collector.netcap;

import com.yuanyeex.metax.collector.Reporter;
import com.yuanyeex.metax.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.DnsPacket;
import org.pcap4j.packet.Packet;

/**
 * @author oex.zh
 * @version NetPacketHandler.java, v 0.1 2022-09-04 01:12 oex.zh
 */
@Getter
@Setter
@AllArgsConstructor
public class NetPacketHandler {

    private final boolean dnsPacket;
    private final boolean tcpTraffic;
    private final Reporter<Event> reporter;

    public void handle(Packet packet) {
        Event event = doConvert(packet);
        if (event != null) {
            reporter.report(event);
        }
    }

    private Event doConvert(Packet packet) {
        if (dnsPacket) {
            Event event = PacketConverter.tryComposeDnsResolveEvent(packet);
            if (event != null) {
                return event;
            }
        }

        if (tcpTraffic) {
            return PacketConverter.tryComposeTcpTraffic(packet);
        }

        return null;
    }

    private boolean isDnsResponsePacket(Packet packet) {
        if (packet instanceof DnsPacket) {
           return ((DnsPacket) packet).getHeader().isResponse();
        }

        Packet payload = packet.getPayload();
        if (payload == null) {
            return false;
        }

        return isDnsResponsePacket(payload);
    }
}
