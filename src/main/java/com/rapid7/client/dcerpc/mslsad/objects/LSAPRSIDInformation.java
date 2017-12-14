/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.mslsad.objects;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.objects.RPCSID;
import java.io.IOException;

/**
 * Documentation from https://msdn.microsoft.com/en-us/library/cc234459.aspx
 *
 * <h1 class="title">2.2.17 LSAPR_SID_INFORMATION</h1>
 *
 * <p>The LSAPR_SID_INFORMATION structure contains a PRPC_SID
 * value.</p>
 *
 * <dl>
 * <dd>
 * <div><pre> typedef struct _LSAPR_SID_INFORMATION {
 *    PRPC_SID Sid;
 *  } LSAPR_SID_INFORMATION,
 *   *PLSAPR_SID_INFORMATION;
 * </pre></div>
 * </dd></dl>
 *
 * <p><strong>Sid:</strong>  Contains the PRPC_SID value,
 * as specified in <a href="https://msdn.microsoft.com/en-us/library/cc230273.aspx">[MS-DTYP]</a>
 * section <a href="https://msdn.microsoft.com/en-us/library/cc230364.aspx">2.4.2.3</a>.
 * This field MUST be non-NULL.</p>
 */

public class LSAPRSIDInformation implements Marshallable
{
    private RPCSID sid;

    public LSAPRSIDInformation(RPCSID sid) {
        if (sid == null)
            throw new IllegalArgumentException("sid must not be null");
        this.sid = sid;
    }

    @Override public void marshalPreamble(PacketOutput out) throws IOException {

    }

    @Override public void marshalEntity(PacketOutput out) throws IOException {
        out.writeMarshallable(sid);
    }

    @Override public void marshalDeferrals(PacketOutput out) throws IOException {
        // No deferrals
    }
}
