/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.messages;

import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;

/**
 * 
 */
public class SamrConnect2Response extends HandleResponse {

    @Override
    public ServerHandle getHandle() {
        ServerHandle handle = new ServerHandle();
        handle.setBytes(super.handle.getBytes());
        return handle;
    }
    /////////////////////////////////////////////////////////////////////////
    // Public methods
    /////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////
    // Non-public methods
    /////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////
    // Non-public fields
    /////////////////////////////////////////////////////////////////////////

}
