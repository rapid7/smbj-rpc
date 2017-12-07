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

package com.rapid7.client.dcerpc.mssrvs.dto;

import java.util.Objects;

public class NetShareInfo501 extends NetShareInfo1 {
    private final int flags;

    public NetShareInfo501(final String netName, final int type, final String remark, final int flags) {
        super(netName, type, remark);
        this.flags = flags;
    }

    public int getFlags() {
        return flags;
    }

    @Override
    public int hashCode() {
        return (31 * super.hashCode()) + Objects.hash(getFlags());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof NetShareInfo501)) {
            return false;
        }
        final NetShareInfo501 other = (NetShareInfo501) obj;
        return super.equals(obj)
                && getFlags() == other.getFlags();
    }

    @Override
    public String toString() {
        return String.format("NetShareInfo501{netName: %s, type: %d, remark: %s, flags: %d}",
                formatString(getNetName()), getType(), formatString(getRemark()), getFlags());
    }
}
