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
package com.rapid7.client.dcerpc.msrrp.dto;

import java.util.Objects;

public class RegistryKey {
    private final String name;
    private final FileTime lastWriteTime;

    public RegistryKey(final String name, final FileTime lastWriteTime) {
        if (name == null) {
            throw new IllegalArgumentException("Name is invalid: " + name);
        }
        if (lastWriteTime == null) {
            throw new IllegalArgumentException("LastWriteTime is invalid: " + lastWriteTime);
        }
        this.name = name;
        this.lastWriteTime = lastWriteTime;
    }

    public String getName() {
        return name;
    }

    public FileTime getLastWriteTime() {
        return lastWriteTime;
    }

    @Override
    public String toString() {
        return String.format("%s {lastWriteTime=%s}", name, lastWriteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastWriteTime);
    }

    @Override
    public boolean equals(final Object anObject) {
        return anObject instanceof RegistryKey && Objects.equals(name, ((RegistryKey) anObject).name) && Objects.equals(lastWriteTime, ((RegistryKey) anObject).lastWriteTime);
    }
}
