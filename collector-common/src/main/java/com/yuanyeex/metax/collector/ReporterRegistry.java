/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.collector;

import com.yuanyeex.metax.ServiceRegistry;
import com.yuanyeex.metax.event.Event;

/**
 * @author oex.zh
 * @version ReporterRegistry.java, v 0.1 2022-09-03 23:28 oex.zh
 */
public interface ReporterRegistry extends ServiceRegistry<ReporterType, Reporter<Event>> {

}
