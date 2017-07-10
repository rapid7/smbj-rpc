package com.rapid7.client.dcerpc;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Test_PFCFlag {
    @Test
    public void first_fragment() {
        assertEquals(0x01, PFCFlag.FIRST_FRAGMENT.getValue());
    }

    @Test
    public void last_fragment() {
        assertEquals(0x02, PFCFlag.LAST_FRAGMENT.getValue());
    }

    @Test
    public void pending_cancel() {
        assertEquals(0x04, PFCFlag.PENDING_CANCEL.getValue());
    }

    @Test
    public void concurrent_multiplexing() {
        assertEquals(0x10, PFCFlag.CONCURRENT_MULTIPLEXING.getValue());
    }

    @Test
    public void did_not_execute() {
        assertEquals(0x20, PFCFlag.DID_NOT_EXECUTE.getValue());
    }

    @Test
    public void maybe() {
        assertEquals(0x40, PFCFlag.MAYBE.getValue());
    }

    @Test
    public void object_uuid() {
        assertEquals(0x80, PFCFlag.OBJECT_UUID.getValue());
    }
}
