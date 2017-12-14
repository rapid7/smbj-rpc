/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msvcctl.messages;

import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.msvcctl.ServiceControlManagerService;
import com.rapid7.client.dcerpc.objects.WChar;

import static org.testng.Assert.assertEquals;

public class Test_ROpenService {

    @Test
    public void encodeROpenServiceRequest() throws IOException {
        byte[] dummyHandle = Hex.decode("00000000905b8d8804ce08479cb8f06082448314");
        final ROpenServiceWRequest request = new ROpenServiceWRequest(dummyHandle, WChar.NullTerminated.of("Remoteregistry"), ServiceControlManagerService.FULL_ACCESS);
        assertEquals(request.toHexString(), "00000000905b8d8804ce08479cb8f060824483140f000000000000000f000000520065006d006f007400650072006500670069007300740072007900000000003f000f00");
    }
}
