package com.yuanyeex.metax.collector.netcap.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Netcap configurations
 *
 * @author yuanyeex
 */
@Component
@ConfigurationProperties(prefix = "metax.collector.pacap")
@Getter
@Setter
@ToString
public class NetcapProperties {

    private String networkInterfaceDeviceName;

    private boolean networkInterfacePromiscuousMode = true;

    private Integer snaplen = 65536;

    private Integer timeoutMills = 10;

    private String bpfPattern = "";

    private boolean bpfCompileOptimizedMode = true;
}
