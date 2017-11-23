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
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 *  <h1 class="title">3.1.4.8 LsarLookupNames (Opnum 14)</h1>
 *  <div id="mainSection">
 *            <div id="mainBody">
 *                <div class="section" id="collapseableSection">
 *<p>The LsarLookupNames method translates a batch of <a href="https://msdn.microsoft.com/en-ca/library/cc234422.aspx#gt_f3ef2572-95cf-4c5c-b3c9-551fd648f409">security principal</a> names to
 *their <a href="https://msdn.microsoft.com/en-ca/library/cc234422.aspx#gt_83f2020d-0804-4840-a5ac-e06439d50f8d">SID</a> form. It also
 *returns the <a href="https://msdn.microsoft.com/en-ca/library/cc234422.aspx#gt_b0276eb2-4e65-4cf1-a718-e0920a614aca">domains</a> that
 *these names are a part of.</p>
 *<dl>
 *<dd>
 *<div><pre>&nbsp;NTSTATUS LsarLookupNames(
 *  [in] LSAPR_HANDLE PolicyHandle,
 *  [in, range(0,1000)] unsigned long Count,
 *  [in, size_is(Count)] PRPC_UNICODE_STRING Names,
 *  [out] PLSAPR_REFERENCED_DOMAIN_LIST* ReferencedDomains,
 *  [in, out] PLSAPR_TRANSLATED_SIDS TranslatedSids,
 *  [in] LSAP_LOOKUP_LEVEL LookupLevel,
 *  [in, out] unsigned long* MappedCount
 *);
 *</pre></div>
 *</dd></dl>
 *<p><strong>PolicyHandle: </strong>Context handle obtained by an <a href="https://msdn.microsoft.com/en-ca/library/cc234489.aspx">LsarOpenPolicy</a> or <a href="https://msdn.microsoft.com/en-ca/library/cc234486.aspx">LsarOpenPolicy2</a> call.</p>
 *<p><strong>Count: </strong>Number of names in the Names array.<a id="Appendix_A_Target_32"></a><a href="https://msdn.microsoft.com/en-ca/library/cc234510.aspx#Appendix_A_32">&lt;32&gt;</a></p>
 *<p><strong>Names: </strong>Contains the security principal names
 *to translate, as specified in section <a href="https://msdn.microsoft.com/en-ca/library/cc234492.aspx">3.1.4.5</a>.</p>
 *<p><strong>ReferencedDomains: </strong>On successful return,
 *contains the domain information for the domain to which each security principal
 *belongs. The domain information includes a NetBIOS domain name and a domain SID
 *for each entry in the list.</p>
 *<p><strong>TranslatedSids: </strong>On successful return, contains
 *the corresponding SID forms for security principal names in the <em>Names</em>
 *parameter. It MUST be ignored on input.</p>
 *<p><strong>LookupLevel: </strong>Specifies what scopes are to be
 *used during translation, as specified in section <a href="https://msdn.microsoft.com/en-ca/library/cc234458.aspx">2.2.16</a>.</p>
 *<p><strong>MappedCount: </strong>On successful return, contains
 *the number of names that are translated completely to their SID forms. This
 *parameter has no effect on message processing in any environment. It MUST be
 *ignored on input.</p>
 *<p><strong>Return Values: </strong>The following table contains a
 *summary of the return values that an implementation MUST return, as specified
 *by the message processing shown after the table.</p>
 * <a href="https://msdn.microsoft.com/en-ca/library/cc234495.aspx"> Table</a>
 *
 *<p>The behavior required when receiving an LsarLookupNames
 *message MUST be identical to that when receiving an <a href="https://msdn.microsoft.com/en-ca/library/cc234494.aspx">LsarLookupNames2</a> message,
 *with the following exceptions:</p>
 *<p>
 *Elements in the TranslatedSids output structure do not contain a <strong>Flags</strong>
 *field.</p>
 *<p>
 *Due to the absence of the <em>LookupOptions</em> and <em>ClientRevision</em>
 *parameters, the <a href="https://msdn.microsoft.com/en-ca/library/cc234422.aspx#gt_ae65dac0-cd24-4e83-a946-6d1097b71553">RPC server</a>
 *MUST assume that <em>LookupOptions</em> is 0 and <em>ClientRevision</em> is 1.</p>
 *<p>
 *The server MUST return STATUS_ACCESS_DENIED if neither of the
 *following conditions is true:</p>
 *
 *The RPC_C_AUTHN_NETLOGON security provider (as specified in <a href="https://msdn.microsoft.com/en-ca/library/cc243560.aspx">[MS-RPCE]</a>
 *section <a href="https://msdn.microsoft.com/en-ca/library/cc243578.aspx">2.2.1.1.7</a>)
 *and at least RPC_C_AUTHN_LEVEL_PKT_INTEGRITY authentication level (as specified
 *in [MS-RPCE] section <a href="https://msdn.microsoft.com/en-ca/library/cc243867.aspx">2.2.1.1.8</a>)
 *were used in this <a href="https://msdn.microsoft.com/en-ca/library/cc234422.aspx#gt_8a7f6700-8311-45bc-af10-82e10accd331">RPC</a>
 *message.
 *
 *The PolicyHandle was granted POLICY_LOOKUP_NAMES access.
 *
 *                </div>
 *            </div>
 *        </div>
 */

public class LsarLookupNamesRequest extends RequestCall<LsarLookupNamesResponse> {
    private final static short OP_NUM = 14;
    private final static int LSA_LOOKUP_NAMES_ALL = 0x1;

    private final String[] names;
    private final byte[] policyHandle;
    private final int lookupLevel;

    public LsarLookupNamesRequest(final byte[] policyHandle, final String[] names, int lookupLevel) {
        super(OP_NUM);
        this.names = names;
        this.policyHandle = policyHandle;
        this.lookupLevel = lookupLevel;
    }

    @Override
    public LsarLookupNamesResponse getResponseObject() {
        return new LsarLookupNamesResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut)
        throws IOException
    {
        packetOut.write(policyHandle);
        packetOut.writeInt(names.length);
        writeNames(packetOut);
        packetOut.align(Alignment.FOUR); // Names is variable length; align for SID

        //TODO: Write as packetOut.writeMarshalable(new LSAPRTranslatedSIDs())
        packetOut.writeInt(0); //count for SID
        packetOut.writeNull(); // SID

        packetOut.writeInt(lookupLevel);
        packetOut.writeNull(); // Count (ignored on input)
    }

    private void writeNames(final PacketOutput packetOut)
        throws IOException
    {
        packetOut.writeInt(names.length);

        RPCUnicodeString[] rpcNames = new RPCUnicodeString[names.length];
        for (int i = 0; i < rpcNames.length; i++){
            rpcNames[i] = RPCUnicodeString.NonNullTerminated.of(names[i]);
            rpcNames[i].marshalPreamble(packetOut);
        }
        for (RPCUnicodeString rpcName: rpcNames){
            rpcName.marshalEntity(packetOut);
        }
        for (RPCUnicodeString rpcName: rpcNames){
            rpcName.marshalDeferrals(packetOut);
        }
    }
}
