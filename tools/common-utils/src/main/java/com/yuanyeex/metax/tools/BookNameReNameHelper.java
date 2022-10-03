/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.tools;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author oex.zh
 * @version BookNameReNameHelper.java, v 0.1 2022-10-03 23:35 oex.zh
 */
public class BookNameReNameHelper {

    private static final Map<Character, Character> SPECIAL_MAPPING =
            ImmutableMap.<Character, Character>builder()
                    .put(' ', '_')
                    .put(':', '-')
                    .build();

    /**
     * Paper or book Name rename.
     * 1. Replace ' ' to '_'
     * 2. Replace ':' to '-'
     *
     * @param originName origin name
     * @return the formatted name
     */
    public static String rename(String originName) {
        StringBuilder sb = new StringBuilder();
        boolean prevSpecial = false;
        for (char c : originName.toCharArray()) {
            Character mapped = SPECIAL_MAPPING.get(c);
            if (mapped != null) {
                if (!prevSpecial) {
                    sb.append(mapped);
                }
                prevSpecial = true;
            } else {
                sb.append(c);
                prevSpecial = false;
            }
        }
        return sb.toString();
    }
}
