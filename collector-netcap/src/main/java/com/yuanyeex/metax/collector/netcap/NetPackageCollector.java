package com.yuanyeex.metax.collector.netcap;

import com.yuanyeex.metax.collector.Collector;
import com.yuanyeex.metax.collector.Reporter;
import com.yuanyeex.metax.collector.ReporterRegistry;
import com.yuanyeex.metax.collector.ReporterType;
import com.yuanyeex.metax.collector.netcap.config.NetcapProperties;
import com.yuanyeex.metax.event.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * net package collector
 *
 * @author yuanyeex
 */
@Slf4j
@Component
public class NetPackageCollector implements Collector {

    private static final String NETCAP_COLLECTOR = "netcap-collector";

    private final NetcapProperties netcapProperties;

    private Reporter<Event> reporter;

    private Future<Void> future;

    @Getter
    @Setter
    @Autowired
    private ReporterRegistry reporterRegistry;

    public NetPackageCollector(NetcapProperties netcapProperties) {
        this.netcapProperties = netcapProperties;
    }

    @Override
    public void start() {
        reporter = reporterRegistry.get(ReporterType.KAFKA);
        if (reporter == null) {
            log.error("Reporter {} is not registered!", ReporterType.KAFKA);
            throw new RuntimeException(String.format("Reporter %s is not registered", ReporterType.KAFKA));
        } else {
            log.info("Reporter netpacket events by reporter {}", reporter.getClass().getCanonicalName());
        }
        future = CompletableFuture.runAsync(this::doStart);
    }

    @Override
    public void stop() {
        try {
            future.cancel(true);
        } finally {
            future = null;
        }
    }

    private void doStart() {
        try (PcapHandle pcapHandle = Pcap4jHelper.buildPcapHandler(netcapProperties)) {
            pcapHandle.loop(-1, (PacketListener) packet -> log.debug("[{}] network packet received: {} bytes", NETCAP_COLLECTOR,
                    packet.getHeader().length()));
        } catch (InterruptedException interruptedException) {
            log.error("collector netcap is interrupted!");
        } catch (Exception e) {
            log.error("[{}] failed to start collector with props {}!",
                    NETCAP_COLLECTOR, netcapProperties, e);
        }
    }
}
