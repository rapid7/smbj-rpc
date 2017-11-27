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

package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRUserAllInformation;
import com.rapid7.client.dcerpc.mssamr.objects.UserInformationClass;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245786.aspx">SamrQueryInformationUser</a>
 *
 * <blockquote><pre>The SamrQueryInformationUser method obtains attributes from a user object.
 *      long SamrQueryInformationUser(
 *          [in] SAMPR_HANDLE UserHandle,
 *          [in] USER_INFORMATION_CLASS UserInformationClass,
 *          [out, switch_is(UserInformationClass)]
 *          PSAMPR_USER_INFO_BUFFER* Buffer
 *      );
 *  See the description of SamrQueryInformationUser2 (section 3.1.5.5.5) for details, because the method interface arguments and message processing are identical.
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 *  The server MUST behave as with a call to SamrQueryInformationUser2, with the following parameter values.
 *      Parameter name        Parameter value
 *      UserHandle            SamrQueryInformationUser.UserHandle
 *      UserInformationClass  SamrQueryInformationUser.UserInformationClass
 *      Buffer                SamrQueryInformationUser.Buffer</pre></blockquote>
 */
public abstract class SamrQueryInformationUserRequest<T extends Unmarshallable> extends RequestCall<SamrQueryInformationUserResponse<T>> {
    public static final short OP_NUM = 36;

    private final byte[] userHandle;

    SamrQueryInformationUserRequest(byte[] userHandle) {
        super(OP_NUM);
        this.userHandle = userHandle;
    }

    public byte[] getUserHandle() {
        return userHandle;
    }

    public abstract UserInformationClass getUserInformationClass();

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: struct> [in] SAMPR_HANDLE UserHandle,
        packetOut.write(getUserHandle());
        // <NDR: unsigned short> [in] USER_INFORMATION_CLASS UserInformationClass,
        // Alignment: 2 - Already aligned. ContextHandle writes 20 bytes above
        packetOut.writeShort(getUserInformationClass().getInfoLevel());
    }

    public static class UserAllInformation extends SamrQueryInformationUserRequest<SAMPRUserAllInformation> {
        public UserAllInformation(byte[] userHandle) {
            super(userHandle);
        }

        @Override
        public UserInformationClass getUserInformationClass() {
            return UserInformationClass.USER_ALL_INFORMATION;
        }

        @Override
        public SamrQueryInformationUserResponse.UserAllInformation getResponseObject() {
            return new SamrQueryInformationUserResponse.UserAllInformation();
        }
    }
}
