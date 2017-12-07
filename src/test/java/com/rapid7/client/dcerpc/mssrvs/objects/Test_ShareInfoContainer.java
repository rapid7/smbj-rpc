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
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.io.PacketInput;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class Test_ShareInfoContainer {

    @DataProvider
    public Object[][] data_allInstances() {
        return new Object[][] {
                {new ShareInfoContainer.ShareInfo0Container()},
                {new ShareInfoContainer.ShareInfo1Container()},
                {new ShareInfoContainer.ShareInfo2Container()},
                {new ShareInfoContainer.ShareInfo501Container()},
                {new ShareInfoContainer.ShareInfo502Container()},
                {new ShareInfoContainer.ShareInfo503Container()},
        };
    }

    @Test(dataProvider = "data_allInstances")
    public void test_unmarshalPreamble(ShareInfoContainer container) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);
        container.unmarshalPreamble(in);
        assertEquals(bin.available(), 0);
        assertNull(container.getBuffer());
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                {"05000000 02000000", new ShareInfoContainer.ShareInfo0Container(), new ShareInfo0[5]},
                {"05000000 00000000", new ShareInfoContainer.ShareInfo0Container(), null},
                {"05000000 02000000", new ShareInfoContainer.ShareInfo1Container(), new ShareInfo1[5]},
                {"05000000 00000000", new ShareInfoContainer.ShareInfo1Container(), null},
                {"05000000 02000000", new ShareInfoContainer.ShareInfo2Container(), new ShareInfo2[5]},
                {"05000000 00000000", new ShareInfoContainer.ShareInfo2Container(), null},
                {"05000000 02000000", new ShareInfoContainer.ShareInfo501Container(), new ShareInfo501[5]},
                {"05000000 00000000", new ShareInfoContainer.ShareInfo501Container(), null},
                {"05000000 02000000", new ShareInfoContainer.ShareInfo502Container(), new ShareInfo502[5]},
                {"05000000 00000000", new ShareInfoContainer.ShareInfo502Container(), null},
                {"05000000 02000000", new ShareInfoContainer.ShareInfo503Container(), new ShareInfo503[5]},
                {"05000000 00000000", new ShareInfoContainer.ShareInfo503Container(), null},
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, ShareInfoContainer container, ShareInfo[] expectBuffer) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        container.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        assertEquals(container.getBuffer(), expectBuffer);
    }
}
