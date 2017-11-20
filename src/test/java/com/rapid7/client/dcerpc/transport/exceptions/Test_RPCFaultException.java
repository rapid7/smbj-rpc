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

package com.rapid7.client.dcerpc.transport.exceptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.PDUFault;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class Test_RPCFaultException {

    @DataProvider
    public Object[][] data_read_known() {
        PDUFault[] faults = PDUFault.values();

        Object[][] ret = new Object[faults.length][];
        for (int i = 0; i < faults.length; i++) {
            ret[i] = new Object[]{faults[i]};
        }
        return ret;
    }

    @Test(dataProvider = "data_read_known")
    public void test_read_known(PDUFault fault) throws IOException {
        // Set up the buffer
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        out.writeInt(fault.getValue());
        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        PacketInput in = new PacketInput(bin);
        // Test
        RPCFaultException ex = RPCFaultException.read(in);
        assertSame(ex.getRpcFault(), fault);
        assertEquals(ex.getRpcFaultValue(), fault.getValue());
        assertTrue(ex.getMessage().startsWith("Fault: " + fault.toString()));
        assertEquals(bin.available(), 0);
    }

    @DataProvider
    public Object[][] data_read_unknown() {
        return new Object[][] {
                // Actually UKNOWN
                {"00000000", 0},
                // Unknown number
                {"09000000", 9},
                // Insufficient bytes
                {"", -1},
                {"00", -1},
        };
    }

    @Test(dataProvider = "data_read_unknown")
    public void test_read_unknown(String hex, int expectValue) throws IOException {
        // Set up the buffer
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        // Test
        RPCFaultException ex = RPCFaultException.read(in);
        assertSame(ex.getRpcFault(), PDUFault.UNKNOWN);
        assertEquals(ex.getRpcFaultValue(), expectValue);
        assertEquals(bin.available(), 0);
    }
}
