/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax;

import java.util.Collection;

/**
 * @author oex.zh
 * @version ServiceRegistry.java, v 0.1 2022-09-03 23:23 oex.zh
 */
public interface ServiceRegistry<N, S> {

    /**
     * Register a service with key
     *
     * @param name    name of the service
     * @param service service to be registered
     * @return register status
     */
    boolean register(N name, S service);

    /**
     * Get the registered service with key
     *
     * @param name of the service
     * @return registered service
     */
    S get(N name);

    /**
     * list all registered services
     *
     * @return all services registered
     */
    Collection<S> listAll();

}
