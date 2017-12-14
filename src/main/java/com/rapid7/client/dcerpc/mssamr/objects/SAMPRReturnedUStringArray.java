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
package com.rapid7.client.dcerpc.mssamr.objects;

import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString.NonNullTerminated;

/**
 * The SAMPR_RETURNED_USTRING_ARRAY structure holds an array of counted UTF-16
 * encoded strings.
 *
 * <pre>
 * typedef struct _SAMPR_RETURNED_USTRING_ARRAY {
 *   unsigned long Count;
 *   [size_is(Count)] PRPC_UNICODE_STRING Element;
 * } SAMPR_RETURNED_USTRING_ARRAY,
 *  *PSAMPR_RETURNED_USTRING_ARRAY;
 * </pre>
 *
 * <p>
 * Count: The number of elements in Element. If zero, Element MUST be ignored.
 * If nonzero, Element MUST point to at least Count * sizeof(RPC_UNICODE_STRING)
 * bytes of memory.
 * </p>
 * <p>
 * Element: Array of counted strings (see RPC_UNICODE_STRING in [MS-DTYP]
 * section 2.3.10). The semantic meaning is method-dependent.
 * </p>
 */
public class SAMPRReturnedUStringArray extends SAMPREnumerationBuffer<NonNullTerminated> {
    @Override
    protected NonNullTerminated initEntity() throws UnmarshalException {
        return new NonNullTerminated();
    }
}
