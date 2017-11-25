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

package com.rapid7.client.dcerpc.dto.ace;

import java.nio.ByteBuffer;
import com.rapid7.client.dcerpc.dto.SID;

/**
 * This class represents an <a href="https://msdn.microsoft.com/en-us/library/cc230286.aspx">ACCESS_ALLOWED_ACE</a>
 */
public class AccessAllowedACE extends BasicACE {

    public AccessAllowedACE(final ACEHeader header, final int mask, final SID sid) {
        super(header, mask, sid);
    }

    AccessAllowedACE(final ACEHeader header, final int mask, final ByteBuffer buffer) {
        super(header, mask, buffer);
    }
}
