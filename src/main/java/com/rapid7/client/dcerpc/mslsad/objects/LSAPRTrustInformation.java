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

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import java.io.IOException;

/**

 */
public class LSAPRTrustInformation implements Unmarshallable
{
    RPCUnicodeString.NullTerminated name;
    RPCSID sid;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        name = new RPCUnicodeString.NullTerminated();
        name.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        name.unmarshalEntity(in);
        in.align(Alignment.FOUR);

        if (in.readReferentID() != 0){
            sid = new RPCSID();
        } else sid = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        name.unmarshalDeferrals(in);
        if (sid != null){
            in.readUnmarshallable(sid);
        }
    }
}
