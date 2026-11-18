package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

/**
 * SamrSetInformationGroup
 *
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/ba787e4e-3a4b-47a2-aca3-c3ac2d2c511e">SamrSetInformationAlias (Opnum 29)</a>
 */
public class SamrSetInformationAliasResponse extends RequestResponse {
	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		//STATUS_ERROR_CODE
	}
}
