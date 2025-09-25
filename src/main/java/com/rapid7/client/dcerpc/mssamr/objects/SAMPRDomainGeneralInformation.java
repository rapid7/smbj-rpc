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
 * SAMPR_DOMAIN_GENERAL_INFORMATION
 *
    typedef struct _SAMPR_DOMAIN_GENERAL_INFORMATION {
   OLD_LARGE_INTEGER ForceLogoff;
   RPC_UNICODE_STRING OemInformation;
   RPC_UNICODE_STRING DomainName;
   RPC_UNICODE_STRING ReplicaSourceNodeName;
   OLD_LARGE_INTEGER DomainModifiedCount;
   unsigned long DomainServerState;
   unsigned long DomainServerRole;
   unsigned char UasCompatibilityRequired;
   unsigned long UserCount;
   unsigned long GroupCount;
   unsigned long AliasCount;
 } SAMPR_DOMAIN_GENERAL_INFORMATION,
  *PSAMPR_DOMAIN_GENERAL_INFORMATION;
  *
  *@see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/85973e1c-96f2-4c80-8135-b24d74ad7794">SAMPR_DOMAIN_GENERAL_INFORMATION</a>
  */
public class SAMPRDomainGeneralInformation implements Unmarshallable, Marshallable {
	// <NDR: hyper> LARGE_INTEGER ForceLogoff;
	private long forceLogoff;
	// <NDR: struct> RPC_UNICODE_STRING OemInformation;
	private RPCUnicodeString.NonNullTerminated oemInformation;
	// <NDR: struct> RPC_UNICODE_STRING DomainName;
	private RPCUnicodeString.NonNullTerminated domainName;
	// <NDR: struct> RPC_UNICODE_STRING ReplicaSourceNodeName;
	private RPCUnicodeString.NonNullTerminated replicaSourceNodeName;
	// <NDR: hyper> LARGE_INTEGER DomainModifiedCount;
	private long domainModifiedCount;
	// <NDR: unsigned long> ULONG DomainServerState;
	private long domainServerState;
	// <NDR: unsigned long> ULONG DomainServerRole;
	private long domainServerRole;
	// <NDR: boolean> unsigned char UasCompatibilityRequired;
	private char uasCompatibilityRequired;
	// <NDR: unsigned long> ULONG UserCount;
	private long userCount;
	// <NDR: unsigned long> ULONG GroupCount;
	private long groupCount;
	// <NDR: unsigned long> ULONG AliasCount;
	private long aliasCount;

