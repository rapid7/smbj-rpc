package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

/**
 * SamrSetInformationUser
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/538222f7-1b89-4811-949a-0eac62e38dce">SamrSetInformationUser (Opnum 37)</a>
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/99ee9f39-43e8-4bba-ac3a-82e0c0e0699e">SamrSetInformationUser2 (Opnum 58)</a>
 */
public  class SamrSetInformationUserResponse extends RequestResponse {


	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException { 
		//STATUS_ERROR_CODE
	 }

	
}
