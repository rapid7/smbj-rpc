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
import com.rapid7.client.dcerpc.mssamr.objects.GroupInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGroupGeneralInformation;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245780.aspx">SamrQueryInformationGroup</a>
 *
 * <blockquote><pre>The SamrQueryInformationGroup method obtains attributes from a group object.
 *      long SamrQueryInformationGroup(
 *          [in] SAMPR_HANDLE GroupHandle,
 *          [in] GROUP_INFORMATION_CLASS GroupInformationClass,
 *          [out, switch_is(GroupInformationClass)]
 *          PSAMPR_GROUP_INFO_BUFFER* Buffer
 *      );
 *  GroupHandle: An RPC context handle, as specified in section 2.2.3.2, representing a group object.
 *  GroupInformationClass: An enumeration indicating which attributes to return. See section 2.2.5.6 for a listing of possible values.
 *  Buffer: The requested attributes on output. See section 2.2.5.7 for structure details.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 *  Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *
 *  1. The server MUST return an error if GroupHandle.HandleType is not equal to "Group".
 *  2. GroupHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *  3. The following information levels MUST be processed by setting the appropriate output field name to either the associated database attribute or the value resulting from the associated processing rules, as specified in section 3.1.5.14.9. Processing is completed by returning 0 on success.
 *      GroupGeneralInformation
 *      GroupNameInformation
 *      GroupAttributeInformation
 *      GroupAdminCommentInformation
 *  4. If GroupInformationClass does not meet the criteria of constraint 3, the constraints associated with the GroupInformationClass input value in the following subsections MUST be satisfied; if there is no subsection for the GroupInformationClass value, an error MUST be returned to the client.</pre></blockquote>
 */
public abstract class SamrQueryInformationGroupRequest<T extends Unmarshallable> extends RequestCall<SamrQueryInformationGroupResponse<T>> {
    public static final short OP_NUM = 20;

    private final byte[] groupHandle;

    SamrQueryInformationGroupRequest(byte[] groupHandle) {
        super(OP_NUM);
        this.groupHandle = groupHandle;
    }

    public byte[] getGroupHandle() {
        return groupHandle;
    }

    public abstract GroupInformationClass getGroupInformationClass();

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: struct> [in] SAMPR_HANDLE GroupHandle,
        packetOut.write(getGroupHandle());
        // <NDR: unsigned short> [in] GROUP_INFORMATION_CLASS GroupInformationClass,
        // Alignment: 2 - Already aligned. ContextHandle writes 20 bytes above
        packetOut.writeShort(getGroupInformationClass().getInfoLevel());
    }

    public static class GroupGeneralInformation extends SamrQueryInformationGroupRequest<SAMPRGroupGeneralInformation> {
        public GroupGeneralInformation(byte[] groupHandle) {
            super(groupHandle);
        }

        @Override
        public GroupInformationClass getGroupInformationClass() {
            return GroupInformationClass.GROUP_GENERAL_INFORMATION;
        }

        @Override
        public SamrQueryInformationGroupResponse.GroupGeneralInformation getResponseObject() {
            return new SamrQueryInformationGroupResponse.GroupGeneralInformation();
        }
    }
}
