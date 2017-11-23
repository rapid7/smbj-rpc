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
package com.rapid7.client.dcerpc.mslsad.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRSIDEnumBuffer;
import com.rapid7.client.dcerpc.objects.RPCSID;

/**
 *    <h1 class="title">3.1.4.11 LsarLookupSids (Opnum 15)</h1>
 *
 *   <div id="mainSection">
 *             <div id="mainBody">
 *
 *                 <div class="section" id="collapseableSection">
 *
 *
 * <p>The LsarLookupSids method translates a batch of <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_f3ef2572-95cf-4c5c-b3c9-551fd648f409">security principal</a> <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_83f2020d-0804-4840-a5ac-e06439d50f8d">SIDs</a> to their name forms.
 * It also returns the <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_b0276eb2-4e65-4cf1-a718-e0920a614aca">domains</a>
 * that these names are a part of.</p>
 *
 * <dl>
 * <dd>
 * <div><pre> NTSTATUS LsarLookupSids(
 *    [in] LSAPR_HANDLE PolicyHandle,
 *    [in] PLSAPR_SID_ENUM_BUFFER SidEnumBuffer,
 *    [out] PLSAPR_REFERENCED_DOMAIN_LIST* ReferencedDomains,
 *    [in, out] PLSAPR_TRANSLATED_NAMES TranslatedNames,
 *    [in] LSAP_LOOKUP_LEVEL LookupLevel,
 *    [in, out] unsigned long* MappedCount
 *  );
 * </pre></div>
 * </dd></dl>
 *
 * <p><strong>PolicyHandle: </strong>Context handle obtained by an <a href="https://msdn.microsoft.com/en-us/library/cc234489.aspx">LsarOpenPolicy</a> or <a href="https://msdn.microsoft.com/en-us/library/cc234486.aspx">LsarOpenPolicy2</a> call.</p>
 *
 * <p><strong>SidEnumBuffer: </strong>Contains the SIDs to be
 * translated. The SIDs in this structure can be that of users, groups, computers,
 * Windows-defined well-known security principals, or domains.</p>
 *
 * <p><strong>ReferencedDomains: </strong>On successful return,
 * contains the domain information for the domain to which each security principal
 * belongs. The domain information includes a NetBIOS domain name and a domain SID
 * for each entry in the list.</p>
 *
 * <p><strong>TranslatedNames: </strong>On successful return,
 * contains the corresponding name form for security principal SIDs in the <em>SidEnumBuffer</em>
 * parameter. It MUST be ignored on input.</p>
 *
 * <p><strong>LookupLevel: </strong>Specifies what scopes are to be
 * used during translation, as specified in section <a href="https://msdn.microsoft.com/en-us/library/cc234458.aspx">2.2.16</a>.</p>
 *
 * <p><strong>MappedCount: </strong>On successful return, contains
 * the number of names that are translated completely to their Name forms. It MUST
 * be ignored on input.</p>
 *
 * <p><strong>Return Values: </strong>The following table contains a
 * summary of the return values that an implementation MUST return, as specified
 * by the message processing shown after the table.</p>
 * See table at: https://msdn.microsoft.com/en-us/library/cc234488.aspx
 *
 * <p>The behavior required when receiving an LsarLookupSids
 * message MUST be identical to that when receiving an <a href="https://msdn.microsoft.com/en-us/library/cc234487.aspx">LsarLookupSids2</a> message,
 * with the following exceptions:</p>
 *
 * <ul><li><p>Elements in the TranslatedNames output structure do not contain a
 * Flags field.</p>
 *
 * </li><li><p>Due to the absence of <em>LookupOptions</em> and <em>ClientRevision</em>
 * parameters, the <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_ae65dac0-cd24-4e83-a946-6d1097b71553">RPC server</a>
 * MUST assume that <em>LookupOptions</em> is 0 and <em>ClientRevision</em> is 1.</p>
 *
 * </li><li><p>The server MUST return STATUS_ACCESS_DENIED if neither of the
 * following conditions is true:</p>
 *
 * <ol><li><p>The RPC_C_AUTHN_NETLOGON security provider (as specified in <a href="https://msdn.microsoft.com/en-us/library/cc243560.aspx">[MS-RPCE]</a>
 * section <a href="https://msdn.microsoft.com/en-us/library/cc243578.aspx">2.2.1.1.7</a>)
 * and at least RPC_C_AUTHN_LEVEL_PKT_INTEGRITY authentication level (as specified
 * in [MS-RPCE] section <a href="https://msdn.microsoft.com/en-us/library/cc243867.aspx">2.2.1.1.8</a>)
 * were used in this <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_8a7f6700-8311-45bc-af10-82e10accd331">RPC</a>
 * message.</p>
 *
 * </li><li><p>The PolicyHandle was granted POLICY_LOOKUP_NAMES access.</p>
 *
 * </li></ol></li></ul>
 *                 </div>
 *             </div>
 *         </div>
 *
 */

public class LsarLookupSIDsRequest extends RequestCall<LsarLookupSIDsResponse> {
    private final static short OP_NUM = 15;

    // <NDR: fixed array> [in] LSAPR_HANDLE PolicyHandle
    private final byte[] policyHandle;
    // <NDR: struct> [in] PLSAPR_SID_ENUM_BUFFER SidEnumBuffer
    private final LSAPRSIDEnumBuffer lsaprsidEnumBuffer;
    // <NDR: short> [in] LSAP_LOOKUP_LEVEL LookupLevel
    private final short lookupLevel;
    // <NDR: unsigned long> [in, out] unsigned long* MappedCount
    // Only considered during marshalling

    public LsarLookupSIDsRequest(final byte[] policyHandle, final RPCSID[] rpcSIDs, final short lookupLevel) {
        super(OP_NUM);
        this.policyHandle = policyHandle;
        this.lsaprsidEnumBuffer = new LSAPRSIDEnumBuffer(rpcSIDs);
        this.lookupLevel = lookupLevel;
    }

    @Override
    public LsarLookupSIDsResponse getResponseObject() {
        return new LsarLookupSIDsResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] LSAPR_HANDLE PolicyHandle
        // Alignment: 1 - Already aligned
        packetOut.write(policyHandle);
        // <NDR: struct> [in] PLSAPR_SID_ENUM_BUFFER SidEnumBuffer
        packetOut.writeMarshallable(lsaprsidEnumBuffer);
        // <NDR: struct> [in, out] PLSAPR_TRANSLATED_NAMES TranslatedNames
        packetOut.align(Alignment.FOUR);
        packetOut.writeInt(0); //count of names
        packetOut.writeNull();
        // <NDR: short> [in] LSAP_LOOKUP_LEVEL LookupLevel
        // Alignment: 2 - Already aligned
        packetOut.writeShort(lookupLevel);
        // <NDR: unsigned long> [in, out] unsigned long* MappedCount
        packetOut.pad(2);
        // <NDR: unsigned long> [in, out] unsigned long* MappedCount
        // Alignment: 4 - Already aligned
        packetOut.writeNull(); // Count (ignored on input)
    }
}
