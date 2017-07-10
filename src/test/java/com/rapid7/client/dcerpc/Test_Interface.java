package com.rapid7.client.dcerpc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class Test_Interface {
    @Test
    public void winRegV1() {
        final byte[] uuidBytes = Hex.decode("01d08c334422f131aaaa900038001003");
        assertArrayEquals(uuidBytes, Interface.WINREG_V1_0.getUUID());
        assertEquals(1, Interface.WINREG_V1_0.getMajorVersion());
        assertEquals(0, Interface.WINREG_V1_0.getMinorVersion());
    }

    @Test
    public void ndr32bitV2() {
        final byte[] uuidBytes = Hex.decode("045d888aeb1cc9119fe808002b104860");
        assertArrayEquals(uuidBytes, Interface.NDR_32BIT_V2.getUUID());
        assertEquals(2, Interface.NDR_32BIT_V2.getMajorVersion());
        assertEquals(0, Interface.NDR_32BIT_V2.getMinorVersion());
    }
}
