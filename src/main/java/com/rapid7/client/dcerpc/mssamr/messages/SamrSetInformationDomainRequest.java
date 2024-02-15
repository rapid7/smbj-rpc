package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mslsad.objects.DomainInformationClass;
import com.rapid7.client.dcerpc.mssamr.dto.DomainHandle;
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

/**
 * <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/9b7ae0b0-bd11-4133-9c62-fba7095aee12">SamrSetInformationDomain (Opnum 9)</a>
 * <blockquote><pre>The SamrSetInformationDomain method updates attributes on a domain object.
 *
 * 	long SamrSetInformationDomain(
 *       [in] SAMPR_HANDLE DomainHandle,
 *       [in] DOMAIN_INFORMATION_CLASS DomainInformationClass,
 *       [in, switch_is(DomainInformationClass)]
 *       PSAMPR_DOMAIN_INFO_BUFFER DomainInformation
 *      );
 *      
 * DomainHandle: An RPC context handle, as specified in section 2.2.3.2, representing a domain object.
 * DomainInformationClass: An enumeration indicating which attributes to update. See section 2.2.4.16 for a list of possible values.
 * DomainInformation: The requested attributes and values to update. See section 2.2.4.17 for structure details.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints.
 * 1.    The server MUST return an error if DomainHandle.HandleType is not equal to "Domain".
 * 2.    DomainHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 * 3.    The following information levels MUST be processed by setting the database attribute on the domain object associated with DomainHandle.Object to the associated input field-name value using the mapping in section 3.1.5.14.8. All updates MUST be performed in the same transaction.
 *     DomainInformationClass
 *     DomainLockoutInformation
 *     DomainLogoffInformation
 *     DomainOemInformation
 *     DomainReplicationInformation
 * 4.    If DomainInformationClass does not meet the criteria of constraint 3, the constraints associated with the DomainInformationClass input value in the following subsections MUST be satisfied. If there is no subsection for the DomainInformationClass value, an error MUST be returned to the client.
 *     </pre></blockquote>
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/9b7ae0b0-bd11-4133-9c62-fba7095aee12">SamrSetInformationDomain  (Opnum 9)</a>
 */

public abstract class SamrSetInformationDomainRequest <T extends Marshallable> extends RequestCall<SamrSetInformationDomainResponse> {
    public static final short OP_NUM = 9;
    // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
	private DomainHandle domainHandle;
    // <NDR: unsigned short> [in] DOMAIN_INFORMATION_CLASS  DomainInformationClass,
    private T domainInformation;

    public T getDomainInformation() {
        return domainInformation;
    }
    
    public DomainHandle getDomainHandle() {
        return domainHandle;
    }
    
    public abstract DomainInformationClass getDomainInformationClass();
    
    public SamrSetInformationDomainRequest(final DomainHandle domainHandle, T domainInformation) {
        super(OP_NUM);
        this.domainHandle = domainHandle;
        this.domainInformation = domainInformation;
    }
    
	@Override
	public void marshal(PacketOutput packetOut) throws IOException { 
		packetOut.write(domainHandle.getBytes());		
		packetOut.writeShort(getDomainInformationClass().getInfoLevel());
		packetOut.writeShort(getDomainInformationClass().getInfoLevel());
		domainInformation.marshalPreamble(packetOut);		
		domainInformation.marshalEntity(packetOut);		
		domainInformation.marshalDeferrals(packetOut);		
	}

	@Override
	public SamrSetInformationDomainResponse getResponseObject() { 
		return new SamrSetInformationDomainResponse();
	}
	
    public static class DomainPasswordInformation extends SamrSetInformationDomainRequest<SAMPRDomainPasswordInformation> {
        public DomainPasswordInformation(DomainHandle domainHandle, SAMPRDomainPasswordInformation domainInformation) {
            super(domainHandle, domainInformation);
        }

        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_PASSWORD_INFORMATION;
        }
    }

    public static class DomainGeneralInformation extends SamrSetInformationDomainRequest<SAMPRDomainGeneralInformation> {
        public DomainGeneralInformation(DomainHandle domainHandle, SAMPRDomainGeneralInformation domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_GENERAL_INFORMATION;
        }
    }

    public static class DomainLogOffInformation extends SamrSetInformationDomainRequest<SAMPRDomainLogoffInformation> {
        public DomainLogOffInformation(DomainHandle domainHandle, SAMPRDomainLogoffInformation domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_LOGOFF_INFORMATION;
        }
    }

    public static class DomainOemInformation extends SamrSetInformationDomainRequest<SAMPRDomainOemInformation> {
        public DomainOemInformation(DomainHandle domainHandle, SAMPRDomainOemInformation domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_OEM_INFORMATION;
        }
    }

    public static class DomainNameInformation extends SamrSetInformationDomainRequest<SAMPRDomainNameInformation> {
        public DomainNameInformation(DomainHandle domainHandle, SAMPRDomainNameInformation domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_NAME_INFORMATION;
        }
    }

    public static class DomainReplicationInformation extends SamrSetInformationDomainRequest<SAMPRDomainReplicationInformation> {
        public DomainReplicationInformation(DomainHandle domainHandle, SAMPRDomainReplicationInformation domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_REPLICATION_INFORMATION;
        }
    }

    public static class DomainServerRoleInformation extends SamrSetInformationDomainRequest<SAMPRDomainServerRoleInformation> {
        public DomainServerRoleInformation(DomainHandle domainHandle, SAMPRDomainServerRoleInformation domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_SERVERROLE_INFORMATION;
        }
    }

    public static class DomainModifiedInformation extends SamrSetInformationDomainRequest<SAMPRDomainModifiedInformation> {
        public DomainModifiedInformation(DomainHandle domainHandle, SAMPRDomainModifiedInformation domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_LOGOFF_INFORMATION;
        }
    }

    public static class DomainStateInformation extends SamrSetInformationDomainRequest<SAMPRDomainStateInformation> {
        public DomainStateInformation(DomainHandle domainHandle, SAMPRDomainStateInformation domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_STATE_INFORMATION;
        }
    }


    public static class DomainGeneralInformation2 extends SamrSetInformationDomainRequest<SAMPRDomainGeneralInformation2> {
        public DomainGeneralInformation2(DomainHandle domainHandle, SAMPRDomainGeneralInformation2 domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_GENERAL_INFORMATION2;
        }
    }

    public static class DomainLockoutInformation extends SamrSetInformationDomainRequest<SAMPRDomainLockoutInformation> {
        public DomainLockoutInformation(DomainHandle domainHandle, SAMPRDomainLockoutInformation domainInformation) {
            super(domainHandle, domainInformation);
        }

        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_LOCKOUT_INFORMATION;
        }
    }

    public static class DomainModifiedInformation2 extends SamrSetInformationDomainRequest<SAMPRDomainModifiedInformation2> {
        public DomainModifiedInformation2(DomainHandle domainHandle, SAMPRDomainModifiedInformation2 domainInformation) {
            super(domainHandle, domainInformation);
        }
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_MODIFIED_INFORMATION2;
        }
    }
}
