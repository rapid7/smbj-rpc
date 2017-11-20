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

import java.io.EOFException;
import java.io.IOException;
import com.rapid7.client.dcerpc.PDUFault;
import com.rapid7.client.dcerpc.io.PacketInput;

/**
 * This class represents both PDUType.FAULT and PDUType.REJECT. PDUType.REJECT is unexpected in connection-oriented
 * calls but maps to the same 32bit fields, so we can catch it with this exception as well.
 */
public class RPCFaultException extends IOException {

    public static RPCFaultException read(PacketInput in) throws IOException {
        int rpcFaultValue;
        try {
            rpcFaultValue = in.readInt();
        } catch (EOFException e) {
            rpcFaultValue = -1;
        }
        return new RPCFaultException(rpcFaultValue);
    }

    private final int rpcFaultValue;
    private final PDUFault rpcFault;

    public RPCFaultException(final int rpcFaultValue) {
        this.rpcFaultValue = rpcFaultValue;
        this.rpcFault = PDUFault.fromValue(this.rpcFaultValue);
    }

    public int getRpcFaultValue() {
        return rpcFaultValue;
    }

    public PDUFault getRpcFault() {
        return rpcFault;
    }

    @Override
    public String getMessage() {
        return String.format("Fault: %s (0x%08X)", getRpcFault(), getRpcFaultValue());
    }
}
