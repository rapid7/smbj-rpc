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

package com.rapid7.client.dcerpc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class Test_RPCFault {

    @Test(dataProvider = "data_pairs")
    public void test_fromValue(PDUFault expectFault, long value) {
        assertEquals(PDUFault.fromValue((int) value), expectFault);
    }

    @Test(dataProvider = "data_pairs")
    public void test_getValue(PDUFault fault, long expectValue) {
        assertEquals(fault.getValue(), (int) expectValue);
    }

    @Test(dataProvider = "data_pairs")
    public void test_toString(PDUFault fault, long value) {
        assertEquals(fault.toString(), fault.name().toLowerCase());
    }

    @DataProvider
    public Object[][] data_pairs() {
        return new Object[][] {
                {PDUFault.UNKNOWN, 0},
                {PDUFault.NCA_S_FAULT_OTHER, 1},
                {PDUFault.NCA_S_FAULT_ACCESS_DENIED, 5},
                {PDUFault.NCA_S_FAULT_NDR, 1783},
                {PDUFault.NCA_S_FAULT_CANT_PERFORM, 1752},
                {PDUFault.NCA_S_FAULT_INT_DIV_BY_ZERO, 469762049},
                {PDUFault.NCA_S_FAULT_ADDR_ERROR, 469762050},
                {PDUFault.NCA_S_FAULT_FP_DIV_ZERO, 469762051},
                {PDUFault.NCA_S_FAULT_FP_UNDERFLOW, 469762052},
                {PDUFault.NCA_S_FAULT_FP_OVERFLOW, 469762053},
                {PDUFault.NCA_S_FAULT_INVALID_TAG, 469762054},
                {PDUFault.NCA_S_FAULT_INVALID_BOUND, 469762055},
                {PDUFault.NCA_RPC_VERSION_MISMATCH, 469762056},
                {PDUFault.NCA_UNSPEC_REJECT, 469762057},
                {PDUFault.NCA_S_BAD_ACTID, 469762058},
                {PDUFault.NCA_WHO_ARE_YOU_FAILED, 469762059},
                {PDUFault.NCA_MANAGER_NOT_ENTERED, 469762060},
                {PDUFault.NCA_S_FAULT_CANCEL, 469762061},
                {PDUFault.NCA_S_FAULT_ILL_INST, 469762062},
                {PDUFault.NCA_S_FAULT_FP_ERROR, 469762063},
                {PDUFault.NCA_S_FAULT_INT_OVERFLOW, 469762064},
                {PDUFault.NCA_S_FAULT_PIPE_EMPTY, 469762068},
                {PDUFault.NCA_S_FAULT_PIPE_CLOSED, 469762069},
                {PDUFault.NCA_S_FAULT_PIPE_ORDER, 469762070},
                {PDUFault.NCA_S_FAULT_PIPE_DISCIPLINE, 469762071},
                {PDUFault.NCA_S_FAULT_PIPE_COMM_ERROR, 469762072},
                {PDUFault.NCA_S_FAULT_PIPE_MEMORY, 469762073},
                {PDUFault.NCA_S_FAULT_CONTEXT_MISMATCH, 469762074},
                {PDUFault.NCA_S_FAULT_REMOTE_NO_MEMORY, 469762075},
                {PDUFault.NCA_INVALID_PRES_CONTEXT_ID, 469762076},
                {PDUFault.NCA_UNSUPPORTED_AUTHN_LEVEL, 469762077},
                {PDUFault.NCA_INVALID_CHECKSUM, 469762079},
                {PDUFault.NCA_INVALID_CRC, 469762080},
                {PDUFault.NCS_S_FAULT_USER_DEFINED, 469762081},
                {PDUFault.NCA_S_FAULT_TX_OPEN_FAILED, 469762082},
                {PDUFault.NCA_S_FAULT_CODESET_CONV_ERROR, 469762083},
                {PDUFault.NCA_S_FAULT_OBJECT_NOT_FOUND, 469762084},
                {PDUFault.NCA_S_FAULT_NO_CLIENT_STUB, 469762085},
                {PDUFault.NCA_OP_RNG_ERROR, 469827586},
                {PDUFault.NCA_UNK_IF, 469827587},
                {PDUFault.NCA_WRONG_BOOT_TIME, 469827590},
                {PDUFault.NCA_S_YOU_CRASHED, 469827593},
                {PDUFault.NCA_PROTO_ERROR, 469827595},
                {PDUFault.NCA_OUT_ARGS_TOO_BIG, 469827603},
                {PDUFault.NCA_SERVER_TOO_BUSY, 469827604},
                {PDUFault.NCA_UNSUPPORTED_TYPE, 469827607},
                {PDUFault.E_NOTIMPL, 2147500033L},
                {PDUFault.E_POINTER, 2147500035L},
                {PDUFault.E_AOBRT, 2147500036L},
                {PDUFault.E_UNEXPECTED, 2147549183L},
                {PDUFault.RPC_E_SERVERFAULT, 2147549445L},
                {PDUFault.RPC_E_DISCONNECTED, 2147549448L},
                {PDUFault.RPC_E_INVALID_IPID, 2147549459L},
                {PDUFault.RPC_E_TIMEOUT, 2147549471L},
                {PDUFault.DISP_E_MEMBERNOTFOUND, 2147614723L},
                {PDUFault.DISP_E_UNKNOWNNAME, 2147614726L},
                {PDUFault.DISP_E_BADPARAMCOUNT, 2147614734L},
                {PDUFault.CBA_E_MALFORMED, 2147797760L},
                {PDUFault.CBA_E_UNKNOWNOBJECT, 2147797761L},
                {PDUFault.CBA_E_INVALIDID, 2147797765L},
                {PDUFault.CBA_E_INVALIDCOOKIE, 2147797769L},
                {PDUFault.CBA_E_QOSTYPEUNSUPPORTED, 2147797771L},
                {PDUFault.CBA_E_QOSVALUEUNSUPPORTED, 2147797772L},
                {PDUFault.CBA_E_NOTAPPLICABLE, 2147797775L},
                {PDUFault.CBA_E_LIMITVIOLATION, 2147797778L},
                {PDUFault.CBA_E_QOSTYPENOTAPPLICABLE, 2147797779L},
                {PDUFault.CBA_E_OUTOFPARTNERACCOS, 2147797784L},
                {PDUFault.CBA_E_FLAGUNSUPPORTED, 2147797788L},
                {PDUFault.CBA_E_FRAMECOUNTUNSUPPORTED, 2147797795L},
                {PDUFault.CBA_E_MODECHANGE, 2147797797L},
                {PDUFault.E_OUTOFMEMORY, 2147942414L},
                {PDUFault.E_INVALIDARG, 2147942487L},
                {PDUFault.RPC_S_PROCNUM_OUT_OF_RANGE, 2147944145L},
                {PDUFault.OR_INVALID_OXID, 2147944310L}
        };
    }
}
