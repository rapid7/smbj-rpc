package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.EmptyResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;

import java.io.IOException;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245926.aspx">RDeleteService</a>
 * <blockquote><pre>The RDeleteService method marks the specified service for deletion from the SCM database.
 *
 *      DWORD RDeleteService(
 *          [in] SC_RPC_HANDLE hService
 *      );
 *
 * hService: An SC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the service record that MUST have been created previously, using one of the open methods specified in section 3.1.4. The DELETE access right MUST have been granted to the caller when the RPC context handle to the service record was created.</pre></blockquote>
 */
public class RDeleteServiceWRequest extends RequestCall<EmptyResponse> {
    public final static short OP_NUM = 2;
    // <NDR: fixed array> [in] SC_RPC_HANDLE hService
    private final byte[] hService;

    public RDeleteServiceWRequest(final byte[] hService) {
        super(OP_NUM);
        this.hService = hService;
    }

    @Override
    public EmptyResponse getResponseObject() {
        return new EmptyResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SC_RPC_HANDLE hService
        packetOut.write(this.hService);
    }
}
