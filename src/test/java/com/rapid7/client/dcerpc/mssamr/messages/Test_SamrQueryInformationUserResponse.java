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

package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.UnmarshalException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.mssamr.objects.UserInformationClass;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_SamrQueryInformationUserResponse {

    @DataProvider
    public Object[][] data_getters() {
        return new Object[][] {
                {new SamrQueryInformationUserResponse.UserAllInformation(), UserInformationClass.USER_ALL_INFORMATION}
        };
    }

    @Test(dataProvider = "data_getters")
    public void test_getters(SamrQueryInformationUserResponse response, UserInformationClass expectedUserInformationClass) {
        assertNull(response.getUserInformation());
        assertSame(response.getUserInformationClass(), expectedUserInformationClass);
    }

    @DataProvider
    public Object[][] data_unmarshal() {
        return new Object[][] {
                // Reference: 1, USER_INFORMATION_CLASS: 21
                {new SamrQueryInformationUserResponse.UserAllInformation(), "01000000 1500 FFFF 01000000"}
        };
    }

    @Test(dataProvider = "data_unmarshal")
    public void test_unmarshal(SamrQueryInformationUserResponse response, String hex) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = spy(new PacketInput(bin));
        doReturn(null).when(in).readUnmarshallable(any(Unmarshallable.class));
        response.unmarshal(in);
        assertEquals(bin.available(), 0);
        assertNotNull(response.getUserInformation());
        assertEquals(response.getReturnValue(), 1);
    }

    @DataProvider
    public Object[][] data_unmarshall_Null() {
        return new Object[][] {
                {new SamrQueryInformationUserResponse.UserAllInformation(), "00000000 01000000"}
        };
    }

    @Test(dataProvider = "data_unmarshall_Null")
    public void test_unmarshall_Null(SamrQueryInformationUserResponse response, String hex) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = spy(new PacketInput(bin));
        response.unmarshal(in);
        assertEquals(bin.available(), 0);
        assertNull(response.getUserInformation());
        assertEquals(response.getReturnValue(), 1);
    }

    @DataProvider
    public Object[][] data_unmarshal_InvalidTag() {
        return new Object[][] {
                // Reference: 1, POLICY_CLASS_INFORMATION: 3
                {new SamrQueryInformationUserResponse.UserAllInformation(), "01000000 FFFF"},
        };
    }

    @Test(dataProvider = "data_unmarshal_InvalidTag",
            expectedExceptions = {UnmarshalException.class},
            expectedExceptionsMessageRegExp = "Incoming USER_INFORMATION_CLASS 65535 does not match expected: [0-9]+")
    public void test_unmarshal_InvalidTag(SamrQueryInformationUserResponse response, String hex) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = spy(new PacketInput(bin));
        response.unmarshal(in);
        assertEquals(bin.available(), 0);
    }
}
