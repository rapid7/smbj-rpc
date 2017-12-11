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

import java.io.EOFException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.Hexify;
import com.rapid7.client.dcerpc.io.HexifyImpl;
import com.rapid7.client.dcerpc.io.Packet;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;

public abstract class RequestResponse extends HexifyImpl implements Packet, Hexify {

    private int returnValue;

    /**
     * @return The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code, as
     * specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF]. The most common
     * error codes are listed in the following table.<br>
     * <br>
     * <table border="1" summary="">
     * <tr>
     * <td>Return value/code</td>
     * <td>Description</td>
     * <tr>
     * <tr>
     * <td>ERROR_ACCESS_DENIED (0x00000005)</td>
     * <td>The caller does not have KEY_ENUMERATE_SUB_KEYS access rights.</td>
     * </tr>
     * <tr>
     * <td>ERROR_OUTOFMEMORY (0x0000000E)</td>
     * <td>Not enough storage is available to complete this operation.</td>
     * </tr>
     * <tr>
     * <td>ERROR_INVALID_PARAMETER (0x00000057)</td>
     * <td>A parameter is incorrect.</td>
     * </tr>
     * <tr>
     * <td>ERROR_NO_MORE_ITEMS (0x00000103)</td>
     * <td>No more data is available.</td>
     * </tr>
     * <tr>
     * <td>ERROR_WRITE_PROTECT (0x00000013)</td>
     * <td>A read or write operation was attempted to a volume after it was dismounted. The server can no longer
     * service registry requests because server shutdown has been initiated.</td>
     * </tr>
     * <tr>
     * <td>ERROR_MORE_DATA (0x000000EA)</td>
     * <td>The size of the buffer is not large enough to hold the requested data.</td>
     * </tr>
     * </table>
     */
    public int getReturnValue() {
        return returnValue;
    }

    public SystemErrorCode getReturnCode() {
        return SystemErrorCode.getErrorCode(getReturnValue());
    }

    public boolean isSuccess() {
        return getReturnCode() == SystemErrorCode.ERROR_SUCCESS;
    }

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
        unmarshalResponse(packetIn);
        // <NDR: unsigned long> NTSTATUS
        packetIn.align(Alignment.FOUR);
        this.returnValue = packetIn.readInt();
        // Ensure EOF
        try {
            packetIn.readByte();
        } catch (final EOFException e) {
            return;
        }
        throw new UnmarshalException("At least one byte remained after reading the return code. Is this response aligned properly?");
    }

    public abstract void unmarshalResponse(PacketInput packetIn) throws IOException;

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        throw new UnsupportedOperationException("Marshal Not Implemented.");
    }
}
