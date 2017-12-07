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

package com.rapid7.client.dcerpc.mssrvs.objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.rmi.UnmarshalException;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.bouncycastle.util.encoders.Hex;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class Test_ShareEnumStruct {

    @DataProvider
    public Object[][] data_allInstances() {
        return new Object[][] {
                // ShareEnumStruct0
                {new ShareEnumStruct.ShareEnumStruct0()},
                {new ShareEnumStruct.ShareEnumStruct1()},
                {new ShareEnumStruct.ShareEnumStruct2()},
                {new ShareEnumStruct.ShareEnumStruct501()},
                {new ShareEnumStruct.ShareEnumStruct502()},
                {new ShareEnumStruct.ShareEnumStruct503()},
        };
    }

    @Test(dataProvider = "data_allInstances")
    public void test_unmarshalPreamble(ShareEnumStruct struct) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);
        struct.unmarshalPreamble(in);
        assertEquals(bin.available(), 0);
        assertNull(struct.getShareInfoContainer());
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // ShareEnumStruct0
                {"00000000 00000000 02000000", new ShareEnumStruct.ShareEnumStruct0(), false},
                {"00000000 00000000 00000000", new ShareEnumStruct.ShareEnumStruct0(), true},
                // ShareEnumStruct1
                {"01000000 01000000 02000000", new ShareEnumStruct.ShareEnumStruct1(), false},
                {"01000000 01000000 00000000", new ShareEnumStruct.ShareEnumStruct1(), true},
                // ShareEnumStruct2
                {"02000000 02000000 02000000", new ShareEnumStruct.ShareEnumStruct2(), false},
                {"02000000 02000000 00000000", new ShareEnumStruct.ShareEnumStruct2(), true},
                // ShareEnumStruct501
                {"F5010000 F5010000 02000000", new ShareEnumStruct.ShareEnumStruct501(), false},
                {"F5010000 F5010000 00000000", new ShareEnumStruct.ShareEnumStruct501(), true},
                // ShareEnumStruct502
                {"F6010000 F6010000 02000000", new ShareEnumStruct.ShareEnumStruct502(), false},
                {"F6010000 F6010000 00000000", new ShareEnumStruct.ShareEnumStruct502(), true},
                // ShareEnumStruct503
                {"F7010000 F7010000 02000000", new ShareEnumStruct.ShareEnumStruct503(), false},
                {"F7010000 F7010000 00000000", new ShareEnumStruct.ShareEnumStruct503(), true},
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, ShareEnumStruct struct, boolean expectNullContainer) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        struct.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        if (expectNullContainer)
            assertNull(struct.getShareInfoContainer());
        else
            assertNotNull(struct.getShareInfoContainer());
    }

    @Test(dataProvider = "data_allInstances",
            expectedExceptions = {UnmarshalException.class},
            expectedExceptionsMessageRegExp = "Expected info level [0-9]+, got: 2139062143")
    public void test_unmarshalEntity_levelMismatch1(ShareEnumStruct struct) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[]{127, 127, 127, 127});
        PacketInput in = new PacketInput(bin);
        struct.unmarshalEntity(in);
    }

    @Test(dataProvider = "data_allInstances",
            expectedExceptions = {UnmarshalException.class},
            expectedExceptionsMessageRegExp = "Expected info level [0-9]+ to match enum level, got: 2139062143")
    public void test_unmarshalEntity_levelMismatch2(ShareEnumStruct struct) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(8);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putInt(struct.getLevel().getInfoLevel());
        buff.putInt(2139062143);
        ByteArrayInputStream bin = new ByteArrayInputStream(buff.array());
        PacketInput in = new PacketInput(bin);
        struct.unmarshalEntity(in);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                {mock(ShareInfoContainer.class), true},
                {null, false}
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(ShareInfoContainer container, boolean expectUnmarshall) throws IOException {
        final MutableBoolean unmarshallCalled = new MutableBoolean(false);
        PacketInput in = mock(PacketInput.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) {
                unmarshallCalled.setTrue();
                return null;
            }
        }).when(in).readUnmarshallable(container);

        ShareEnumStruct struct = mock(ShareEnumStruct.class);
        when(struct.getShareInfoContainer()).thenReturn(container);
        doCallRealMethod().when(struct).unmarshalDeferrals(in);
        struct.unmarshalDeferrals(in);
        assertEquals(unmarshallCalled.booleanValue(), expectUnmarshall);
    }
}
