/*
 * Metax Tech.
 * Copyright (c) 2021-2022 All Rights Reserved.
 */
package com.yuanyeex.metax.event.netcap;

import com.yuanyeex.metax.event.Event;
import com.yuanyeex.metax.event.EventNameEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author oex.zh
 * @version CompactEvent.java, v 0.1 2022-09-04 00:57 oex.zh
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@ToString(callSuper = true)
public class CompactEvent extends Event {

    private List<Event> events;

    public CompactEvent() {
        super(EventNameEnum.COMPACT_EVENT.name());
    }

    public static CompactEvent compact(List<Event> events) {
        CompactEvent compactEvent = new CompactEvent();
        compactEvent.setEvents(events);
        compactEvent.setEpoch(System.currentTimeMillis());
        return compactEvent;
    }

}
