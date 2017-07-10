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
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.protocol.commons.EnumWithValue.EnumUtils;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.messages.Request;

/**
 * One of the following method(s) {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
 * {@link OpenPerformanceData}, {@link OpenUsers}, {@link BaseRegCreateKey}, {@link BaseRegOpenKey},
 * {@link OpenCurrentConfig}, {@link OpenPerformanceText}, {@link OpenPerformanceNlsText} is called by the client. In
 * response, the server opens the appropriate predefined key.<br>
 * <br>
 *
 * <pre>
 * error_status_t OpenClassesRoot(
 *    [in, unique] PREGISTRY_SERVER_NAME ServerName,
 *    [in] REGSAM samDesired,
 *    [out] PRPC_HKEY phKey
 * );
 * </pre>
 *
 * <b>Example:</b>
 *
 * <pre>
 * Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 32, Call: 1, Ctx: 0, [Resp: #11176]
 *     Version: 5
 *     Version (minor): 0
 *     Packet type: Request (0)
 *     Packet Flags: 0x03
 *     Data Representation: 10000000
 *     Frag Length: 32
 *     Auth Length: 0
 *     Call ID: 1
 *     Alloc hint: 32
 *     Context ID: 0
 *     Opnum: 2
 *     [Response in frame: 11176]
 * Remote Registry Service, OpenHKLM
 *     Operation: OpenHKLM (2)
 *     [Response in frame: 11176]
 *     NULL Pointer: Pointer to System Name (uint16)
 *     Access Mask: 0x02000000
 *         Generic rights: 0x00000000
 *         .... ..1. .... .... .... .... .... .... = Maximum allowed: Set
 *         .... .... 0... .... .... .... .... .... = Access SACL: Not set
 *         Standard rights: 0x00000000
 *         WINREG specific rights: 0x00000000
 * </pre>
 *
 * @see {@link OpenClassesRoot}
 * @see {@link OpenCurrentUser}
 * @see {@link OpenLocalMachine}
 * @see {@link OpenPerformanceData}
 * @see {@link OpenUsers}
 * @see {@link BaseRegCreateKey}
 * @see {@link BaseRegOpenKey}
 * @see {@link OpenCurrentConfig}
 * @see {@link OpenPerformanceText}
 * @see {@link OpenPerformanceNlsText}
 * @see {@link <a href="https://msdn.microsoft.com/en-us/cc244950">3.1.5.1 OpenClassesRoot (Opnum 0)</a>}
 * @see {@link <a href="https://msdn.microsoft.com/en-us/cc244952">3.1.5.2 OpenCurrentUser (Opnum 1)</a>}
 * @see {@link <a href="https://msdn.microsoft.com/en-us/cc244953">3.1.5.3 OpenLocalMachine (Opnum 2)</a>}
 * @see {@link <a href="https://msdn.microsoft.com/en-us/cc244954">3.1.5.4 OpenPerformanceData (Opnum 3)</a>}
 * @see {@link <a href="https://msdn.microsoft.com/en-us/cc244957">3.1.5.5 OpenUsers (Opnum 4)</a>}
 * @see {@link <a href="https://msdn.microsoft.com/en-us/cc244951">3.1.5.25 OpenCurrentConfig (Opnum 27)</a>}
 * @see {@link <a href="https://msdn.microsoft.com/en-us/cc244956">3.1.5.28 OpenPerformanceText (Opnum 32)</a>}
 * @see {@link <a href="https://msdn.microsoft.com/en-us/cc244955">3.1.5.29 OpenPerformanceNlsText (Opnum 33)</a>}
 */
public class HandleRequest extends Request<HandleResponse> {
    /**
     * One of the following method(s) {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
     * {@link OpenPerformanceData}, {@link OpenUsers}, {@link BaseRegCreateKey}, {@link BaseRegOpenKey},
     * {@link OpenCurrentConfig}, {@link OpenPerformanceText}, {@link OpenPerformanceNlsText} is called by the client.
     * In response, the server opens the appropriate predefined key.
     *
     * @param op The operation number for one of {@link OpenClassesRoot}, {@link OpenCurrentUser},
     *        {@link OpenLocalMachine}, {@link OpenPerformanceData}, {@link OpenUsers}, {@link BaseRegCreateKey},
     *        {@link BaseRegOpenKey}, {@link OpenCurrentConfig}, {@link OpenPerformanceText},
     *        {@link OpenPerformanceNlsText}.
     * @param serverName The server name.
     * @param accessMask A bit field that describes the requested security access for the key.
     */
    public HandleRequest(final short op, final EnumSet<AccessMask> accessMask) {
        super(op);
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 32, Call: 1, Ctx: 0, [Resp: #11176]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 32
        //      Auth Length: 0
        //      Call ID: 1
        //      Alloc hint: 32
        //      Context ID: 0
        //      Opnum: 2
        //      [Response in frame: 11176]
        // Remote Registry Service, OpenHKLM
        //      Operation: OpenHKLM (2)
        //      [Response in frame: 11176]
        //      NULL Pointer: Pointer to System Name (uint16)
        //      Access Mask: 0x02000000
        //          Generic rights: 0x00000000
        //          .... ..1. .... .... .... .... .... .... = Maximum allowed: Set
        //          .... .... 0... .... .... .... .... .... = Access SACL: Not set
        //          Standard rights: 0x00000000
        //          WINREG specific rights: 0x00000000
        putNull();
        putInt((int) EnumUtils.toLong(accessMask));
    }

    /**
     * @return A bit field that describes the requested security access for the key.
     */
    public EnumSet<AccessMask> getAccessMask() {
        return EnumUtils.toEnumSet(getInt(28), AccessMask.class);
    }

    @Override
    protected HandleResponse parsePDUResponse(final ByteBuffer responseBuffer)
        throws TransportException {
        return new HandleResponse(responseBuffer);
    }
}
