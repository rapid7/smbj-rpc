/* Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. */
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * SAMPR_DOMAIN_NAME_INFORMATION
 *
 *  typedef struct _SAMPR_DOMAIN_NAME_INFORMATION {
   RPC_UNICODE_STRING DomainName;
 } SAMPR_DOMAIN_NAME_INFORMATION,
  *PSAMPR_DOMAIN_NAME_INFORMATION;
  *
  *@see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/5131d2c0-04c7-4c1b-8fd5-0b0b6cfa6c24">SAMPR_DOMAIN_NAME_INFORMATION</a>
 */
public class SAMPRDomainNameInformation implements Unmarshallable, Marshallable {
	// <NDR: struct> RPC_UNICODE_STRING DomainName;
	private RPCUnicodeString.NonNullTerminated domainName;

	public RPCUnicodeString.NonNullTerminated getDomainName() { return domainName; }
	public void setDomainName(RPCUnicodeString.NonNullTerminated domainName) { this.domainName = domainName; }
	@Override
	public void unmarshalPreamble(PacketInput in) throws IOException {
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		domainName = new RPCUnicodeString.NonNullTerminated();
		domainName.unmarshalPreamble(in);
	}
	@Override
	public void unmarshalEntity(PacketInput in) throws IOException {
		// Structure Alignment: 8
		in.align(Alignment.EIGHT);
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		// Alignment: 4 - Already aligned
		domainName.unmarshalEntity(in);
	}
	@Override
	public void unmarshalDeferrals(PacketInput in) throws IOException {
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		domainName.unmarshalDeferrals(in);
	}
	@Override
	public void marshalPreamble(PacketOutput out) throws IOException {
		if (domainName == null)
			domainName = RPCUnicodeString.NonNullTerminated.of(null);
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		domainName.marshalPreamble(out);
	}
	@Override
	public void marshalEntity(PacketOutput out) throws IOException {
		// Structure Alignment: 4
		out.align(Alignment.EIGHT);
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		// Alignment: 4 - Already aligned
		domainName.marshalEntity(out);
	}
	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException {
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		domainName.marshalDeferrals(out);
	}
	@Override
	public int hashCode() {
		return Objects.hash(getDomainName());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		else
			if (!(obj instanceof SAMPRDomainNameInformation)) {
				return false;
			}
		SAMPRDomainNameInformation other = (SAMPRDomainNameInformation) obj;
		return Objects.equals(getDomainName(), other.getDomainName());
	}
	@Override
	public String toString() {
		return String.format("SAMPRDomainNameInfo{domainName:%s}", getDomainName());
	}
}
