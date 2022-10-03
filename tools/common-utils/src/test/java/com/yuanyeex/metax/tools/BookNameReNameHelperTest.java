/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author oex.zh
 * @version BookNameReNameHelperTest.java, v 0.1 2022-10-03 23:38 oex.zh
 */
public class BookNameReNameHelperTest {

    @Test
    public void test() {
        Assertions.assertEquals("Apache_Calcite-A_Foundational_Framework_for_Optimized_Query_Processing_Over_Heterogeneous_Data_Sources",
                BookNameReNameHelper.rename("Apache Calcite: A Foundational Framework for Optimized Query Processing Over Heterogeneous Data Sources"));
    }

}
