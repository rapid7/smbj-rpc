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

public class NetShareInfo2 extends NetShareInfo1 {

    private final int permissions;
    private final int maxUses;
    private final int currentUses;
    private final String path;
    private final String passwd;

    public NetShareInfo2(final String netName, final int type, final String remark,
            final int permissions, final int maxUses, final int currentUses, final String path, final String passwd) {
        super(netName, type, remark);
        this.permissions = permissions;
        this.maxUses = maxUses;
        this.currentUses = currentUses;
        this.path = path;
        this.passwd = passwd;
    }

    public int getPermissions() {
        return permissions;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public int getCurrentUses() {
        return currentUses;
    }

    public String getPath() {
        return path;
    }

    public String getPasswd() {
        return passwd;
    }

    @Override
    public int hashCode() {
        return (31 * super.hashCode())
                + Objects.hash(getPermissions(), getMaxUses(), getCurrentUses(), getPath(), getPasswd());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof NetShareInfo2)) {
            return false;
        }
        final NetShareInfo2 other = (NetShareInfo2) obj;
        return super.equals(obj)
                && getPermissions() == other.getPermissions()
                && getMaxUses() == other.getMaxUses()
                && getCurrentUses() == other.getCurrentUses()
                && Objects.equals(getPath(), other.getPath())
                && Objects.equals(getPasswd(), other.getPasswd());
    }

    @Override
    public String toString() {
        return String.format("NetShareInfo2{netName: %s, type: %d, remark: %s, " +
                        "permissions: %d, maxUses: %d, currentUses: %d, path: %s, passwd: %s}",
                formatString(getNetName()), getType(), formatString(getRemark()),
                getPermissions(), getMaxUses(), getCurrentUses(), formatString(getPasswd()), formatString(getPasswd()));
    }
}
