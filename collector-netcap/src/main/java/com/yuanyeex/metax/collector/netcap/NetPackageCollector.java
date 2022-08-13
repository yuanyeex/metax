package com.yuanyeex.metax.collector.netcap;

import com.yuanyeex.metax.collector.Collector;
import com.yuanyeex.metax.collector.netcap.config.NetcapProperties;
import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * net package collector
 *
 * @author yuanyeex
 */
@Slf4j
@Component
public class NetPackageCollector implements Collector, InitializingBean {

    private static final String NETCAP_COLLECTOR = "netcap-collector";

    private final NetcapProperties netcapProperties;

    public NetPackageCollector(NetcapProperties netcapProperties) {
        this.netcapProperties = netcapProperties;
    }

    @Override
    public void start() {
        try (PcapHandle pcapHandle = Pcap4jHelper.buildPcapHandler(netcapProperties)) {
            pcapHandle.loop(-1, (PacketListener) packet -> log.info("[{}] network packet received: {} bytes", NETCAP_COLLECTOR,
                    packet.getHeader().length()));
        } catch (Exception e) {
            log.error("[{}] failed to start collector with props {}!",
                    NETCAP_COLLECTOR, netcapProperties, e);
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void afterPropertiesSet() {
        CompletableFuture.runAsync(this::start);
    }
}
