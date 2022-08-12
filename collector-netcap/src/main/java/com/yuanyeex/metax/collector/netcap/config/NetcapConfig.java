package com.yuanyeex.metax.collector.netcap.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Netcap configurations
 *
 * @author yuanyeex
 */
@Configuration
@ComponentScan(basePackages = {"com.yuanyeex.metax.collector.netcap"})
@EnableConfigurationProperties(NetcapConfig.class)
public class NetcapConfig {
}
