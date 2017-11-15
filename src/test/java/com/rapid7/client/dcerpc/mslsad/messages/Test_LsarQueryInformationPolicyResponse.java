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
package com.rapid7.client.dcerpc.mslsad.messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.UnmarshalException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.mslsad.objects.PolicyInformationClass;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class Test_LsarQueryInformationPolicyResponse {

    @Test
    public void test_getters() {
        Unmarshallable obj = mock(Unmarshallable.class);
        LsarQueryInformationPolicyResponse<Unmarshallable> response = new LsarQueryInformationPolicyResponse<>(obj, PolicyInformationClass.POLICY_ACCOUNT_DOMAIN_INFORMATION);
        assertSame(response.getPolicyInformation(), obj);
    }

    @DataProvider
    public Object[][] data_unmarshal() {
        return new Object[][] {
                // Reference: 1, POLICY_CLASS_INFORMATION: 3
                {"01000000 0300"},
        };
    }

    @Test(dataProvider = "data_unmarshal")
    public void test_unmarshal(String hex) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        Unmarshallable unmarshallable = mock(Unmarshallable.class);
        doNothing().when(unmarshallable).unmarshalPreamble(in);
        doNothing().when(unmarshallable).unmarshalEntity(in);
        doNothing().when(unmarshallable).unmarshalDeferrals(in);
        LsarQueryInformationPolicyResponse<Unmarshallable> response = new LsarQueryInformationPolicyResponse<>(
                unmarshallable,
                PolicyInformationClass.POLICY_PRIMARY_DOMAIN_INFORMATION);
        response.unmarshal(in);
        assertEquals(bin.available(), 0);
    }

    @Test
    public void test_unmarshal_NullPointer() throws IOException {
        // Reference: 0
        String hex = "00000000";
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        LsarQueryInformationPolicyResponse<Unmarshallable> response = new LsarQueryInformationPolicyResponse<>(
                null,
                PolicyInformationClass.POLICY_ACCOUNT_DOMAIN_INFORMATION);
        response.unmarshal(in);
        assertEquals(bin.available(), 0);
    }

    @Test
    public void test_unmarshal_InvalidTag() throws IOException {
        // Reference: 1, POLICY_CLASS_INFORMATION: 3
        String hex = "01000000 0300";
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        LsarQueryInformationPolicyResponse<Unmarshallable> response = new LsarQueryInformationPolicyResponse<>(
                null,
                PolicyInformationClass.POLICY_ACCOUNT_DOMAIN_INFORMATION);
        UnmarshalException actual = null;
        try {
            response.unmarshal(in);
        } catch (UnmarshalException e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(actual.getMessage(), "Incoming POLICY_INFORMATION_CLASS 3 does not match expected: 5");
        assertEquals(bin.available(), 0);
    }
}
