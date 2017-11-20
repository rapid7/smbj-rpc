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
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;

/**
 * The SamrEnumerateUsersInDomain method enumerates all users.
 *
 * <pre>
 * long SamrEnumerateUsersInDomain(
 *   [in] SAMPR_HANDLE DomainHandle,
 *   [in, out] unsigned long* EnumerationContext,
 *   [in] unsigned long UserAccountControl,
 *   [out] PSAMPR_ENUMERATION_BUFFER* Buffer,
 *   [in] unsigned long PreferedMaximumLength,
 *   [out] unsigned long* CountReturned
 * );
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/cc245759.aspx">
 *       https://msdn.microsoft.com/en-us/library/cc245759.aspx</a>
 */
public class SamrEnumerateUsersInDomainRequest extends RequestCall<SamrEnumerateUsersInDomainResponse> {
    public static final short OP_NUM = 13;
    private final DomainHandle handle;
    private final int enumContext;
    private final int userAccountControl;
    private final int maxLength;

    public SamrEnumerateUsersInDomainRequest(DomainHandle handle, int enumContext, int userAccountControl,
            int maxLength) {
        super(OP_NUM);
        this.handle = handle;
        this.enumContext = enumContext;
        this.userAccountControl = userAccountControl;
        this.maxLength = maxLength;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(handle.getBytes());
        packetOut.writeInt(enumContext);
        packetOut.writeInt(userAccountControl);
        packetOut.writeInt(maxLength);
    }

    @Override
    public SamrEnumerateUsersInDomainResponse getResponseObject() {
        return new SamrEnumerateUsersInDomainResponse();
    }

}
