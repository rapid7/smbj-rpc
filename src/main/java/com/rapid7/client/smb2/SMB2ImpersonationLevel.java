/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.smb2;

/**
 * [MS-SMB2].pdf 2.2.13 SMB2 CREATE Request
 *
 * @see https://msdn.microsoft.com/en-us/library/cc246502.aspx
 */
public enum SMB2ImpersonationLevel {
    /** The server cannot impersonate or identify the client. */
    ANONYMOUS(0x00000000),
    /** The server can get the identity and privileges of the client, but cannot impersonate the client. */
    IDENTIFICATION(0x00000001),
    /** The server can impersonate the client's security context on the local system. */
    IMPERSONATION(0x00000002),
    /** The server can impersonate the client's security context on remote systems. */
    DELEGATE(0x00000003);

    private final int value;

    private SMB2ImpersonationLevel(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
