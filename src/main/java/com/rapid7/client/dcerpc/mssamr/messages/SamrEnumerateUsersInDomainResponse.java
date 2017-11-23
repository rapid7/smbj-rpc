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
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.mssamr.objects.EnumeratedUsers;
import com.rapid7.client.dcerpc.mssamr.objects.UserInfo;

/**
 * The {@link SamrEnumerateResponse} implementation for request {@link SamrEnumerateUsersInDomainRequest}.
 */
public class SamrEnumerateUsersInDomainResponse extends SamrEnumerateResponse<UserInfo> {
    // <NDR: struct> [out] PSAMPR_ENUMERATION_BUFFER* Buffer
    private EnumeratedUsers users;

    @Override
    protected void unmarshallBuffer(PacketInput packetIn) throws IOException {
        users = new EnumeratedUsers();
        packetIn.readUnmarshallable(users);
    }

    @Override
    public List<UserInfo> getList() {
        return users.getEntries();
    }
}
