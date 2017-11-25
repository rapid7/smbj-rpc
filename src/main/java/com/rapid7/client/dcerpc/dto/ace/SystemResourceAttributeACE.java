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

import java.nio.ByteBuffer;
import com.rapid7.client.dcerpc.dto.SID;
import com.rapid7.client.dcerpc.dto.SecurityDescriptor;

/**
 * This class represents a <a href="https://msdn.microsoft.com/en-us/library/hh877837.aspx">SYSTEM_RESOURCE_ATTRIBUTE_ACE</a>
 */
public class SystemResourceAttributeACE extends ACE {

    private final SID sid;
    private final byte[] attributeData;

    public SystemResourceAttributeACE(final ACEHeader header, final int mask, final SID sid, final byte[] attributeData) {
        super(header, mask);
        this.sid = sid;
        this.attributeData = attributeData;
    }

    SystemResourceAttributeACE(final ACEHeader header, final int mask, final ByteBuffer buffer, final int aceStartPos) {
        super(header, mask);
        this.sid = SecurityDescriptor.readSID(buffer);
        // TODO parse this
        this.attributeData = new byte[header.getSize() - (buffer.position() - aceStartPos)];
    }

    @Override
    public SID getSID() {
        return sid;
    }

    public byte[] getAttributeData() {
        return attributeData;
    }
}
