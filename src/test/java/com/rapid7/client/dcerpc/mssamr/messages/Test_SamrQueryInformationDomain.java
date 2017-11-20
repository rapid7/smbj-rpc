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
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLockoutInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLogOffInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainPasswordInfo;

public class Test_SamrQueryInformationDomain {

    @Test
    public void SamrQueryPasswordInformationDomain() throws IOException {
        String hexString = "000002000100000000000000000000000000000040deffff000000000000000000000000";
        SamrQueryInformationDomainResponse<SAMPRDomainPasswordInfo> response =
                new SamrQueryInformationDomainResponse.DomainPasswordInformation();
        response.unmarshal(getPacketInput(hexString));
        SAMPRDomainPasswordInfo passInfo = response.getDomainInformation();

        assertEquals(-37108517437440L, passInfo.getMaximumPasswordAge());
        assertEquals(0, passInfo.getMinimumPasswordAge());
        assertEquals(0, passInfo.getPasswordProperties());
        assertEquals(0, passInfo.getMinimumPasswordLength());
        assertEquals(0, passInfo.getPasswordHistoryLength());
    }

    @Test
    public void SamrQueryLogOffInformationDomain() throws IOException {
        String hexString = "0000020003000000000000000000008000000000";
        SamrQueryInformationDomainResponse<SAMPRDomainLogOffInfo>
                response = new SamrQueryInformationDomainResponse.DomainLogOffInformation();
        response.unmarshal(getPacketInput(hexString));
        SAMPRDomainLogOffInfo logOffInfo = response.getDomainInformation();

        // -9223372036854775808(never expire)
        assertEquals(-9223372036854775808L, logOffInfo.getForceLogoff());
    }

    @Test
    public void SamrQueryLockoutInformationDomain() throws IOException {
        String hexString = "000002000c00000000cc1dcffbffffff00cc1dcffbffffff0000";
        SamrQueryInformationDomainResponse<SAMPRDomainLockoutInfo> response =
                new SamrQueryInformationDomainResponse.DomainLockoutInformation();
        response.unmarshal(getPacketInput(hexString));
        SAMPRDomainLockoutInfo lockout = response.getDomainInformation();

        // 1800 seconds
        assertEquals(-18000000000L, lockout.getLockoutDuration());
        assertEquals(-18000000000L, lockout.getLockoutObservationWindow());
        assertEquals(0, lockout.getLockoutThreshold());
    }

    private PacketInput getPacketInput(final String hexString) {
        final byte[] inputStreamBytes = Hex.decode(hexString);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
        return new PacketInput(inputStream);
    }
}
