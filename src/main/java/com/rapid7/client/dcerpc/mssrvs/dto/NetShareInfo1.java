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

public class NetShareInfo1 extends NetShareInfo0 {
    private final int type;
    private final String remark;

    public NetShareInfo1(final String netName, final int type, final String remark) {
        super(netName);
        this.type = type;
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public int hashCode() {
        return (31 * super.hashCode()) + Objects.hash(getType(), getRemark());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof NetShareInfo1)) {
            return false;
        }
        final NetShareInfo1 other = (NetShareInfo1) obj;
        return super.equals(obj)
                && getType() == other.getType()
                && Objects.equals(getRemark(), other.getRemark());
    }

    @Override
    public String toString() {
        return String.format("NetShareInfo1{netName: %s, type: %d, remark: %s}",
                formatString(getNetName()), getType(), formatString(getRemark()));
    }
}
