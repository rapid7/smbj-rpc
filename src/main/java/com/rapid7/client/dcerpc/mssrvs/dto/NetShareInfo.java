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

public abstract class NetShareInfo {
    private final String netName;

    public NetShareInfo(final String netName) {
        this.netName = netName;
    }

    public String getNetName() {
        return netName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNetName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof NetShareInfo)) {
            return false;
        }
        return Objects.equals(getNetName(), ((NetShareInfo) obj).getNetName());
    }

    String formatString(final String str) {
        if (str == null)
            return "null";
        return String.format("\"%s\"", str);
    }
}
