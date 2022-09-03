/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yuanyeex.metax.event.netcap.CompactEvent;
import com.yuanyeex.metax.event.netcap.DnsResolveEvent;
import com.yuanyeex.metax.event.netcap.TcpTrafficEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oex.zh
 * @version EventTest.java, v 0.1 2022-08-28 22:40 oex.zh
 */
public class EventTest {

    @Test
    public void testTcpTraffic() throws JsonProcessingException {

        TcpTrafficEvent tcpTrafficEvent = new TcpTrafficEvent();
        tcpTrafficEvent.setEpoch(1661698423501L);
        tcpTrafficEvent.setSrcAddr("127.0.0.1");
        tcpTrafficEvent.setSrcPort(129);
        tcpTrafficEvent.setDstAddr("192.168.31.1");
        tcpTrafficEvent.setDstPort(1022);
        tcpTrafficEvent.setLength(100023L);

        String eventJson = EventMapper.mapper(tcpTrafficEvent);
        String expectedJson = "{\"name\":\"TCP_TRAFFIC\",\"name\":\"TCP_TRAFFIC\",\"epoch\":1661698423501,\"src_addr\":\"127.0.0.1\","
                + "\"src_port\":129,\"dst_addr\":\"192.168.31.1\",\"dst_port\":1022,\"length\":100023}";

        System.out.println(eventJson);
        Assertions.assertEquals(expectedJson, eventJson);

        Event event = EventMapper.parse(eventJson);
        Assertions.assertNotNull(event);
        Assertions.assertTrue(event instanceof TcpTrafficEvent);
        Assertions.assertEquals(tcpTrafficEvent, event);
    }

    @Test
    public void testDnsResolveEvent() throws JsonProcessingException {
        DnsResolveEvent dnsResolveEvent = new DnsResolveEvent();
        dnsResolveEvent.setQuery("www.baidu.com");
        dnsResolveEvent.setDnsClass("11");
        dnsResolveEvent.setRecordType("A");
        dnsResolveEvent.setResponse("123.123.12.12");

        String parsed = EventMapper.mapper(dnsResolveEvent);
        System.out.println(parsed);
        String expected = "{\"name\":\"DNS_PACKET\",\"name\":\"DNS_PACKET\",\"query\":\"www.baidu.com\",\"record_type\":\"A\",\"dns_class\":\"11\","
                + "\"response\":\"123.123.12.12\"}";
        Assertions.assertEquals(expected, parsed);

        Event event = EventMapper.parse(expected);
        Assertions.assertNotNull(event);
        Assertions.assertTrue(event instanceof DnsResolveEvent);
        Assertions.assertEquals(dnsResolveEvent, event);
    }

    @Test
    public void testCompactEvent() throws JsonProcessingException {
        DnsResolveEvent dnsResolveEvent = new DnsResolveEvent();
        dnsResolveEvent.setQuery("www.baidu.com");
        dnsResolveEvent.setDnsClass("11");
        dnsResolveEvent.setRecordType("A");
        dnsResolveEvent.setResponse("123.123.12.12");

        TcpTrafficEvent tcpTrafficEvent = new TcpTrafficEvent();
        tcpTrafficEvent.setEpoch(1661698423501L);
        tcpTrafficEvent.setSrcAddr("127.0.0.1");
        tcpTrafficEvent.setSrcPort(129);
        tcpTrafficEvent.setDstAddr("192.168.31.1");
        tcpTrafficEvent.setDstPort(1022);
        tcpTrafficEvent.setLength(100023L);

        CompactEvent compactEvent = new CompactEvent();
        List<Event> events = new ArrayList<>();
        events.add(dnsResolveEvent);
        events.add(tcpTrafficEvent);
        compactEvent.setEvents(events);


        String parsed = EventMapper.mapper(compactEvent);

        System.out.println(parsed);

        Event parse = EventMapper.parse(parsed);
        Assertions.assertNotNull(parse);
        Assertions.assertTrue(parse instanceof CompactEvent);
        Assertions.assertEquals(compactEvent, parse);
    }
}
