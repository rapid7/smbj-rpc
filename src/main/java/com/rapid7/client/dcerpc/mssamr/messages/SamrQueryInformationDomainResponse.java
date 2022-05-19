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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mslsad.objects.DomainInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainGeneralInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainGeneralInformation2;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLockoutInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLogoffInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainModifiedInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainModifiedInformation2;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainNameInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainOemInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainPasswordInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainReplicationInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainServerRoleInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainStateInformation;

public abstract class SamrQueryInformationDomainResponse<T extends Unmarshallable> extends RequestResponse {
	private T domainInformation;

	public T getDomainInformation() { return domainInformation; }
	public abstract DomainInformationClass getDomainInformationClass();
	abstract T createDomainInformation();
	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		if (packetIn.readReferentID() != 0) {
			final int infoLevel = packetIn.readShort();
			if (infoLevel != getDomainInformationClass().getInfoLevel()) {
				throw new IllegalArgumentException(
						String.format(
								"Incoming DOMAIN_INFORMATION_CLASS %d does not match expected: %d", infoLevel,
								getDomainInformationClass().getInfoLevel()
						)
				);
			}
			this.domainInformation = createDomainInformation();
			packetIn.readUnmarshallable(this.domainInformation);
		}
		else {
			this.domainInformation = null;
		}
	}

	public static class DomainPasswordInformation extends SamrQueryInformationDomainResponse<SAMPRDomainPasswordInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_PASSWORD_INFORMATION; }
		@Override
		SAMPRDomainPasswordInformation createDomainInformation() {
			return new SAMPRDomainPasswordInformation();
		}
	}
	public static class DomainGeneralInformation extends SamrQueryInformationDomainResponse<SAMPRDomainGeneralInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_GENERAL_INFORMATION; }
		@Override
		SAMPRDomainGeneralInformation createDomainInformation() {
			return new SAMPRDomainGeneralInformation();
		}
	}
	public static class DomainLogOffInformation extends SamrQueryInformationDomainResponse<SAMPRDomainLogoffInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_LOGOFF_INFORMATION; }
		@Override
		SAMPRDomainLogoffInformation createDomainInformation() {
			return new SAMPRDomainLogoffInformation();
		}
	}
	public static class DomainOemInformation extends SamrQueryInformationDomainResponse<SAMPRDomainOemInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_OEM_INFORMATION; }
		@Override
		SAMPRDomainOemInformation createDomainInformation() {
			return new SAMPRDomainOemInformation();
		}
	}
	public static class DomainNameInformation extends SamrQueryInformationDomainResponse<SAMPRDomainNameInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_NAME_INFORMATION; }
		@Override
		SAMPRDomainNameInformation createDomainInformation() {
			return new SAMPRDomainNameInformation();
		}
	}
	public static class DomainReplicationInformation extends SamrQueryInformationDomainResponse<SAMPRDomainReplicationInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_REPLICATION_INFORMATION; }
		@Override
		SAMPRDomainReplicationInformation createDomainInformation() {
			return new SAMPRDomainReplicationInformation();
		}
	}
	public static class DomainServerRoleInformation extends SamrQueryInformationDomainResponse<SAMPRDomainServerRoleInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_SERVERROLE_INFORMATION; }
		@Override
		SAMPRDomainServerRoleInformation createDomainInformation() {
			return new SAMPRDomainServerRoleInformation();
		}
	}
	public static class DomainModifiedInformation extends SamrQueryInformationDomainResponse<SAMPRDomainModifiedInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_MODIFIED_INFORMATION; }
		@Override
		SAMPRDomainModifiedInformation createDomainInformation() {
			return new SAMPRDomainModifiedInformation();
		}
	}
	public static class DomainStateInformation extends SamrQueryInformationDomainResponse<SAMPRDomainStateInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_STATE_INFORMATION; }
		@Override
		SAMPRDomainStateInformation createDomainInformation() {
			return new SAMPRDomainStateInformation();
		}
	}
	public static class DomainGeneralInformation2 extends SamrQueryInformationDomainResponse<SAMPRDomainGeneralInformation2> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_GENERAL_INFORMATION2; }
		@Override
		SAMPRDomainGeneralInformation2 createDomainInformation() {
			return new SAMPRDomainGeneralInformation2();
		}
	}
	public static class DomainLockoutInformation extends SamrQueryInformationDomainResponse<SAMPRDomainLockoutInformation> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_LOCKOUT_INFORMATION; }
		@Override
		SAMPRDomainLockoutInformation createDomainInformation() {
			return new SAMPRDomainLockoutInformation();
		}
	}
	public static class DomainModifiedInformation2 extends SamrQueryInformationDomainResponse<SAMPRDomainModifiedInformation2> {
		@Override
		public DomainInformationClass getDomainInformationClass() { return DomainInformationClass.DOMAIN_MODIFIED_INFORMATION2; }
		@Override
		SAMPRDomainModifiedInformation2 createDomainInformation() {
			return new SAMPRDomainModifiedInformation2();
		}
	}
}
