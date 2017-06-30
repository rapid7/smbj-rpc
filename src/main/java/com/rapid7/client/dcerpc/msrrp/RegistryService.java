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
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegCloseKey;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumKeyRequest;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumKeyResponse;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumValueRequest;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumValueResponse;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegOpenKey;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryInfoKeyRequest;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryInfoKeyResponse;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryValueRequest;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryValueResponse;
import com.rapid7.client.dcerpc.msrrp.messages.HandleResponse;
import com.rapid7.client.dcerpc.msrrp.messages.OpenClassesRoot;
import com.rapid7.client.dcerpc.msrrp.messages.OpenCurrentConfig;
import com.rapid7.client.dcerpc.msrrp.messages.OpenCurrentUser;
import com.rapid7.client.dcerpc.msrrp.messages.OpenLocalMachine;
import com.rapid7.client.dcerpc.msrrp.messages.OpenPerformanceData;
import com.rapid7.client.dcerpc.msrrp.messages.OpenPerformanceNlsText;
import com.rapid7.client.dcerpc.msrrp.messages.OpenPerformanceText;
import com.rapid7.client.dcerpc.msrrp.messages.OpenUsers;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class RegistryService {
    private final int MAX_REGISTRY_KEY_NAME_SIZE = 256;
    private final int MAX_REGISTRY_KEY_CLASS_SIZE = 32767;
    private final int MAX_REGISTRY_VALUE_NAME_SIZE = 32767;
    private final int MAX_REGISTRY_VALUE_DATA_SIZE = 65536;

    public RegistryService(final RPCTransport transport) {
        this.transport = transport;
    }

    public ContextHandle openClassesRoot()
        throws RegistryServiceException, TransportException, InterruptedException {
        final OpenClassesRoot request = new OpenClassesRoot(EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Open classes root returned error: " + response.getReturnValue());
        }
        return response.getHandle();
    }

    public ContextHandle openCurrentConfig()
        throws RegistryServiceException, TransportException, InterruptedException {
        final OpenCurrentConfig request = new OpenCurrentConfig(EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Open current user returned error: " + response.getReturnValue());
        }
        return response.getHandle();
    }

    public ContextHandle openCurrentUser()
        throws RegistryServiceException, TransportException, InterruptedException {
        final OpenCurrentUser request = new OpenCurrentUser(EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Open current user returned error: " + response.getReturnValue());
        }
        return response.getHandle();
    }

    public ContextHandle openLocalMachine()
        throws RegistryServiceException, TransportException, InterruptedException {
        final OpenLocalMachine request = new OpenLocalMachine(EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Open local machine returned error: " + response.getReturnValue());
        }
        return response.getHandle();
    }

    public ContextHandle openPerformanceData()
        throws RegistryServiceException, TransportException, InterruptedException {
        final OpenPerformanceData request = new OpenPerformanceData();
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Open performance data returned error: " + response.getReturnValue());
        }
        return response.getHandle();
    }

    public ContextHandle openPerformanceNlsText()
        throws RegistryServiceException, TransportException, InterruptedException {
        final OpenPerformanceNlsText request = new OpenPerformanceNlsText();
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Open performance data returned error: " + response.getReturnValue());
        }
        return response.getHandle();
    }

    public ContextHandle openPerformanceText()
        throws RegistryServiceException, TransportException, InterruptedException {
        final OpenPerformanceText request = new OpenPerformanceText();
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Open performance data returned error: " + response.getReturnValue());
        }
        return response.getHandle();
    }

    public ContextHandle openUsers()
        throws RegistryServiceException, TransportException, InterruptedException {
        final OpenUsers request = new OpenUsers(EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Open users returned error: " + response.getReturnValue());
        }
        return response.getHandle();
    }

    public void baseRegCloseKey(final ContextHandle handle)
        throws RegistryServiceException, TransportException, InterruptedException {
        final BaseRegCloseKey request = new BaseRegCloseKey(handle);
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Close key returned error: " + response.getReturnValue());
        }
    }

    public List<RegistryKey> baseRegEnumKey(final ContextHandle handle)
        throws RegistryServiceException, TransportException, InterruptedException {
        final List<RegistryKey> names = new LinkedList<>();

        for (int index = 0;; index++) {
            final BaseRegEnumKeyRequest request =
                new BaseRegEnumKeyRequest(handle, index, MAX_REGISTRY_KEY_NAME_SIZE, MAX_REGISTRY_KEY_CLASS_SIZE);
            final BaseRegEnumKeyResponse response = transport.transact(request);
            final int returnCode = response.getReturnValue();

            if (ERROR_SUCCESS.is(returnCode)) {
                names.add(new RegistryKey(response.getName(), response.getLastWriteTime()));
            }
            else if (ERROR_NO_MORE_ITEMS.is(returnCode)) {
                return Collections.unmodifiableList(new ArrayList<RegistryKey>(names));
            }
            else {
                throw new RegistryServiceException(
                    String.format("Unexpected response from BaseRegEnumKey request: %s (%d)",
                        SystemErrorCode.getErrorCode(returnCode), returnCode));
            }
        }
    }

    public List<RegistryValue> baseRegEnumValue(final ContextHandle handle)
        throws RegistryServiceException, TransportException, InterruptedException {
        final List<RegistryValue> values = new LinkedList<>();

        for (int index = 0;; index++) {
            final BaseRegEnumValueRequest request =
                new BaseRegEnumValueRequest(handle, index, MAX_REGISTRY_VALUE_NAME_SIZE, MAX_REGISTRY_VALUE_DATA_SIZE);
            final BaseRegEnumValueResponse response = transport.transact(request);
            final int returnCode = response.getReturnValue();

            if (ERROR_SUCCESS.is(returnCode)) {
                values.add(new RegistryValue(response.getName(), response.getType(), response.getData()));
            }
            else if (ERROR_NO_MORE_ITEMS.is(returnCode)) {
                return Collections.unmodifiableList(new ArrayList<RegistryValue>(values));
            }
            else {
                throw new RegistryServiceException(
                    String.format("Unexpected response from BaseRegEnumValue request: %s (%d)",
                        SystemErrorCode.getErrorCode(returnCode), returnCode));
            }
        }
    }

    public ContextHandle baseRegOpenKey(final ContextHandle handle, final String key)
        throws RegistryServiceException, TransportException, InterruptedException {
        final BaseRegOpenKey request = new BaseRegOpenKey(handle, key, 0, EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final HandleResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Open key returned error: " + response.getReturnValue());
        }
        return response.getHandle();
    }

    public void baseRegQueryInfoKey(final ContextHandle handle)
        throws RegistryServiceException, TransportException, InterruptedException {
        final BaseRegQueryInfoKeyRequest request = new BaseRegQueryInfoKeyRequest(handle);
        final BaseRegQueryInfoKeyResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Query info key returned error: " + response.getReturnValue());
        }
    }

    public RegistryValue baseRegQueryValue(final ContextHandle handle, final String name)
        throws RegistryServiceException, TransportException, InterruptedException {
        final BaseRegQueryValueRequest request =
            new BaseRegQueryValueRequest(handle, name, MAX_REGISTRY_VALUE_DATA_SIZE);
        final BaseRegQueryValueResponse response = transport.transact(request);
        if (0 != response.getReturnValue()) {
            throw new RegistryServiceException("Query value returned error: " + response.getReturnValue());
        }
        return new RegistryValue(name, response.getType(), response.getData());
    }

    private final RPCTransport transport;
}
