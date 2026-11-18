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
import com.rapid7.client.dcerpc.mssamr.objects.AliasInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRAliasGeneralInformation;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245782.aspx">SamrQueryInformationAlias</a>
 *
 * <blockquote><pre>The SamrQueryInformationAlias method obtains attributes from an alias object.
 *      long SamrQueryInformationAlias(
 *          [in] SAMPR_HANDLE AliasHandle,
 *          [in] ALIAS_INFORMATION_CLASS AliasInformationClass,
 *          [out, switch_is(AliasInformationClass)]
 *          PSAMPR_ALIAS_INFO_BUFFER* Buffer
 *      );
 *  AliasHandle: An RPC context handle, as specified in section 2.2.3.2, representing an alias object.
 *  AliasInformationClass: An enumeration indicating which attributes to return. See section 2.2.6.5 for a listing of possible values.
 *  Buffer: The requested attributes on output. See section 2.2.6.6 for structure details.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 *
 *  Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *      1. The server MUST return an error if AliasHandle.HandleType is not equal to "Alias".
 *      2. AliasHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *      3. The following information levels MUST be processed by setting the appropriate output field name to the associated database attribute, as specified in section 3.1.5.14.10. Processing is completed by returning 0 on success. If the presented information level is not in the following table, the server MUST return an error.</pre></blockquote>
 */
public abstract class SamrQueryInformationAliasRequest<T extends Unmarshallable> extends RequestCall<SamrQueryInformationAliasResponse<T>> {
    public static final short OP_NUM = 28;

    // <NDR: fixed array> [in] SAMPR_HANDLE AliasHandle
    private final byte[] aliasHandle;

    SamrQueryInformationAliasRequest(byte[] aliasHandle) {
        super(OP_NUM);
        this.aliasHandle = aliasHandle;
    }

    public byte[] getAliasHandle() {
        return aliasHandle;
    }

    public abstract AliasInformationClass getAliasInformationClass();

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SAMPR_HANDLE AliasHandle
        packetOut.write(getAliasHandle());
        // <NDR: unsigned short> [in] ALIAS_INFORMATION_CLASS AliasInformationClass,
        // Alignment: 2 - Already aligned. ContextHandle writes 20 bytes above
        packetOut.writeShort(getAliasInformationClass().getInfoLevel());
    }

    public static class AliasGeneralInformation extends SamrQueryInformationAliasRequest<SAMPRAliasGeneralInformation> {
        public AliasGeneralInformation(byte[] aliasHandle) {
            super(aliasHandle);
        }

        @Override
        public AliasInformationClass getAliasInformationClass() {
            return AliasInformationClass.ALIAS_GENERAL_INFORMATION;
        }

        @Override
        public SamrQueryInformationAliasResponse.AliasGeneralInformation getResponseObject() {
            return new SamrQueryInformationAliasResponse.AliasGeneralInformation();
        }
    }
}
