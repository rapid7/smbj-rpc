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
package com.rapid7.client.dcerpc.mssrvs.messages;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.messages.Response;

/**
 * <br>
 * Example:<br>
 *
 * <pre>
 * Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 284, Call: 1, Ctx: 0, [Req: #17]
 *     Version: 5
 *     Version (minor): 0
 *     Packet type: Response (2)
 *     Packet Flags: 0x03
 *     Data Representation: 10000000 (Order: Little-endian, Char: ASCII, Float: IEEE)
 *     Frag Length: 284
 *     Auth Length: 0
 *     Call ID: 1
 *     Alloc hint: 260
 *     Context ID: 0
 *     Cancel count: 0
 *     Opnum: 15
 *     [Request in frame: 17]
 *     [Time from request: 0.007913000 seconds]
 *     Complete stub data (260 bytes)
 * Server Service, NetShareEnumAll
 *     Operation: NetShareEnumAll (15)
 *     [Request in frame: 17]
 *     Pointer to Level (uint32)
 *         Level: 1
 *     Pointer to Ctr (srvsvc_NetShareCtr)
 *         srvsvc_NetShareCtr
 *             Ctr
 *             Pointer to Ctr1 (srvsvc_NetShareCtr1)
 *                 Referent ID: 0x00020000
 *                 Ctr1
 *                     Count: 3
 *                     Pointer to Array (srvsvc_NetShareInfo1)
 *                         Referent ID: 0x00020004
 *                         Max Count: 3
 *                         Array
 *                             Pointer to Name (uint16)
 *                                 Referent ID: 0x00020008
 *                                 Max Count: 7
 *                                 Offset: 0
 *                                 Actual Count: 7
 *                                 Name: ADMIN$
 *                             Type: STYPE_DISKTREE_HIDDEN (2147483648)
 *                             Pointer to Comment (uint16)
 *                                 Referent ID: 0x0002000c
 *                                 Max Count: 13
 *                                 Offset: 0
 *                                 Actual Count: 13
 *                                 Comment: Remote Admin
 *                         Array
 *                             Pointer to Name (uint16)
 *                                 Referent ID: 0x00020010
 *                                 Max Count: 3
 *                                 Offset: 0
 *                                 Actual Count: 3
 *                                 Name: C$
 *                             Type: STYPE_DISKTREE_HIDDEN (2147483648)
 *                             Pointer to Comment (uint16)
 *                                 Referent ID: 0x00020014
 *                                 Max Count: 14
 *                                 Offset: 0
 *                                 Actual Count: 14
 *                                 Comment: Default share
 *                         Array
 *                             Pointer to Name (uint16)
 *                                 Referent ID: 0x00020018
 *                                 Max Count: 5
 *                                 Offset: 0
 *                                 Actual Count: 5
 *                                 Name: IPC$
 *                             Type: STYPE_IPC_HIDDEN (2147483651)
 *                             Pointer to Comment (uint16)
 *                                 Referent ID: 0x0002001c
 *                                 Max Count: 11
 *                                 Offset: 0
 *                                 Actual Count: 11
 *                                 Comment: Remote IPC
 *     Pointer to Totalentries (uint32)
 *         Totalentries: 3
 *     NULL Pointer: Pointer to Resume Handle (uint32)
 *     Windows Error: WERR_OK (0x00000000)
 * </pre>
 */
public class NetrShareEnumResponse extends Response {
    private final int level;
    private final List<NetShareInfo0> shares = new LinkedList<>();
    private final int shareCount;
    private final Integer resumeHandle;
    private final int returnValue;

    public NetrShareEnumResponse(final ByteBuffer packet)
        throws TransportException {
        super(packet);
        level = getInt();
        getInt();

        switch (level) {
        case 1:
            if (0 != getReferentID()) {
                final int count = getInt();
                if (0 != getReferentID()) {
                    final List<Integer> nameRefs = new LinkedList<>();
                    final List<Integer> types = new LinkedList<>();
                    final List<Integer> commentRefs = new LinkedList<>();
                    getInt();
                    for (int index = 0; index < count; index++) {
                        nameRefs.add(getReferentID());
                        types.add(getInt());
                        commentRefs.add(getReferentID());
                    }
                    for (int index = 0; index < count; index++) {
                        final String name = nameRefs.get(index) == 0 ? null : getString(true);
                        final int type = types.get(index);
                        final String comment = commentRefs.get(index) == 0 ? null : getString(true);
                        shares.add(new NetShareInfo1(name, type, comment));
                    }
                }
            }
            break;
        default:
            throw new TransportException("Unsupported share info container.");
        }

        shareCount = getInt();
        resumeHandle = getIntRef();
        returnValue = getInt();
    }

    public int getLevel() {
        return level;
    }

    public List<NetShareInfo0> getShares() {
        return shares;
    }

    public int getShareCount() {
        return shareCount;
    }

    public Integer getResumeHandle() {
        return resumeHandle;
    }

    public int getReturnValue() {
        return returnValue;
    }
}
