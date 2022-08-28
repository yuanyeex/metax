/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.collector;

/**
 * Reporter interface
 * @author oex.zh
 * @version DataReporter.java, v 0.1 2022-08-28 20:54 oex.zh
 */
public interface Reporter<D> {
    /**
     * Report data
     *
     * @param data data
     * @return success or failure
     */
    boolean report(D data);
}
