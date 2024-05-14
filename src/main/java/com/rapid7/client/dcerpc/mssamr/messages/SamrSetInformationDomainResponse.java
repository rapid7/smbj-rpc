package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

/**
 * SamrSetInformationDomain
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/9b7ae0b0-bd11-4133-9c62-fba7095aee12">SamrSetInformationDomain  (Opnum 9)</a> 
 * */

public class SamrSetInformationDomainResponse extends RequestResponse {

	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException { 
		//STATUS_ERROR_CODE
	 }
	
}
