/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */
package com.rapid7.client.dcerpc.msvcctl.dto.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * This enumeration is a representation of the service status types.
 */
public enum ServiceStatusType {
    SERVICE_PAUSED(0x00000007),
    SERVICE_RUNNING(0x00000004),
    SERVICE_STOPPED(0x00000001),
    SERVICE_CONTINUE_PENDING(0x00000005),
    SERVICE_PAUSE_PENDING(0x00000006),
    SERVICE_START_PENDING(0x00000002),
    SERVICE_STOP_PENDING(0x00000003),
    ACCESS_MASK(0xF01FF),
    UNKNOWN(-1);

    /////////////////////////////////////////////////////////
    // Public Methods
    /////////////////////////////////////////////////////////

    /**
     * This constructor initializes a service action enum type
     * with the provided integer representation for that type.
     *
     * @param value The integer representation for the enum type
     */
    ServiceStatusType(int value) {
        m_value = value;
    }

    /**
     * Returns the int value of the enum.
     *
     * @return the int value
     */
    public int getValue() {
        return m_value;
    }

    /**
     * Takes an int value and returns the enum that is matched.
     *
     * @param value Value to match
     * @return enum found or UNKNOWN
     */
    public static ServiceStatusType fromInt(int value) {
        ServiceStatusType type = MS_TYPEDMAP.get(value);
        if (type == null) return UNKNOWN;
        return type;
    }

    /////////////////////////////////////////////////////////
    // Non-Public Fields
    /////////////////////////////////////////////////////////

    private final int m_value;
    private static final Map<Integer, ServiceStatusType> MS_TYPEDMAP = new HashMap<>();

    static {
        for (ServiceStatusType type : ServiceStatusType.values()) {
            MS_TYPEDMAP.put(type.getValue(), type);

        }
    }
}
