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
 * SAMPR_DOMAIN_REPLICATION_INFORMATION
 *
 typedef struct SAMPR_DOMAIN_REPLICATION_INFORMATION {
   RPC_UNICODE_STRING ReplicaSourceNodeName;
 } SAMPR_DOMAIN_REPLICATION_INFORMATION,
  *PSAMPR_DOMAIN_REPLICATION_INFORMATION;
  *
  *@see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/5131d2c0-04c7-4c1b-8fd5-0b0b6cfa6c24>SAMPR_DOMAIN_REPLICATION_INFORMATION</a>
 */
public class SAMPRDomainReplicationInformation implements Unmarshallable, Marshallable {
	// <NDR: struct> RPC_UNICODE_STRING ReplicaSourceNodeName;
	private RPCUnicodeString.NonNullTerminated replicaSourceNodeName;

	public RPCUnicodeString.NonNullTerminated getReplicaSourceNodeName() { return replicaSourceNodeName; }
	public void setReplicaSourceNodeName(RPCUnicodeString.NonNullTerminated replicaSourceNodeName) {
		this.replicaSourceNodeName = replicaSourceNodeName;
	}
	@Override
	public void unmarshalPreamble(PacketInput in) throws IOException {
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		replicaSourceNodeName = new RPCUnicodeString.NonNullTerminated();
		replicaSourceNodeName.unmarshalPreamble(in);
	}
	@Override
	public void unmarshalEntity(PacketInput in) throws IOException {
		// Structure Alignment: 8
		in.align(Alignment.EIGHT);
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		// Alignment: 4 - Already aligned
		replicaSourceNodeName.unmarshalEntity(in);
	}
	@Override
	public void unmarshalDeferrals(PacketInput in) throws IOException {
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		replicaSourceNodeName.unmarshalDeferrals(in);
	}
	@Override
	public void marshalPreamble(PacketOutput out) throws IOException {
		if (replicaSourceNodeName == null)
			replicaSourceNodeName = RPCUnicodeString.NonNullTerminated.of(null);
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		replicaSourceNodeName.marshalPreamble(out);
	}
	@Override
	public void marshalEntity(PacketOutput out) throws IOException {
		// Structure Alignment: 4
		out.align(Alignment.EIGHT);
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		// Alignment: 4 - Already aligned
		replicaSourceNodeName.marshalEntity(out);
	}
	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException {
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		replicaSourceNodeName.marshalDeferrals(out);
	}
	@Override
	public int hashCode() {
		return Objects.hash(getReplicaSourceNodeName());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		else
			if (!(obj instanceof SAMPRDomainReplicationInformation)) {
				return false;
			}
		SAMPRDomainReplicationInformation other = (SAMPRDomainReplicationInformation) obj;
		return Objects.equals(getReplicaSourceNodeName(), other.getReplicaSourceNodeName());
	}
	@Override
	public String toString() {
		return String.format("SAMPR_DOMAIN_REPLICATION_INFORMATION { replicaSourceNodeName:%s }", getReplicaSourceNodeName());
	}
}
