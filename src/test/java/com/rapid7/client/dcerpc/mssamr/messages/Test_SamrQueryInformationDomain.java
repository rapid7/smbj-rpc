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
package com.rapid7.client.dcerpc.mssamr.messages;

import static org.testng.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.mslsad.objects.DomainInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLogOffInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainPasswordInfo;

public class Test_SamrQueryInformationDomain {

    @Test
    public void SamrQueryPasswordInformationDomain() throws IOException {
        String hexString = "000002000100000000000000000000000000000040deffff000000000000000000000000";
        SAMPRDomainPasswordInfo passInfo = new SAMPRDomainPasswordInfo();
        SamrQueryInformationDomainResponse<SAMPRDomainPasswordInfo> response = new SamrQueryInformationDomainResponse<>(
            passInfo, DomainInformationClass.DOMAIN_PASSWORD_INFORMATION);
        response.unmarshal(getPacketInput(hexString));

        assertEquals(-37108517437440L, passInfo.getMaximumPasswordAge());
        assertEquals(0, passInfo.getMinimumPasswordAge());
        assertEquals(0, passInfo.getPasswordProperties());
        assertEquals(0, passInfo.getMinimumPasswordLength());
        assertEquals(0, passInfo.getPasswordHistoryLength());
    }

    @Test
    public void SamrQueryLogOffInformationDomain() throws IOException {
        String hexString = "0000020003000000000000000000008000000000";
        SAMPRDomainLogOffInfo logOffInfo = new SAMPRDomainLogOffInfo();
        SamrQueryInformationDomainResponse<SAMPRDomainLogOffInfo> response = new SamrQueryInformationDomainResponse<>(
            logOffInfo, DomainInformationClass.DOMAIN_LOGOFF_INFORMATION);
        response.unmarshal(getPacketInput(hexString));

        // -9223372036854775808(never expire)
        assertEquals(-9223372036854775808L, logOffInfo.getForceLogoff());
    }


    private PacketInput getPacketInput(final String hexString) {
        final byte[] inputStreamBytes = Hex.decode(hexString);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
        return new PacketInput(inputStream);
    }
}
