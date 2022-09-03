/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.collectorbootstrap;

import com.yuanyeex.metax.domain.DomainConfiguration;
import com.yuanyeex.metax.collector.netcap.config.NetcapConfig;
import com.yuanyeex.metax.reporter.kafka.ReporterKafkaProducerConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author oex.zh
 * @version BootstrapConfiguration.java, v 0.1 2022-09-03 23:47 oex.zh
 */
@Configuration
@ImportAutoConfiguration({
        DomainConfiguration.class,
        ReporterKafkaProducerConfig.class,
        NetcapConfig.class
        })
public class BootstrapConfiguration {
}
