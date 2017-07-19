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
package com.rapid7.client.dcerpc.mssrvs.messages;

import java.util.Objects;

public class NetShareInfo2 extends NetShareInfo1 {
    private final Integer permissions;
    private final Integer maximumUsers;
    private final Integer currentUsers;
    private final String path;
    private final String password;

    public NetShareInfo2(
        final String name,
        final Integer type,
        final String comment,
        final Integer permissions,
        final Integer maximumUsers,
        final Integer currentUsers,
        final String path,
        final String password) {
        super(name, type, comment);
        this.permissions = permissions;
        this.maximumUsers = maximumUsers;
        this.currentUsers = currentUsers;
        this.path = path;
        this.password = password;
    }

    public int getPermissions() {
        return permissions;
    }

    public int getMaximumUsers() {
        return maximumUsers;
    }

    public int getCurrentUsers() {
        return currentUsers;
    }

    public String getPath() {
        return path;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("%s, permissions=%d, maximumUsers=%d, currentUsers=%d, path=%s, password=%s",
            super.toString(), permissions, maximumUsers, currentUsers, path, password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), permissions, maximumUsers, currentUsers, path, password);
    }

    @Override
    public boolean equals(final Object anObject) {
        return super.equals(anObject) && anObject instanceof NetShareInfo1
            && Objects.equals(((NetShareInfo2) anObject).permissions, permissions)
            && Objects.equals(((NetShareInfo2) anObject).maximumUsers, maximumUsers)
            && Objects.equals(((NetShareInfo2) anObject).currentUsers, currentUsers)
            && Objects.equals(((NetShareInfo2) anObject).path, path)
            && Objects.equals(((NetShareInfo2) anObject).password, password);
    }
}
