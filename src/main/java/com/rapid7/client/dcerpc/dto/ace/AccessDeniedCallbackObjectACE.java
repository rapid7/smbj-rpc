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

/**
 * This class represents an <a href="https://msdn.microsoft.com/en-us/library/cc230293.aspx">ACCESS_DENIED_CALLBACK_OBJECT_ACE</a>
 */
public class AccessDeniedCallbackObjectACE extends CallbackObjectACE {

    public AccessDeniedCallbackObjectACE(final ACEHeader header, final int mask, final int flags,
            final UUID objectType, final UUID inheritedObjectType, final SID sid, final byte[] applicationData) {
        super(header, mask, flags, objectType, inheritedObjectType, sid, applicationData);
    }

    AccessDeniedCallbackObjectACE(final ACEHeader header, final int mask, final ByteBuffer buffer, final int aceStartPos) {
        super(header, mask, buffer, aceStartPos);
    }
}
