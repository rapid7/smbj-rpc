/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssrvs.messages;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import com.hierynomus.protocol.transport.TransportException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

/**
 * <br>
 * Example:<br>
 *
 * <pre>
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
public class NetrShareEnumResponse extends RequestResponse {
    private int level;
    private List<NetShareInfo0> shares;
    private int shareCount;
    private Integer resumeHandle;
    private int returnValue;

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

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
        shares = new LinkedList<>();
        level = packetIn.readInt();
        packetIn.readInt();

        switch (level) {
            case 1:
                if (0 != packetIn.readReferentID()) {
                    final int count = packetIn.readInt();
                    if (0 != packetIn.readReferentID()) {
                        final List<Integer> nameRefs = new LinkedList<>();
                        final List<Integer> types = new LinkedList<>();
                        final List<Integer> commentRefs = new LinkedList<>();
                        packetIn.readInt(); // Maximum count
                        for (int index = 0; index < count; index++) {
                            nameRefs.add(packetIn.readReferentID());
                            types.add(packetIn.readInt());
                            commentRefs.add(packetIn.readReferentID());
                        }
                        for (int index = 0; index < count; index++) {
                            final String name = nameRefs.get(index) == 0 ? null : packetIn.readString(true);
                            final int type = types.get(index);
                            final String comment = commentRefs.get(index) == 0 ? null : packetIn.readString(true);
                            shares.add(new NetShareInfo1(name, type, comment));
                        }
                    }
                }
                break;
            case 2:
                if (0 != packetIn.readReferentID()) {
                    final int count = packetIn.readInt();
                    if (0 != packetIn.readReferentID()) {
                        final List<Integer> nameRefs = new LinkedList<>();
                        final List<Integer> types = new LinkedList<>();
                        final List<Integer> commentRefs = new LinkedList<>();
                        final List<Integer> permissions = new LinkedList<>();
                        final List<Integer> maximumUsers = new LinkedList<>();
                        final List<Integer> currentUsers = new LinkedList<>();
                        final List<Integer> pathRefs = new LinkedList<>();
                        final List<Integer> passwordRefs = new LinkedList<>();
                        packetIn.readInt(); // Maximum count
                        for (int index = 0; index < count; index++) {
                            nameRefs.add(packetIn.readReferentID());
                            types.add(packetIn.readInt());
                            commentRefs.add(packetIn.readReferentID());
                            permissions.add(packetIn.readInt());
                            maximumUsers.add(packetIn.readInt());
                            currentUsers.add(packetIn.readInt());
                            pathRefs.add(packetIn.readReferentID());
                            passwordRefs.add(packetIn.readReferentID());
                        }
                        for (int index = 0; index < count; index++) {
                            final String name = nameRefs.get(index) == 0 ? null : packetIn.readString(true);
                            final int type = types.get(index);
                            final String comment = commentRefs.get(index) == 0 ? null : packetIn.readString(true);
                            final int permission = permissions.get(index);
                            final int maximumUser = maximumUsers.get(index);
                            final int currentUser = currentUsers.get(index);
                            final String path = pathRefs.get(index) == 0 ? null : packetIn.readString(true);
                            final String password = passwordRefs.get(index) == 0 ? null : packetIn.readString(true);
                            final NetShareInfo2 shareInfo = new NetShareInfo2(name, type, comment, permission, maximumUser, currentUser, path, password);
                            shares.add(shareInfo);
                        }
                    }
                }
                break;
            default:
                throw new TransportException("Unsupported share info container.");
        }

        shareCount = packetIn.readInt();
        resumeHandle = packetIn.readIntRef();
        returnValue = packetIn.readInt();
    }
}
