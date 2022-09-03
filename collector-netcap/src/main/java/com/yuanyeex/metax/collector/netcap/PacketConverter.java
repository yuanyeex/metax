/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.collector.netcap;

import com.yuanyeex.metax.event.Event;
import com.yuanyeex.metax.event.netcap.CompactEvent;
import com.yuanyeex.metax.event.netcap.DnsResolveEvent;
import org.pcap4j.packet.DnsPacket;
import org.pcap4j.packet.DnsQuestion;
import org.pcap4j.packet.DnsRDataA;
import org.pcap4j.packet.DnsRDataAaaa;
import org.pcap4j.packet.DnsRDataCName;
import org.pcap4j.packet.DnsRDataHInfo;
import org.pcap4j.packet.DnsRDataMInfo;
import org.pcap4j.packet.DnsRDataMb;
import org.pcap4j.packet.DnsRDataMd;
import org.pcap4j.packet.DnsRDataMf;
import org.pcap4j.packet.DnsRDataMg;
import org.pcap4j.packet.DnsRDataPtr;
import org.pcap4j.packet.DnsRDataTxt;
import org.pcap4j.packet.DnsResourceRecord;
import org.pcap4j.packet.Packet;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author oex.zh
 * @version PacketConvert.java, v 0.1 2022-09-04 00:46 oex.zh
 */
public class PacketConverter {

    public static Event tryComposeDnsResolveEvent(Packet packet) {
        return tryGetDnsResponse(packet);
    }

    /**
     * Not supported yet
     *
     * @param packet packet
     * @return event
     */
    public static Event tryComposeTcpTraffic(Packet packet) {
        return null;
    }

    private static CompactEvent tryGetDnsResponse(Packet packet) {
        return resolveDnsResponsePacket(packet);
    }

    private static List<Event> toCompactEvents(DnsPacket.DnsHeader dnsHeader) {
        List<DnsQuestion> questions = dnsHeader.getQuestions();
        if (questions.isEmpty()) {
            return null;
        }
        DnsQuestion dnsQuestion = questions.get(0);

        List<DnsResourceRecord> answers = dnsHeader.getAnswers();
        return answers.stream()
                .filter(Objects::nonNull)
                .map(record -> PacketConverter.toDnsResolveEvent(dnsQuestion, record))
                .collect(Collectors.toList());
    }

    private static DnsResolveEvent toDnsResolveEvent(DnsQuestion dnsQuestion, DnsResourceRecord dnsResourceRecord) {
        DnsResolveEvent dnsResolveEvent = new DnsResolveEvent();

        dnsResolveEvent.setQuery(dnsQuestion.getQName().getName());
        dnsResolveEvent.setHost(LocalAddressHelper.getLocalAddress());
        dnsResolveEvent.setTtl(dnsResourceRecord.getTtl());
        dnsResolveEvent.setDnsClass(dnsResourceRecord.getDataClass().valueAsString());
        dnsResolveEvent.setRecordType(dnsResourceRecord.getDataType().name());
        dnsResolveEvent.setResponse(convertDnsRdata(dnsResourceRecord.getRData()));
        dnsResolveEvent.setEpoch(System.currentTimeMillis());

        return dnsResolveEvent;
    }

    private static String convertDnsRdata(DnsResourceRecord.DnsRData dnsRdata) {
        if (dnsRdata instanceof DnsRDataA) {
            return ((DnsRDataA) dnsRdata).getAddress().getHostAddress();
        } else if (dnsRdata instanceof DnsRDataAaaa) {
            return ((DnsRDataAaaa) dnsRdata).getAddress().getHostAddress();
        } else if (dnsRdata instanceof DnsRDataCName) {
            return ((DnsRDataCName) dnsRdata).getCName().getName();
        } else if (dnsRdata instanceof DnsRDataMb) {
            return ((DnsRDataMb) dnsRdata).getMaDName().getName();
        } else if (dnsRdata instanceof DnsRDataTxt) {
            return String.join(",", ((DnsRDataTxt) dnsRdata).getTexts());
        } else if (dnsRdata instanceof DnsRDataPtr) {
            return ((DnsRDataPtr) dnsRdata).getPtrDName().getName();
        } else if (dnsRdata instanceof DnsRDataMg) {
            return ((DnsRDataMg) dnsRdata).getMgMName().getName();
        } else if (dnsRdata instanceof DnsRDataMInfo) {
            return ((DnsRDataMInfo) dnsRdata).getRMailBx().getName() + "," + ((DnsRDataMInfo) dnsRdata).getEMailBx().getName();
        } else if (dnsRdata instanceof DnsRDataMf) {
            return ((DnsRDataMf) dnsRdata).getMaDName().getName();
        } else if (dnsRdata instanceof DnsRDataMd) {
            return ((DnsRDataMd) dnsRdata).getMaDName().getName();
        } else  {
            return ((DnsRDataHInfo) dnsRdata).toString();
        }
    }

    private static CompactEvent resolveDnsResponsePacket(Packet packet) {
        if (packet instanceof DnsPacket) {
            DnsPacket dnsPacket = (DnsPacket) packet;
            if (dnsPacket.getHeader().isResponse()) {
                return composeEvent(dnsPacket);
            }
            return null;
        }

        Packet payload = packet.getPayload();
        if (payload == null) {
            return null;
        }

        return resolveDnsResponsePacket(payload);
    }

    private static CompactEvent composeEvent(DnsPacket dnsPacket) {
        DnsPacket.DnsHeader header = dnsPacket.getHeader();
        List<Event> dnsResolveEvents = toCompactEvents(header);
        return CompactEvent.compact(dnsResolveEvents);
    }

}
