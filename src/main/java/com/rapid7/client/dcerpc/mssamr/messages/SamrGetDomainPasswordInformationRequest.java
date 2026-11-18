/* Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. */
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/1a884148-7e90-4a93-b4c4-7a0006f59026">SamrGetDomainPasswordInformation</a>
 * <blockquote><pre>The SamrGetDomainPasswordInformation method obtains select password policy information (without authenticating to the server).
 *
 * long SamrGetDomainPasswordInformation(
 *    [in] handle_t BindingHandle,
 *    [in, unique] PRPC_UNICODE_STRING Unused,
 *    [out] PUSER_DOMAIN_PASSWORD_INFORMATION PasswordInformation
 *    );
 *
 *  BindingHandle: An RPC binding handle parameter, as specified in [C706] section 1.
 *  Unused: A string value that is unused by the protocol. It is ignored by the server. The client MAY<62> set any value.
 *  PasswordInformation: Password policy information from the account domain.
 *  There is no security enforced for this method.
 *  Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *  	1. The output parameter PasswordInformation.MinPasswordLength MUST be set to the minPwdLength attribute value on the account domain object.
 *  	2. The output parameter PasswordInformation.PasswordProperties MUST be set to the pwdProperties attribute value on the account domain object.
 *  	3. The method MUST return STATUS_SUCCESS.
 *  </pre></blockquote>
 *
 */
public class SamrGetDomainPasswordInformationRequest extends RequestCall<SamrGetDomainPasswordInformationResponse> {
	public static final short OP_NUM = 56;
	// <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
	private final byte[] domainHandle;
	private final RPCUnicodeString.NonNullTerminated unused;

	public SamrGetDomainPasswordInformationRequest(final byte[] domainHandle) {
		super(OP_NUM);
		this.domainHandle = domainHandle;
		unused = RPCUnicodeString.NonNullTerminated.of("");
	}
	public byte[] getDomainHandle() { return domainHandle; }
	@Override
	public SamrGetDomainPasswordInformationResponse getResponseObject() { return new SamrGetDomainPasswordInformationResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// [in] SAMPR_HANDLE DomainHandle
		// Alignment: 1 - Already aligned
		packetOut.write(this.domainHandle);
		unused.marshalPreamble(packetOut);
		unused.marshalEntity(packetOut);
		unused.marshalDeferrals(packetOut);
	}
}
