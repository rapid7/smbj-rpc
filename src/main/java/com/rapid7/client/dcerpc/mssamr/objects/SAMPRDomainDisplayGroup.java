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
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * The SAMPR_DOMAIN_DISPLAY_GROUP structure contains a subset of group information sufficient to show a summary of the account for an account management application.
 *
 * <pre>
 * typedef struct _SAMPR_DOMAIN_DISPLAY_GROUP {
 *   unsigned long Index;
 *   unsigned long Rid;
 *   unsigned long Attributes;
 *   RPC_UNICODE_STRING AccountName;
 *   RPC_UNICODE_STRING AdminComment;
 * } SAMPR_DOMAIN_DISPLAY_GROUP,
 *  *PSAMPR_DOMAIN_DISPLAY_GROUP;
 * </pre>
 *
 * For information on each field, see section <a href=https://msdn.microsoft.com/en-us/library/cc245627.aspx>2.2.8.1</a>.
 *
 * @see <a href=https://msdn.microsoft.com/en-us/library/cc245634.aspx>
 *       https://msdn.microsoft.com/en-us/library/cc245634.aspx</a>
 */
public class SAMPRDomainDisplayGroup implements Unmarshallable {
    private int index;
    private int rid;
    private int attributes;
    private RPCUnicodeString.NonNullTerminated accountName;
    private RPCUnicodeString.NonNullTerminated description;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    public int getIndex() {
        return index;
    }

    public int getRid() {
        return rid;
    }

    public int getAttributes() {
        return attributes;
    }

    public RPCUnicodeString.NonNullTerminated getAccountName() {
        return accountName;
    }

    public RPCUnicodeString.NonNullTerminated getDescription() {
        return description;
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        index = in.readInt();
        rid = in.readInt();
        attributes = in.readInt();
        accountName = new RPCUnicodeString.NonNullTerminated();
        accountName.unmarshalEntity(in);
        description = new RPCUnicodeString.NonNullTerminated();
        description.unmarshalEntity(in);
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        accountName.unmarshalDeferrals(in);
        description.unmarshalDeferrals(in);
    }
}
