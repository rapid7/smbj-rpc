/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateDomainsInSamServerRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateDomainsInSamServerResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DomainInfo;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class Test_SecurityAccountManagerService {

    @Test
    public void getDomainsForServer() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        ServerHandle handle = new ServerHandle();
        SamrEnumerateDomainsInSamServerResponse response = Mockito.mock(SamrEnumerateDomainsInSamServerResponse.class);
        Mockito.when(response.getReturnValue()).thenReturn(SystemErrorCode.ERROR_SUCCESS.ordinal());
        List<DomainInfo> domains = new ArrayList<>();
        domains.add(Mockito.mock(DomainInfo.class));
        domains.add(Mockito.mock(DomainInfo.class));
        Mockito.when(response.getDomainList()).thenReturn(domains);
        Mockito.when(transport.call(Mockito.any(SamrEnumerateDomainsInSamServerRequest.class))).thenReturn(response);
        assertEquals(2, service.getDomainsForServer(handle).size());
    }

    @Test
    public void getDomainsForServerMoreEntries() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        ServerHandle handle = new ServerHandle();
        SamrEnumerateDomainsInSamServerResponse response1 = Mockito.mock(SamrEnumerateDomainsInSamServerResponse.class);
        SamrEnumerateDomainsInSamServerResponse response2 = Mockito.mock(SamrEnumerateDomainsInSamServerResponse.class);
        Mockito.when(response1.getReturnValue()).thenReturn(SystemErrorCode.ERROR_MORE_ENTRIES.getErrorCode());
        Mockito.when(response1.getResumeHandle()).thenReturn(1);
        Mockito.when(response2.getReturnValue()).thenReturn(SystemErrorCode.ERROR_SUCCESS.getErrorCode());
        Mockito.when(response2.getResumeHandle()).thenReturn(2);
        List<DomainInfo> domains1 = new ArrayList<>();
        List<DomainInfo> domains2 = new ArrayList<>();
        domains1.add(Mockito.mock(DomainInfo.class));
        domains2.add(Mockito.mock(DomainInfo.class));
        Mockito.when(response1.getDomainList()).thenReturn(domains1);
        Mockito.when(response2.getDomainList()).thenReturn(domains2);
        Mockito.when(transport.call(Mockito.any(SamrEnumerateDomainsInSamServerRequest.class))).thenReturn(response1)
            .thenReturn(response2);
        assertEquals(2, service.getDomainsForServer(handle).size());
    }
}
