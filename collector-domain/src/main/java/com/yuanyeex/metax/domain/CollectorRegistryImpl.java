/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.domain;

import com.yuanyeex.metax.collector.Collector;
import com.yuanyeex.metax.collector.CollectorRegistry;
import com.yuanyeex.metax.collector.CollectorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author oex.zh
 * @version CollectorRegistryImpl.java, v 0.1 2022-09-04 00:13 oex.zh
 */
@Component
@Slf4j
public class CollectorRegistryImpl implements CollectorRegistry {

    private final Map<CollectorType, Collector> registry = new HashMap<>();

    @Override
    public boolean register(CollectorType name, Collector collector) {
        Collector existing = registry.putIfAbsent(name, collector);
        if (existing != null) {
            log.error("collector {} / {} already registered by {}", name,
                    collector.getClass().getCanonicalName(),
                    existing.getClass().getCanonicalName());
            return false;
        } else {
            log.info("collector {} / {} registered!", name, collector.getClass().getCanonicalName());
            return true;
        }
    }

    @Override
    public Collector get(CollectorType name) {
        return registry.get(name);
    }

    @Override
    public Collection<Collector> listAll() {
        return Collections.unmodifiableCollection(registry.values());
    }
}
