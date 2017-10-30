/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.messages;

import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mssamr.objects.UserHandle;

/**
 *
 */
public class SamrOpenUserResponse extends HandleResponse {

    @Override
    public UserHandle getHandle() {
	UserHandle handle = new UserHandle();
        handle.setBytes(super.handle.getBytes());
        return handle;
    }

}
