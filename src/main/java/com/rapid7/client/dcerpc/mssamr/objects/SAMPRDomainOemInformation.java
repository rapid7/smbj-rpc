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
 * SAMPR_DOMAIN_OEM_INFORMATION
 *
 *  typedef struct _SAMPR_DOMAIN_OEM_INFORMATION {
   RPC_UNICODE_STRING OemInformation;
 } SAMPR_DOMAIN_OEM_INFORMATION,
  *PSAMPR_DOMAIN_OEM_INFORMATION;
  *
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/7cbb7ff0-e593-440d-8341-a3435195cdf1">SAMPR_DOMAIN_OEM_INFORMATION</a>
 *
 */
public class SAMPRDomainOemInformation implements Unmarshallable, Marshallable {
	// <NDR: struct> RPC_UNICODE_STRING OemInformation;
	private RPCUnicodeString.NonNullTerminated oemInformation;

	public RPCUnicodeString.NonNullTerminated getOemInformation() { return oemInformation; }
	public void setOemInformation(RPCUnicodeString.NonNullTerminated oemInformation) { this.oemInformation = oemInformation; }
	@Override
	public void unmarshalPreamble(PacketInput in) throws IOException {
		oemInformation = new RPCUnicodeString.NonNullTerminated();
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		oemInformation.unmarshalPreamble(in);
	}
	@Override
	public void unmarshalEntity(PacketInput in) throws IOException {
		// Structure Alignment: 8
		in.align(Alignment.EIGHT);
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		// Alignment: 4 - Already aligned
		oemInformation.unmarshalEntity(in);
	}
	@Override
	public void unmarshalDeferrals(PacketInput in) throws IOException {
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		oemInformation.unmarshalDeferrals(in);
	}
	@Override
	public void marshalPreamble(PacketOutput out) throws IOException {
		if (oemInformation == null)
			oemInformation = RPCUnicodeString.NonNullTerminated.of(null);
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		oemInformation.marshalPreamble(out);
	}
	@Override
	public void marshalEntity(PacketOutput out) throws IOException {
		// Structure Alignment: 4
		out.align(Alignment.EIGHT);
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		// Alignment: 4 - Already aligned
		oemInformation.marshalEntity(out);
	}
	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException {
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		oemInformation.marshalDeferrals(out);
	}
	@Override
	public int hashCode() {
		return Objects.hash(getOemInformation());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		else
			if (!(obj instanceof SAMPRDomainOemInformation)) {
				return false;
			}
		SAMPRDomainOemInformation other = (SAMPRDomainOemInformation) obj;
		return Objects.equals(getOemInformation(), other.getOemInformation());
	}
	@Override
	public String toString() {
		return String.format("SAMPRDomainOemInfo { oemInformation: %s }", getOemInformation());
	}
}
