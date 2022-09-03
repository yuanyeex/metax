package com.yuanyeex.metax.collector.netcap;

import com.yuanyeex.metax.collector.Collector;
import com.yuanyeex.metax.collector.CollectorRegistry;
import com.yuanyeex.metax.collector.CollectorType;
import com.yuanyeex.metax.collector.Reporter;
import com.yuanyeex.metax.collector.ReporterRegistry;
import com.yuanyeex.metax.collector.ReporterType;
import com.yuanyeex.metax.collector.netcap.config.NetcapProperties;
import com.yuanyeex.metax.event.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.PcapHandle;
import org.springframework.beans.factory.InitializingBean;
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
public class NetPackageCollector implements Collector, InitializingBean {

    private static final String NETCAP_COLLECTOR = "netcap-collector";

    private final NetcapProperties netcapProperties;

    private Reporter<Event> reporter;

    private Future<Void> future;

    @Getter
    @Setter
    @Autowired
    private ReporterRegistry reporterRegistry;

    @Getter
    @Setter
    @Autowired
    private CollectorRegistry collectorRegistry;

    public NetPackageCollector(NetcapProperties netcapProperties) {
        this.netcapProperties = netcapProperties;
    }

    @Override
    public void start() {
        if (!netcapProperties.isEnable() || !netcapProperties.getModules().isEmpty()) {
            log.warn("netcap collector is disabled or no modules enabled! enabled={}, modules={}",
                    netcapProperties.isEnable(), netcapProperties.getModules());
        }
        initReporter();
        future = CompletableFuture.runAsync(this::doStart);
    }

    @Override
    public void stop() {
        try {
            if (future != null) {
                future.cancel(true);
            }
        } finally {
            future = null;
        }
    }

    private void initReporter() {
        reporter = reporterRegistry.get(ReporterType.KAFKA);
        if (reporter == null) {
            log.error("Reporter {} is not registered!", ReporterType.KAFKA);
            throw new RuntimeException(String.format("Reporter %s is not registered", ReporterType.KAFKA));
        } else {
            log.info("Reporter netpacket events by reporter {}", reporter.getClass().getCanonicalName());
        }
    }

    private void doStart() {
        boolean collectDns = netcapProperties.getModules().contains("dns");
        boolean tcpTraffic = netcapProperties.getModules().contains("traffic");
        NetPacketHandler handler = new NetPacketHandler(collectDns, tcpTraffic, reporter);
        try (PcapHandle pcapHandle = Pcap4jHelper.buildPcapHandler(netcapProperties)) {
            pcapHandle.loop(-1, handler::handle);
        } catch (InterruptedException interruptedException) {
            log.error("collector netcap is interrupted!");
        } catch (Exception e) {
            log.error("[{}] failed to start collector with props {}!",
                    NETCAP_COLLECTOR, netcapProperties, e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        this.collectorRegistry.register(CollectorType.NETCAP, this);
    }
}
