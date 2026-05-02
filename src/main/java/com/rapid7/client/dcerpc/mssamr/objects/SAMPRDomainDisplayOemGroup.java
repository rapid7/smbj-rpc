/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * The SAMPR_DOMAIN_DISPLAY_OEM_GROUP structure contains a subset of group information sufficient to show a summary of the account for an account management application. This structure exists to support non–Unicode-based systems.
 *
 * <pre>
 * typedef struct _SAMPR_DOMAIN_DISPLAY_OEM_GROUP  {
 *   unsigned long Index;
 *   RPC_STRING OemAccountName;
 * } SAMPR_DOMAIN_DISPLAY_OEM_GROUP,
 *  *PSAMPR_DOMAIN_DISPLAY_OEM_GROUP;
 * </pre>
 *
 * For information on each field, see section <a href=https://msdn.microsoft.com/en-us/library/cc245627.aspx>2.2.8.1</a>.
 *
 * @see <a href=https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/5392e27d-9f92-4803-b6b7-0a9f02497443>
 *       SAMPR_DOMAIN_DISPLAY_OEM_GROUP</a>
 */
public class SAMPRDomainDisplayOemGroup implements Unmarshallable {
	private int index;
	private RPCUnicodeString.NullTerminated oemAccountName;

	@Override
	public void unmarshalPreamble(PacketInput in) throws IOException {
	}
	public int getIndex() { return index; }
	public RPCUnicodeString.NullTerminated getOemAccountName() { return oemAccountName; }
	@Override
	public void unmarshalEntity(PacketInput in) throws IOException {
		index = in.readInt();
		oemAccountName = new RPCUnicodeString.NullTerminated();
		oemAccountName.unmarshalEntity(in);
	}
	@Override
	public void unmarshalDeferrals(PacketInput in) throws IOException {
		oemAccountName.unmarshalDeferrals(in);
	}
}
