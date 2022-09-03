/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.reporter.kafka;

import com.yuanyeex.metax.collector.ReporterRegistry;
import com.yuanyeex.metax.event.Event;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author oex.zh
 * @version ReporterKafkaProducer.java, v 0.1 2022-09-03 22:06 oex.zh
 */
@Configuration
@ConfigurationProperties(prefix = "metax.reporter.kafka")
@Getter
@Setter
public class ReporterKafkaProducerConfig {

    private String bootstrapAddress;
    private String topic;

    @Autowired
    @Getter
    @Setter
    private ReporterRegistry reporterRegistry;

    @Bean
    public ProducerFactory<String, Event> eventProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Event> eventKafkaTemplate(ProducerFactory<String, Event> eventProducerFactory) {
        return new KafkaTemplate<>(eventProducerFactory);
    }

    @Bean
    public EventKafkaReporter eventKafkaReporter(KafkaTemplate<String, Event> eventKafkaTemplate) {
        return new EventKafkaReporter(reporterRegistry, eventKafkaTemplate);
    }
    
}
