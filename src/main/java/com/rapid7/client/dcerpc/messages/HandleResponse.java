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
package com.rapid7.client.dcerpc.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.objects.ContextHandle;

/**
 * <b>Example:</b>
 *
 * <pre>
 * Remote Registry Service, OpenHKLM
 *     Operation: OpenHKLM (2)
 *     [Request in frame: 11174]
 *     Pointer to Handle (policy_handle)
 *         Policy Handle: OpenHKLM(&lt;...&gt;)
 *             Handle: 0000000032daf234b77c86409d29efe60d326683
 *             [Frame handle opened: 11176]
 *             [Frame handle closed: 11424]
 *     Windows Error: WERR_OK (0x00000000)
 * </pre>
 */
public class HandleResponse<T extends ContextHandle> extends RequestResponse {
    private final T handle = initHandle();
    private int returnValue;

    /**
     * @return The handle to a opened key.
     */
    public T getHandle() {
        return handle;
    }

    /**
     * @return The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise,
     * it returns a nonzero error code, as
     * specified in [MS-ERREF] section 2.2.
     */
    public int getReturnValue() {
        return returnValue;
    }

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
        // Remote Registry Service, OpenHKLM
        // Operation: OpenHKLM (2)
        // [Request in frame: 11174]
        // Pointer to Handle (policy_handle)
        // Policy Handle: OpenHKLM(<...>)
        // Handle: 0000000032daf234b77c86409d29efe60d326683
        // [Frame handle opened: 11176]
        // [Frame handle closed: 11424]
        // Windows Error: WERR_OK (0x00000000)
        packetIn.readUnmarshallable(handle);
        returnValue = packetIn.readInt();
    }

    /**
     * Instantiate the context handle.
     * Can be overriden to return a context handle of a specific type.
     *
     * @return The context handle.
     */
    protected T initHandle() {
        return (T) new ContextHandle();
    }
}
