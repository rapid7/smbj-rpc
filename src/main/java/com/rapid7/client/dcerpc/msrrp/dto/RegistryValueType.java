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
package com.rapid7.client.dcerpc.msrrp.dto;

import java.util.HashMap;
import java.util.Map;

public enum RegistryValueType {
    REG_NONE(0),
    REG_SZ(1),
    REG_EXPAND_SZ(2),
    REG_BINARY(3),
    REG_DWORD(4),
    REG_DWORD_BIG_ENDIAN(5),
    REG_LINK(6),
    REG_MULTI_SZ(7),
    REG_RESOURCE_LIST(8),
    REG_FULL_RESOURCE_DESCRIPTOR(9),
    REG_RESOURCE_REQUIREMENTS_LIST(10),
    REG_QWORD(11);

    private final int typeID;
    private static final Map<Integer, RegistryValueType> registryValueTypes = new HashMap<>();

    RegistryValueType(final int typeID) {
        this.typeID = typeID;
    }

    public int getTypeID() {
        return typeID;
    }

    public boolean is(final int typeID) {
        return this.typeID == typeID;
    }

    public static RegistryValueType getRegistryValueType(final int typeID) {
        return registryValueTypes.get(typeID);
    }

    static {
        for (final RegistryValueType typeID : RegistryValueType.values()) {
            registryValueTypes.put(typeID.getTypeID(), typeID);
        }
    }
}
