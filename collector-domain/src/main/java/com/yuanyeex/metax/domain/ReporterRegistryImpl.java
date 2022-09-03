/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.domain;

import com.yuanyeex.metax.collector.Reporter;
import com.yuanyeex.metax.collector.ReporterRegistry;
import com.yuanyeex.metax.collector.ReporterType;
import com.yuanyeex.metax.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author oex.zh
 * @version ReporterRegistryImpl.java, v 0.1 2022-09-03 23:33 oex.zh
 */
@Slf4j
@Component
public class ReporterRegistryImpl implements ReporterRegistry {

    private final Map<ReporterType, Reporter<Event>> reporters = new HashMap<>();

    @Override
    public boolean register(ReporterType name, Reporter<Event> service) {
        Reporter<Event> eventReporter = reporters.putIfAbsent(name, service);
        if (eventReporter != null) {
            log.error("reporter {} / {} already registered with {} ",
                    name, service.getClass().getCanonicalName(), eventReporter.getClass().getCanonicalName());
            return false;
        } else {
            log.info("reporter {}/ {} registered!", name, service.getClass().getCanonicalName());
        }
        return true;
    }

    @Override
    public Reporter<Event> get(ReporterType name) {
        return reporters.get(name);
    }

    @Override
    public Collection<Reporter<Event>> listAll() {
        return Collections.unmodifiableCollection(reporters.values());
    }
}
