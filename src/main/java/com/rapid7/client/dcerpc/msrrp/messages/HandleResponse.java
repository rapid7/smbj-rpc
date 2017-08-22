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
package com.rapid7.client.dcerpc.msrrp.messages;

import java.nio.ByteBuffer;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.messages.Response;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;

/**
 * <b>Example:</b>
 *
 * <pre>
 * Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 48, Call: 1, Ctx: 0, [Req: #11174]
 *     Version: 5
 *     Version (minor): 0
 *     Packet type: Response (2)
 *     Packet Flags: 0x03
 *     Data Representation: 10000000
 *     Frag Length: 48
 *     Auth Length: 0
 *     Call ID: 1
 *     Alloc hint: 24
 *     Context ID: 0
 *     Cancel count: 0
 *     Opnum: 2
 *     [Request in frame: 11174]
 *     [Time from request: 0.095388051 seconds]
 * Remote Registry Service, OpenHKLM
 *     Operation: OpenHKLM (2)
 *     [Request in frame: 11174]
 *     Pointer to Handle (policy_handle)
 *         Policy Handle: OpenHKLM(&lt;...&gt;)
 *             Handle: 0000000032daf234b77c86409d29efe60d326683
 *             [Frame handle opened: 11176]
 *             [Frame handle closed: 11424]
 *     Windows Error: WERR_OK (0x00000000)
 * </pre>
 */
public class HandleResponse extends Response {
    private final ContextHandle handle = new ContextHandle();
    private final int returnValue;

    public HandleResponse(final ByteBuffer packet)
        throws TransportException {
        super(packet);
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 48, Call: 1, Ctx: 0, [Req: #11174]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 48
        //      Auth Length: 0
        //      Call ID: 1
        //      Alloc hint: 24
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 2
        //      [Request in frame: 11174]
        //      [Time from request: 0.095388051 seconds]
        // Remote Registry Service, OpenHKLM
        //      Operation: OpenHKLM (2)
        //      [Request in frame: 11174]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        //      Windows Error: WERR_OK (0x00000000)
        handle.setBytes(getBytes(handle.getLength()));
        returnValue = getInt();
    }

    /** @return The handle to a opened key. */
    public ContextHandle getHandle() {
        return handle;
    }

    /**
     * @return The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code, as
     *         specified in [MS-ERREF] section 2.2.
     */
    public int getReturnValue() {
        return returnValue;
    }
}
