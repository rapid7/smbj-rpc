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
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.DisplayInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainDisplayGroupBuffer;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainDisplayMachineBuffer;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainDisplayOemGroupBuffer;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainDisplayOemUserBuffer;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainDisplayUserBuffer;

/**
 * The SamrQueryDisplayInformation3 method obtains a listing of accounts in ascending name-sorted order, starting at a specified index.
 *
 * <pre>
 * long SamrQueryDisplayInformation3(
 *   [in] SAMPR_HANDLE DomainHandle,
 *   [in] DOMAIN_DISPLAY_INFORMATION DisplayInformationClass,
 *   [in] unsigned long Index,
 *   [in] unsigned long EntryCount,
 *   [in] unsigned long PreferredMaximumLength,
 *   [out] unsigned long* TotalAvailable,
 *   [out] unsigned long* TotalReturned,
 *   [out, switch_is(DisplayInformationClass)] PSAMPR_DISPLAY_INFO_BUFFER Buffer
 *  );
 *  </pre>
 *
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/7cd1c6d0-8819-4a58-9721-5be588c1d4ad">
 *       https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/7cd1c6d0-8819-4a58-9721-5be588c1d4ad</a>
 */
public abstract class SamrQueryDisplayInformation3Request<T extends Unmarshallable>
		extends RequestCall<SamrQueryDisplayInformation3Response<T>> {
	public static final short OP_NUM = 51;
	// <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
	private final byte[] domainHandle;
	// <NDR: short> [in] DOMAIN_DISPLAY_INFORMATION DisplayInformationClass
	// Supplied by child classes
	// <NDR: unsigned long> [in] unsigned long Index
	private final int index;
	// <NDR: unsigned long> [in] unsigned long EntryCount
	private final int entryCount;
	// <NDR: unsigned long> [in] unsigned long PreferredMaximumLength
	private final int preferredMaximumLength;

	public SamrQueryDisplayInformation3Request(byte[] domainHandle, int index, int entryCount, int preferredMaximumLength) {
		super(OP_NUM);
		this.domainHandle = domainHandle;
		this.index = index;
		this.entryCount = entryCount;
		this.preferredMaximumLength = preferredMaximumLength;
	}
	public abstract DisplayInformationClass getDisplayInformationClass();
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
		packetOut.write(this.domainHandle);
		// <NDR: short> [in] DOMAIN_DISPLAY_INFORMATION DisplayInformationClass
		// Alignment: 2 - Already aligned, wrote 20 bytes above
		packetOut.writeShort(getDisplayInformationClass().getInfoLevel());
		// <NDR: unsigned long> [in] unsigned long Index
		packetOut.pad(2);
		packetOut.writeInt(this.index);
		// <NDR: unsigned long> [in] unsigned long EntryCount
		// Alignment: 4 - Already aligned
		packetOut.writeInt(this.entryCount);
		// <NDR: unsigned long> [in] unsigned long PreferredMaximumLength
		// Alignment: 4 - Already aligned
		packetOut.writeInt(this.preferredMaximumLength);
	}

	public static class DomainDisplayUser extends SamrQueryDisplayInformation3Request<SAMPRDomainDisplayUserBuffer> {
		public DomainDisplayUser(byte[] domainHandle, int index, int entryCount, int preferredMaximumLength) {
			super(domainHandle, index, entryCount, preferredMaximumLength);
		}
		@Override
		public DisplayInformationClass getDisplayInformationClass() { return DisplayInformationClass.DomainDisplayUser; }
		@Override
		public SamrQueryDisplayInformation3Response.DomainDisplayUser getResponseObject() {
			return new SamrQueryDisplayInformation3Response.DomainDisplayUser();
		}
	}
	public static class DomainDisplayMachine extends SamrQueryDisplayInformation3Request<SAMPRDomainDisplayMachineBuffer> {
		public DomainDisplayMachine(byte[] domainHandle, int index, int entryCount, int preferredMaximumLength) {
			super(domainHandle, index, entryCount, preferredMaximumLength);
		}
		@Override
		public DisplayInformationClass getDisplayInformationClass() { return DisplayInformationClass.DomainDisplayMachine; }
		@Override
		public SamrQueryDisplayInformation3Response.DomainDisplayMachine getResponseObject() {
			return new SamrQueryDisplayInformation3Response.DomainDisplayMachine();
		}
	}
	public static class DomainDisplayGroup extends SamrQueryDisplayInformation3Request<SAMPRDomainDisplayGroupBuffer> {
		public DomainDisplayGroup(byte[] domainHandle, int index, int entryCount, int preferredMaximumLength) {
			super(domainHandle, index, entryCount, preferredMaximumLength);
		}
		@Override
		public DisplayInformationClass getDisplayInformationClass() { return DisplayInformationClass.DomainDisplayGroup; }
		@Override
		public SamrQueryDisplayInformation3Response.DomainDisplayGroup getResponseObject() {
			return new SamrQueryDisplayInformation3Response.DomainDisplayGroup();
		}
	}
	public static class DomainDisplayOemUser extends SamrQueryDisplayInformation3Request<SAMPRDomainDisplayOemUserBuffer> {
		public DomainDisplayOemUser(byte[] domainHandle, int index, int entryCount, int preferredMaximumLength) {
			super(domainHandle, index, entryCount, preferredMaximumLength);
		}
		@Override
		public DisplayInformationClass getDisplayInformationClass() { return DisplayInformationClass.DomainDisplayOemUser; }
		@Override
		public SamrQueryDisplayInformation3Response.DomainDisplayOemUser getResponseObject() {
			return new SamrQueryDisplayInformation3Response.DomainDisplayOemUser();
		}
	}
	public static class DomainDisplayOemGroup extends SamrQueryDisplayInformation3Request<SAMPRDomainDisplayOemGroupBuffer> {
		public DomainDisplayOemGroup(byte[] domainHandle, int index, int entryCount, int preferredMaximumLength) {
			super(domainHandle, index, entryCount, preferredMaximumLength);
		}
		@Override
		public DisplayInformationClass getDisplayInformationClass() { return DisplayInformationClass.DomainDisplayOemGroup; }
		@Override
		public SamrQueryDisplayInformation3Response.DomainDisplayOemGroup getResponseObject() {
			return new SamrQueryDisplayInformation3Response.DomainDisplayOemGroup();
		}
	}
}
