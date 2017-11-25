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

/**
 * This enum represents an <a href="https://msdn.microsoft.com/en-us/library/cc230289.aspx">ACE ObjectType</a>
 */
public enum ACEObjectTypeMask {
    ADS_RIGHT_DS_CONTROL_ACCESS((short) 0x0100),
    ADS_RIGHT_DS_CREATE_CHILD((short) 0x0001),
    ADS_RIGHT_DS_DELETE_CHILD((short) 0x0002),
    ADS_RIGHT_DS_READ_PROP((short) 0x0010),
    ADS_RIGHT_DS_WRITE_PROP((short) 0x0020),
    ADS_RIGHT_DS_SELF((short) 0x0008);

    private final short value;

    ACEObjectTypeMask(final short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    public boolean isSet(final int bitField) {
        return (bitField & getValue()) != 0;
    }
}
