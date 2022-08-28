/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * @author oex.zh
 * @version EventMapper.java, v 0.1 2022-08-28 22:40 oex.zh
 */
public class EventMapper {

    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    public static String mapper(Event event) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(event);
    }

    public static Event parse(String json) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, Event.class);
    }

}
