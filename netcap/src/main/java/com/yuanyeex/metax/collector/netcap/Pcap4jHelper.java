package com.yuanyeex.metax.collector.netcap;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.pcap4j.core.*;

import java.util.Optional;
import java.util.Properties;

/**
 * Helper for open pcap handler
 *
 * @author yuanyeex
 */
class Pcap4jHelper {

    /**
     * Handler to start the pcap handler
     *
     * @param properties properties with keys. Refer to {@link Options}
     * @return opened pcap handler
     * @throws PcapNativeException pcap native excpetion
     * @throws NotOpenException    pcap handler is not open
     */
    public static PcapHandle buildPcapHandler(Properties properties) throws PcapNativeException, NotOpenException {
        PcapNetworkInterface pcapNetworkInterface = getPcapNetworkInterface(properties);
        return openLiveHandler(pcapNetworkInterface, properties);
    }

    private static PcapHandle openLiveHandler(PcapNetworkInterface pcapNetworkInterface, Properties properties) throws PcapNativeException, NotOpenException {
        Integer snapLen = Optional.ofNullable(properties.getProperty(Options.PCAP_SNAPLEN))
                .map(Integer::parseInt)
                .orElse(65536);

        Integer timeout = Optional.ofNullable(properties.getProperty(Options.PCAP_TIMEOUT_MILLS))
                .map(Integer::parseInt)
                .orElse(10);

        Boolean promiscuousModel = Optional.ofNullable(properties.getProperty(Options.PCAP_NETWORK_INTERFACE_PROMISCOUS_MODE))
                .map(Boolean::valueOf)
                .orElse(true);
        PcapNetworkInterface.PromiscuousMode promiscuousMode = promiscuousModel
                ? PcapNetworkInterface.PromiscuousMode.PROMISCUOUS
                : PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS;

        String bpfPattern = Optional.ofNullable(properties.getProperty(Options.PCAP_BPF_PATTERN))
                .orElse("");

        Boolean bpfOptimizedMode = Optional.ofNullable(properties.getProperty(Options.PCAP_BPF_OPTIMIZED_MODE))
                .map(Boolean::valueOf)
                .orElse(true);

        BpfProgram.BpfCompileMode compileMode = bpfOptimizedMode
                ? BpfProgram.BpfCompileMode.OPTIMIZE
                : BpfProgram.BpfCompileMode.NONOPTIMIZE;


        PcapHandle pcapHandle = pcapNetworkInterface.openLive(snapLen, promiscuousMode, timeout);
        pcapHandle.setFilter(bpfPattern, compileMode);

        return pcapHandle;
    }


    private static PcapNetworkInterface getPcapNetworkInterface(Properties properties) throws PcapNativeException {
        String deviceName = properties.getProperty(Options.PCAP_NETWORK_INTERFACE_DEVICE_NAME);

        return getPcapNetworkInterface(deviceName);
    }

    private static PcapNetworkInterface getPcapNetworkInterface(String deviceName) throws PcapNativeException {
        Preconditions.checkArgument(StringUtils.isNotBlank(deviceName), "Missing interface device name!");
        PcapNetworkInterface device = Pcaps.getDevByName(deviceName);
        return Optional.ofNullable(device)
                .orElseThrow(() -> new IllegalStateException(String.format("NetworkInterface [%s] is not found!", deviceName)));
    }

}
