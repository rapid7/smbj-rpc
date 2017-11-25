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
import java.util.UUID;
import com.rapid7.client.dcerpc.dto.SID;
import com.rapid7.client.dcerpc.dto.SecurityDescriptor;

/**
 * This class represents an ACE which contains an object.
 */
public abstract class ObjectACE extends ACE {
    private final int flags;
    private final UUID objectType;
    private final UUID inheritedObjectType;
    private final SID sid;

    public ObjectACE(final ACEHeader header, final int mask, final int flags,
            final UUID objectType, final UUID inheritedObjectType, final SID sid) {
        super(header, mask);
        this.flags = flags;
        this.objectType = objectType;
        this.inheritedObjectType = inheritedObjectType;
        this.sid = sid;
    }

    ObjectACE(final ACEHeader header, final int mask, final ByteBuffer buffer) {
        super(header, mask);
        this.flags = buffer.getInt();
        if (isACEObjectPresent()) {
            // TODO
            buffer.get(16);
        } else {
            buffer.get(16);
        }
        if (isACEInheritedObjectPresent()) {
            // TODO
            buffer.get(16);
        } else {
            buffer.get(16);
        }
        this.objectType = null;
        this.inheritedObjectType = null;
        this.sid = SecurityDescriptor.readSID(buffer);
    }

    @Override
    public SID getSID() {
        return sid;
    }

    public int getFlags() {
        return flags;
    }

    public boolean isACEObjectPresent() {
        return (getFlags() & 0x01) != 0;
    }

    public boolean isACEInheritedObjectPresent() {
        return (getFlags() & 0x02) != 0;
    }

    public UUID getObjectType() {
        return objectType;
    }

    public UUID getInheritedObjectType() {
        return inheritedObjectType;
    }
}
