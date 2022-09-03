/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.reporter.kafka;

import com.yuanyeex.metax.collector.Reporter;
import com.yuanyeex.metax.collector.ReporterRegistry;
import com.yuanyeex.metax.collector.ReporterType;
import com.yuanyeex.metax.event.Event;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author oex.zh
 * @version EventKafkaReporter.java, v 0.1 2022-09-03 22:40 oex.zh
 */
@Slf4j
public class EventKafkaReporter implements Reporter<Event>, InitializingBean {

    private final  ReporterRegistry reporterRegistry;

    @Getter
    private final KafkaTemplate<String, Event> eventKafkaTemplate;

    public EventKafkaReporter(ReporterRegistry reporterRegistry, KafkaTemplate<String, Event> eventKafkaTemplate) {
        this.reporterRegistry = reporterRegistry;
        this.eventKafkaTemplate = eventKafkaTemplate;
    }

    @Override
    public boolean report(Event data) {
        ListenableFuture<SendResult<String, Event>> send = eventKafkaTemplate.send("", data);
        send.addCallback(KafkaSendListenableFutureCallback.CALL_BACK);
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.reporterRegistry.register(ReporterType.KAFKA, this);
    }

    private static class KafkaSendListenableFutureCallback implements ListenableFutureCallback<SendResult<String, Event>> {

        private static final KafkaSendListenableFutureCallback CALL_BACK = new KafkaSendListenableFutureCallback();

        @Override
        public void onFailure(Throwable ex) {
            log.error("send kafka failed with exception", ex);
        }

        @Override
        public void onSuccess(SendResult<String, Event> result) {
            // do nothing
        }
    }
}
