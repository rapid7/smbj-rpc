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
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mslsad.objects.DomainInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLogoffInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainPasswordInfo;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245779.aspx">SamrQueryInformationDomain</a>
 *
 * <blockquote>
 * <pre>
 * long SamrQueryInformationDomain(
 *  [in] SAMPR_HANDLE DomainHandle,
 *  [in] DOMAIN_INFORMATION_CLASS DomainInformationClass,
 *  [out, switch_is(DomainInformationClass)]
 *    PSAMPR_DOMAIN_INFO_BUFFER* Buffer
 * );
 * DomainHandle: An RPC context handle, as specified in section 2.2.3.2, representing a domain object.
 * DomainInformationClass: An enumeration indicating which attributes to return. See section 2.2.4.16 for a listing of possible values.
 * Buffer: The requested attributes on output. See section 2.2.4.17 for structure details.
 *
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a
 * different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *
 *   1.The server MUST return an error if DomainHandle.HandleType is not equal to "Domain".
 *   2.DomainHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *   3.The following information levels MUST be processed by setting the appropriate output field name to the associated database attribute, as specified
 *     in section 3.1.5.14.8. Processing is completed by returning 0 on success.
 * </pre>
 * </blockquote>
 */
public abstract class SamrQueryInformationDomainRequest<T extends Unmarshallable>
        extends RequestCall<SamrQueryInformationDomainResponse<T>> {

    public final static short OP_NUM = 8;

    // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
    private final byte[] domainHandle;

    public SamrQueryInformationDomainRequest(final byte[] domainHandle) {
        super(OP_NUM);
        this.domainHandle = domainHandle;
    }

    public byte[] getDomainHandle() {
        return domainHandle;
    }

    public abstract DomainInformationClass getDomainInformationClass();

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
        packetOut.write(this.domainHandle);
        // <NDR: short> switch_is(DomainInformationClass)
        // Alignment: 2 - Already aligned, wrote 20 bytes above
        packetOut.writeShort(getDomainInformationClass().getInfoLevel());
    }

    public static class DomainPasswordInformation extends SamrQueryInformationDomainRequest<SAMPRDomainPasswordInfo> {
        public DomainPasswordInformation(final byte[] domainHandle) {
            super(domainHandle);
        }

        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_PASSWORD_INFORMATION;
        }

        @Override
        public SamrQueryInformationDomainResponse.DomainPasswordInformation getResponseObject() {
            return new SamrQueryInformationDomainResponse.DomainPasswordInformation();
        }
    }

    public static class DomainLogOffInformation extends SamrQueryInformationDomainRequest<SAMPRDomainLogoffInfo> {
        public DomainLogOffInformation(final byte[] domainHandle) {
            super(domainHandle);
        }

        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_LOGOFF_INFORMATION;
        }

        @Override
        public SamrQueryInformationDomainResponse.DomainLogOffInformation getResponseObject() {
            return new SamrQueryInformationDomainResponse.DomainLogOffInformation();
        }
    }
}
