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
package com.rapid7.client.dcerpc.msrrp;

import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_NO_MORE_ITEMS;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_SUCCESS;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.google.common.base.Strings;
import com.hierynomus.msdtyp.AccessMask;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumKeyRequest;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumKeyResponse;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumValueRequest;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumValueResponse;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegOpenKey;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryInfoKeyRequest;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryInfoKeyResponse;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryValueRequest;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryValueResponse;
import com.rapid7.client.dcerpc.msrrp.messages.HandleRequest;
import com.rapid7.client.dcerpc.msrrp.messages.HandleResponse;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.rapid7.client.dcerpc.transport.RPCTransport;

/**
 * This class implements a partial registry service in accordance with [MS-RRP]: Windows Remote Registry Protocol which
 * specifies the Windows Remote Registry Protocol, a remote procedure call (RPC)-based client/server protocol that is
 * used to remotely manage a hierarchical data store such as the Windows registry.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/cc244877.aspx">[MS-RRP]: Windows Remote Registry Protocol</a>
 */
public class RegistryService {
    private final static int MAX_REGISTRY_KEY_NAME_SIZE = 32767;
    private final static int MAX_REGISTRY_KEY_CLASS_SIZE = 32767;
    private final static int MAX_REGISTRY_VALUE_NAME_SIZE = 32767;
    private final static int MAX_REGISTRY_VALUE_DATA_SIZE = 1048576;
    private final static EnumSet<AccessMask> ACCESS_MASK = EnumSet.of(AccessMask.MAXIMUM_ALLOWED);
    private final Map<RegistryHive, ContextHandle> hiveCache = new HashMap<>();
    private final Map<String, ContextHandle> keyPathCache = new HashMap<>();
    private final RPCTransport transport;

