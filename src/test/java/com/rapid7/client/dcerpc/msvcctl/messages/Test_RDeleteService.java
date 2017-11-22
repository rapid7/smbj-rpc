package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.objects.ContextHandle;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class Test_RDeleteService {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void encodeDeleteServiceRequest() throws IOException {
        ContextHandle handle = new ContextHandle("000000001CCD2628477770489D015EEE8CCFFB01");
        RDeleteServiceRequest request = new RDeleteServiceRequest(handle);
        assertEquals(request.toHexString(), "000000001ccd2628477770489d015eee8ccffb01");
    }
}
