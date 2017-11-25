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

package com.rapid7.client.dcerpc.dto.ace;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a known ACE Type from an {@link ACEHeader}.
 */
public enum ACEType {
    ACCESS_ALLOWED_ACE_TYPE,
    ACCESS_DENIED_ACE_TYPE,
    SYSTEM_AUDIT_ACE_TYPE,
    SYSTEM_ALARM_ACE_TYPE,
    ACCESS_ALLOWED_COMPOUND_ACE_TYPE,
    ACCESS_ALLOWED_OBJECT_ACE_TYPE,
    ACCESS_DENIED_OBJECT_ACE_TYPE,
    SYSTEM_AUDIT_OBJECT_ACE_TYPE,
    SYSTEM_ALARM_OBJECT_ACE_TYPE,
    ACCESS_ALLOWED_CALLBACK_ACE_TYPE,
    ACCESS_DENIED_CALLBACK_ACE_TYPE,
    ACCESS_ALLOWED_CALLBACK_OBJECT_ACE_TYPE,
    ACCESS_DENIED_CALLBACK_OBJECT_ACE_TYPE,
    SYSTEM_AUDIT_CALLBACK_ACE_TYPE,
    SYSTEM_ALARM_CALLBACK_ACE_TYPE,
    SYSTEM_AUDIT_CALLBACK_OBJECT_ACE_TYPE,
    SYSTEM_ALARM_CALLBACK_OBJECT_ACE_TYPE,
    SYSTEM_MANDATORY_LABEL_ACE_TYPE,
    SYSTEM_RESOURCE_ATTRIBUTE_ACE_TYPE,
    SYSTEM_SCOPED_POLICY_ID_ACE_TYPE;

    public byte getValue() {
        return (byte) ordinal();
    }

    @Override
    public String toString() {
        return String.format("%s (0x%02X)", name(), getValue());
    }

    public static ACEType fromValue(final byte value) {
        final ACEType aceType = VALUE_MAP.get(value);
        if (aceType == null)
            throw new IllegalArgumentException(String.format("Unknown AceType value: %d", value));
        return aceType;
    }

    private static final Map<Byte, ACEType> VALUE_MAP = new HashMap<>();
    static {
        for (final ACEType aceType : ACEType.values()) {
            VALUE_MAP.put(aceType.getValue(), aceType);
        }
    }
}