    public RegistryService(final RPCTransport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Invalid RPC transport: " + transport);
        }
        this.transport = transport;
    }

    public boolean doesKeyExist(final String hiveName, final String keyPath)
        throws IOException {
        try {
            openKey(hiveName, keyPath);
        } catch (final RPCException exception) {
            if (exception.hasErrorCode()) {
                switch (exception.getErrorCode()) {
                case ERROR_FILE_NOT_FOUND:
                    return false;
                default:
                    throw exception;
                }
            }
        }
        return true;
    }

    public boolean doesValueExist(final String hiveName, final String keyPath, final String valueName)
        throws IOException {
        try {
            getValue(hiveName, keyPath, valueName);
        } catch (final RPCException exception) {
            if (exception.hasErrorCode()) {
                switch (exception.getErrorCode()) {
                case ERROR_FILE_NOT_FOUND:
                    return false;
                default:
                    throw exception;
                }
            }
        }
        return true;
    }

    public RegistryKeyInfo getKeyInfo(final String hiveName, final String keyPath)
        throws IOException {
        final ContextHandle handle = openKey(hiveName, keyPath);
        final BaseRegQueryInfoKeyRequest request = new BaseRegQueryInfoKeyRequest(handle);
        final BaseRegQueryInfoKeyResponse response = transport.call(request);
        final int returnCode = response.getReturnValue();
        if (returnCode != 0) {
            throw new RPCException("BaseRegQueryInfoKey", returnCode);
        }
        return new RegistryKeyInfo(response.getSubKeys(), response.getMaxSubKeyLen(), response.getMaxClassLen(),
            response.getValues(), response.getMaxValueNameLen(), response.getMaxValueLen(),
            response.getSecurityDescriptor(), response.getLastWriteTime());
    }

    public List<RegistryKey> getSubKeys(final String hiveName, final String keyPath)
        throws IOException {
        final List<RegistryKey> keyNames = new LinkedList<>();
        final ContextHandle handle = openKey(hiveName, keyPath);
        for (int index = 0;; index++) {
            final BaseRegEnumKeyRequest request =
                new BaseRegEnumKeyRequest(handle, index, MAX_REGISTRY_KEY_NAME_SIZE, MAX_REGISTRY_KEY_CLASS_SIZE);
            final BaseRegEnumKeyResponse response = transport.call(request);
            final int returnCode = response.getReturnValue();

            if (ERROR_SUCCESS.is(returnCode)) {
                keyNames.add(new RegistryKey(response.getName(), response.getLastWriteTime()));
            } else if (ERROR_NO_MORE_ITEMS.is(returnCode)) {
                return Collections.unmodifiableList(new ArrayList<RegistryKey>(keyNames));
            } else {
                throw new RPCException("BaseRegEnumKey", returnCode);
            }
        }
    }

    public List<RegistryValue> getValues(final String hiveName, final String keyPath)
        throws IOException {
        final List<RegistryValue> values = new LinkedList<>();
        final ContextHandle handle = openKey(hiveName, keyPath);
        for (int index = 0;; index++) {
            final BaseRegEnumValueRequest request =
                new BaseRegEnumValueRequest(handle, index, MAX_REGISTRY_VALUE_NAME_SIZE, MAX_REGISTRY_VALUE_DATA_SIZE);
            final BaseRegEnumValueResponse response = transport.call(request);
            final int returnCode = response.getReturnValue();

            if (ERROR_SUCCESS.is(returnCode)) {
                values.add(new RegistryValue(response.getName(), response.getType(), response.getData()));
            } else if (ERROR_NO_MORE_ITEMS.is(returnCode)) {
                return Collections.unmodifiableList(new ArrayList<RegistryValue>(values));
            } else {
                throw new RPCException("BaseRegEnumValue", returnCode);
            }
        }
    }

    public RegistryValue getValue(final String hiveName, final String keyPath, final String valueName)
        throws IOException {
        final String canonicalizedValueName = Strings.nullToEmpty(valueName);
        final ContextHandle handle = openKey(hiveName, keyPath);
        final BaseRegQueryValueRequest request =
            new BaseRegQueryValueRequest(handle, canonicalizedValueName, MAX_REGISTRY_VALUE_DATA_SIZE);
        final BaseRegQueryValueResponse response = transport.call(request);
        final int returnCode = response.getReturnValue();
        if (returnCode != 0) {
            throw new RPCException("BaseRegQueryValue", returnCode);
        }
        return new RegistryValue(canonicalizedValueName, response.getType(), response.getData());
    }

    protected String canonicalize(String keyPath) {
        keyPath = Strings.nullToEmpty(keyPath);
        keyPath = keyPath.toLowerCase();
        while (keyPath.contains("\\\\")) {
            keyPath = keyPath.replace("\\\\", "\\");
        }
        if (keyPath.endsWith("\\")) {
            keyPath = keyPath.substring(0, keyPath.length() - 1);
        }
        return keyPath;
    }

    protected ContextHandle openHive(final String hiveName)
        throws IOException {
        if (hiveName == null) {
            throw new IllegalArgumentException("Invalid hive: " + hiveName);
        }
        final RegistryHive hive = RegistryHive.getRegistryHiveByName(hiveName);
        if (hive == null) {
            throw new IllegalArgumentException("Unknown hive: " + hiveName);
        }
        synchronized (hiveCache) {
            if (hiveCache.containsKey(hive)) {
                return hiveCache.get(hive);
            } else {
                final short opNum = hive.getOpNum();
                final HandleRequest request = new HandleRequest(opNum, ACCESS_MASK);
                final HandleResponse response = transport.call(request);
                final int returnCode = response.getReturnValue();
                if (returnCode != 0) {
                    throw new RPCException(hive.getOpName(), returnCode);
                }
                final ContextHandle handle = response.getHandle();
                hiveCache.put(hive, handle);
                return handle;
            }
        }
    }

    protected ContextHandle openKey(final String hiveName, final String keyPath)
        throws IOException {
        final String canonicalizedKeyPath = canonicalize(keyPath);
        if (canonicalizedKeyPath.isEmpty()) {
            return openHive(hiveName);
        }
        synchronized (keyPathCache) {
            if (keyPathCache.containsKey(canonicalizedKeyPath)) {
                return keyPathCache.get(canonicalizedKeyPath);
            }
            final ContextHandle hiveHandle = openHive(hiveName);
            final BaseRegOpenKey request = new BaseRegOpenKey(hiveHandle, canonicalizedKeyPath, 0, ACCESS_MASK);
            final HandleResponse response = transport.call(request);
            final int returnCode = response.getReturnValue();
            if (returnCode != 0) {
                throw new RPCException("BaseRegOpenKey", returnCode);
            }
            final ContextHandle keyHandle = response.getHandle();
            keyPathCache.put(canonicalizedKeyPath, keyHandle);
            return keyHandle;
        }
    }
}
