package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

/**
 * SamrSetInformationGroup
 * 
 * @see <a https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/e66db19f-600a-481b-bc4e-23953433255d">SamrSetInformationGroup (Opnum 21)</a>
 */
public  class SamrSetInformationGroupResponse extends RequestResponse {


	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException { 
		//STATUS_ERROR_CODE
	 }

	
}