	public long getForceLogoff() { return forceLogoff; }
	public void setForceLogoff(long forceLogoff) { this.forceLogoff = forceLogoff; }
	public RPCUnicodeString.NonNullTerminated getOemInformation() { return oemInformation; }
	public void setOemInformation(RPCUnicodeString.NonNullTerminated oemInformation) { this.oemInformation = oemInformation; }
	public RPCUnicodeString.NonNullTerminated getDomainName() { return domainName; }
	public void setDomainName(RPCUnicodeString.NonNullTerminated domainName) { this.domainName = domainName; }
	public RPCUnicodeString.NonNullTerminated getReplicaSourceNodeName() { return replicaSourceNodeName; }
	public void setReplicaSourceNodeName(RPCUnicodeString.NonNullTerminated replicaSourceNodeName) {
		this.replicaSourceNodeName = replicaSourceNodeName;
	}
	public long getDomainModifiedCount() { return domainModifiedCount; }
	public void setDomainModifiedCount(long domainModifiedCount) { this.domainModifiedCount = domainModifiedCount; }
	public long getDomainServerState() { return domainServerState; }
	public void setDomainServerState(long domainServerState) { this.domainServerState = domainServerState; }
	public long getDomainServerRole() { return domainServerRole; }
	public void setDomainServerRole(long domainServerRole) { this.domainServerRole = domainServerRole; }
	public char getUasCompatibilityRequired() { return uasCompatibilityRequired; }
	public void setUasCompatibilityRequired(char uasCompatibilityRequired) { this.uasCompatibilityRequired = uasCompatibilityRequired; }
	public long getUserCount() { return userCount; }
	public void setUserCount(long userCount) { this.userCount = userCount; }
	public long getGroupCount() { return groupCount; }
	public void setGroupCount(long groupCount) { this.groupCount = groupCount; }
	public long getAliasCount() { return aliasCount; }
	public void setAliasCount(long aliasCount) { this.aliasCount = aliasCount; }
	@Override
	public void unmarshalPreamble(PacketInput in) throws IOException {
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		oemInformation = new RPCUnicodeString.NonNullTerminated();
		oemInformation.unmarshalPreamble(in);
		// <NDR struct> RPC_UNICODE_STRING DomainName;
		domainName = new RPCUnicodeString.NonNullTerminated();
		domainName.unmarshalPreamble(in);
		// <NDR struct> RPC_UNICODE_STRING ReplicaSourceNodeName;
		replicaSourceNodeName = new RPCUnicodeString.NonNullTerminated();
		replicaSourceNodeName.unmarshalPreamble(in);
	}
	@Override
	public void unmarshalEntity(PacketInput in) throws IOException {
		// Structure Alignment: 4
		in.align(Alignment.EIGHT);
		// <NDR: hyper> OLD_LARGE_INTEGER ForceLogoff;
		// Alignment: 8 - Already aligned
		forceLogoff = in.readLong();
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		// Alignment: 4 - Already aligned
		oemInformation.unmarshalEntity(in);
		// <NDR: struct> RPC_UNICODE_STRING DomainName;
		// Alignment: 4 - Already aligned
		domainName.unmarshalEntity(in);
		// <NDR: struct> RPC_UNICODE_STRING ReplicaSourceNodeName;
		// Alignment: 4 - Already aligned
		replicaSourceNodeName.unmarshalEntity(in);
		// <NDR: hyper> OLD_LARGE_INTEGER DomainModifiedCount;
		// Alignment: 8 - Already aligned
		domainModifiedCount = in.readLong();
		// <NDR: unsigned long> unsigned long DomainServerState;
		// Alignment: 4 - Already aligned
		domainServerState = in.readUnsignedInt();
		// <NDR: unsigned long> unsigned long DomainServerRole;
		// Alignment: 4 - Already aligned
		domainServerRole = in.readUnsignedInt();
		// <NDR: unsigned char> unsigned char UasCompatibilityRequired;
		uasCompatibilityRequired = in.readChar();
		// <NDR: unsigned long> unsigned long UserCount;
		// Alignment: 4
		in.align(Alignment.FOUR);
		userCount = in.readUnsignedInt();
		// <NDR: unsigned long> unsigned long GroupCount;
		// Alignment: 4 - Already aligned
		groupCount = in.readUnsignedInt();
		// <NDR: unsigned long> unsigned long AliasCount;
		// Alignment: 4 - Already aligned
		aliasCount = in.readUnsignedInt();
	}
	@Override
	public void unmarshalDeferrals(PacketInput in) throws IOException {
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		oemInformation.unmarshalDeferrals(in);
		// <NDR struct> RPC_UNICODE_STRING DomainName;
		domainName.unmarshalDeferrals(in);
		// <NDR struct> RPC_UNICODE_STRING ReplicaSourceNodeName;
		replicaSourceNodeName.unmarshalDeferrals(in);
	}
	@Override
	public int hashCode() {
		return Objects.hash(
				getForceLogoff(), getOemInformation(), getDomainName(), getReplicaSourceNodeName(), getDomainModifiedCount(), getDomainServerState(),
				getDomainServerRole(), getUasCompatibilityRequired(), getUserCount(), getGroupCount(), getAliasCount()
		);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		else
			if (!(obj instanceof SAMPRDomainGeneralInformation)) {
				return false;
			}
		SAMPRDomainGeneralInformation other = (SAMPRDomainGeneralInformation) obj;
		return Objects.equals(getForceLogoff(), other.getForceLogoff())
				&& Objects.equals(getOemInformation(), other.getOemInformation())
				&& Objects.equals(getDomainName(), other.getDomainName())
				&& Objects.equals(getReplicaSourceNodeName(), other.getReplicaSourceNodeName())
				&& Objects.equals(getDomainModifiedCount(), other.getDomainModifiedCount())
				&& Objects.equals(getDomainServerState(), other.getDomainServerState())
				&& Objects.equals(getDomainServerRole(), other.getDomainServerRole())
				&& Objects.equals(getUasCompatibilityRequired(), other.getUasCompatibilityRequired())
				&& Objects.equals(getUserCount(), other.getUserCount())
				&& Objects.equals(getGroupCount(), other.getGroupCount())
				&& Objects.equals(getAliasCount(), other.getAliasCount());
	}
	@Override
	public String toString() {
		return String.format(
				"SAMPRDomainGeneralInfo {forceLogoff:%s, oemInformation:%s, domainName:%s, replicaSourceNodeName:%s, domainModifiedCount:%s "
						+ "domainServerState:%s, domainServerRole:%s, uasCompatibilityRequired:%s, userCount:%s, groupCount:%s, aliasCount:%s}",
				getForceLogoff(), getOemInformation(), getDomainName(), getReplicaSourceNodeName(), getDomainModifiedCount(),
				getDomainServerState(), getDomainServerRole(), (int) getUasCompatibilityRequired(), getUserCount(), getGroupCount(), getAliasCount()
		);
	}
	@Override
	public void marshalPreamble(PacketOutput out) throws IOException {
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		oemInformation.marshalPreamble(out);
		// <NDR struct> RPC_UNICODE_STRING DomainName;
		domainName.marshalPreamble(out);
		// <NDR struct> RPC_UNICODE_STRING ReplicaSourceNodeName;
		replicaSourceNodeName.marshalPreamble(out);
	}
	@Override
	public void marshalEntity(PacketOutput out) throws IOException {
		// Structure Alignment: 4
		out.align(Alignment.EIGHT);
		// <NDR: hyper> OLD_LARGE_INTEGER LastLogon;
		// Alignment: 8 - Already aligned
		out.writeLong(this.forceLogoff);
		// <NDR: struct> RPC_UNICODE_STRING OemInformation;
		// Alignment: 4 - Already aligned
		oemInformation.marshalEntity(out);
		// <NDR: struct> RPC_UNICODE_STRING DomainName;
		// Alignment: 4 - Already aligned
		domainName.marshalEntity(out);
		// <NDR: struct> RPC_UNICODE_STRING ReplicaSourceNodeName;
		// Alignment: 4 - Already aligned
		replicaSourceNodeName.marshalEntity(out);
		// <NDR: hyper> OLD_LARGE_INTEGER DomainModifiedCount;
		// Alignment: 8 - Already aligned
		out.writeLong(this.domainModifiedCount);
		// <NDR: unsigned long> unsigned long DomainServerState;
		// Alignment: 4 - Already aligned
		out.writeInt(this.domainServerState);
		// <NDR: unsigned long> unsigned long DomainServerRole;
		// Alignment: 4 - Already aligned
		out.writeInt(this.domainServerRole);
		// <NDR: unsigned char> unsigned char UasCompatibilityRequired;
		out.writeChar(uasCompatibilityRequired);
		// <NDR: unsigned long> unsigned long UserCount;
		// Alignment: 4
		out.align(Alignment.FOUR);
		out.writeInt(this.userCount);
		// <NDR: unsigned long> unsigned long GroupCount;
		// Alignment: 4 - Already aligned
		out.writeInt(this.groupCount);
		// <NDR: unsigned long> unsigned long AliasCount;
		// Alignment: 4 - Already aligned
		out.writeInt(this.aliasCount);
	}
	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException {
		// <NDR struct> RPC_UNICODE_STRING OemInformation;
		oemInformation.marshalDeferrals(out);
		// <NDR struct> RPC_UNICODE_STRING DomainName;
		domainName.marshalDeferrals(out);
		// <NDR struct> RPC_UNICODE_STRING ReplicaSourceNodeName;
		replicaSourceNodeName.marshalDeferrals(out);
	}
}
