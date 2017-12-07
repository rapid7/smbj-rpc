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

package com.rapid7.client.dcerpc.mssrvs.objects;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc247119.aspx">SHARE_ENUM_UNION</a>
 */
public enum ShareEnumLevel {
    SHARE_INFO_0_CONTAINER(0),
    SHARE_INFO_1_CONTAINER(1),
    SHARE_INFO_2_CONTAINER(2),
    SHARE_INFO_501_CONTAINER(501),
    SHARE_INFO_502_CONTAINER(502),
    SHARE_INFO_503_CONTAINER(503);

    private final int infoLevel;

    ShareEnumLevel(final int infoLevel) {
        this.infoLevel = infoLevel;
    }

    public int getInfoLevel() {
        return infoLevel;
    }
}
