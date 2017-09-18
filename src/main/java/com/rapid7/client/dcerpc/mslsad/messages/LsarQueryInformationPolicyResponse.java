/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mslsad.messages;

import com.rapid7.client.dcerpc.messages.Response;
import com.rapid7.client.dcerpc.mslsad.objects.PolicyInformationClass;
import com.hierynomus.protocol.transport.TransportException;
import java.nio.ByteBuffer;

/**
 * Abstract class for QueryInformationResponse. The child classes will implement
 * the logic for each {@link PolicyInformationClass} respectively.
 */
public abstract class LsarQueryInformationPolicyResponse extends Response {

    protected LsarQueryInformationPolicyResponse(final ByteBuffer packet) throws TransportException {
        super(packet);
    }

    public abstract PolicyInformationClass getInfoLevel();
}
