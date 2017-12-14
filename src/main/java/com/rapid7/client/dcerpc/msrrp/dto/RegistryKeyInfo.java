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

public class RegistryKeyInfo {
    private final int subKeys;
    private final int maxSubKeyLen;
    private final int maxClassLen;
    private final int values;
    private final int maxValueNameLen;
    private final int maxValueLen;
    private final int securityDescriptor;
    private final long lastWriteTime;

    public RegistryKeyInfo(final int subKeys, final int maxSubKeyLen, final int maxClassLen, final int values, final int maxValueNameLen, final int maxValueLen, final int securityDescriptor, final long lastWriteTime) {
        this.subKeys = subKeys;
        this.maxSubKeyLen = maxSubKeyLen;
        this.maxClassLen = maxClassLen;
        this.values = values;
        this.maxValueNameLen = maxValueNameLen;
        this.maxValueLen = maxValueLen;
        this.securityDescriptor = securityDescriptor;
        this.lastWriteTime = lastWriteTime;
    }

    /**
     * @return The count of the subkeys of the specified key.
     */
    public int getSubKeys() {
        return subKeys;
    }

    /**
     * @return The size of the key's subkey with the longest name.
     */
    public int getMaxSubKeyLen() {
        return maxSubKeyLen;
    }

    /**
     * @return The longest string that specifies a subkey class.
     */
    public int getMaxClassLen() {
        return maxClassLen;
    }

    /**
     * @return The number of values that are associated with the key.
     */
    public int getValues() {
        return values;
    }

    /**
     * @return The size of the key's longest value name.
     */
    public int getMaxValueNameLen() {
        return maxValueNameLen;
    }

    /**
     * @return The size in bytes of the longest data component in the key's values.
     */
    public int getMaxValueLen() {
        return maxValueLen;
    }

    /**
     * @return The size in bytes of the key's SECURITY_DESCRIPTOR.
     */
    public int getSecurityDescriptor() {
        return securityDescriptor;
    }

    /**
     * @return The time when a value was last written (set or created).
     */
    public long getLastWriteTime() {
        return lastWriteTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubKeys(), getMaxSubKeyLen(), getMaxClassLen(), getValues(),
                getMaxValueNameLen(), getMaxValueLen(), getSecurityDescriptor(), getLastWriteTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof RegistryKeyInfo)) {
            return false;
        }
        final RegistryKeyInfo other = (RegistryKeyInfo) obj;
        return getSubKeys() == other.getSubKeys()
                && getMaxSubKeyLen() == other.getMaxSubKeyLen()
                && getMaxClassLen() == other.getMaxClassLen()
                && getValues() == other.getValues()
                && getMaxValueNameLen() == other.getMaxValueNameLen()
                && getMaxValueLen() == other.getMaxValueLen()
                && getSecurityDescriptor() == other.getSecurityDescriptor()
                && getLastWriteTime() == other.getLastWriteTime();
    }

    @Override
    public String toString() {
        return String.format("RegistryKeyInfo{subKeys: %d, maxSubKeyLen: %d, maxClassLen: %d, " +
                "values: %d, maxValueNameLen: %d, maxValueLen: %d, securityDescriptor: %d," +
                "lastWriteTime: %d}", getSubKeys(), getMaxSubKeyLen(), getMaxClassLen(),
                getValues(), getMaxValueNameLen(), getMaxValueLen(), getSecurityDescriptor(), getLastWriteTime());
    }
}
