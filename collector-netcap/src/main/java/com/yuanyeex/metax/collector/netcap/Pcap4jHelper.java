package com.yuanyeex.metax.collector.netcap;

import com.google.common.base.Preconditions;
import com.yuanyeex.metax.collector.netcap.config.NetcapConfig;
import com.yuanyeex.metax.collector.netcap.config.NetcapProperties;
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
     * @param config properties with keys.
     * @return opened pcap handler
     * @throws PcapNativeException pcap native excpetion
     * @throws NotOpenException    pcap handler is not open
     */
    public static PcapHandle buildPcapHandler(NetcapProperties config) throws PcapNativeException, NotOpenException {
        PcapNetworkInterface pcapNetworkInterface = getPcapNetworkInterface(config.getNetworkInterfaceDeviceName());
        return openLiveHandler(pcapNetworkInterface, config);
    }

    private static PcapHandle openLiveHandler(PcapNetworkInterface pcapNetworkInterface, NetcapProperties properties) throws PcapNativeException, NotOpenException {
        Integer snapLen = properties.getSnaplen();

        Integer timeout = properties.getTimeoutMills();

        PcapNetworkInterface.PromiscuousMode promiscuousMode = properties.isNetworkInterfacePromiscuousMode()
                ? PcapNetworkInterface.PromiscuousMode.PROMISCUOUS
                : PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS;

        String bpfPattern = Optional.ofNullable(properties.getBpfPattern())
                .orElse("");

        BpfProgram.BpfCompileMode compileMode = properties.isBpfCompileOptimizedMode()
                ? BpfProgram.BpfCompileMode.OPTIMIZE
                : BpfProgram.BpfCompileMode.NONOPTIMIZE;


        PcapHandle pcapHandle = pcapNetworkInterface.openLive(snapLen, promiscuousMode, timeout);
        pcapHandle.setFilter(bpfPattern, compileMode);

        return pcapHandle;
    }

    private static PcapNetworkInterface getPcapNetworkInterface(String deviceName) throws PcapNativeException {
        Preconditions.checkArgument(StringUtils.isNotBlank(deviceName), "Missing interface device name!");
        PcapNetworkInterface device = Pcaps.getDevByName(deviceName);
        return Optional.ofNullable(device)
                .orElseThrow(() -> new IllegalStateException(String.format("NetworkInterface [%s] is not found!", deviceName)));
    }

}
