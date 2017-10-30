/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.objects;

import com.rapid7.client.dcerpc.objects.ContextHandle;

/**
 * A typed RPC context handle for domains.
 *
 * @see <a href= "https://msdn.microsoft.com/en-us/library/cc245544.aspx">SAMPR_HANDLE</a>
 */
public class DomainHandle extends ContextHandle {
    /////////////////////////////////////////////////////////////////////////
    // Public methods
    /////////////////////////////////////////////////////////////////////////

    public DomainHandle() {
        super();
    }
}
