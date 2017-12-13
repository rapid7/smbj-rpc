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
package com.rapid7.client.dcerpc.msrrp;

import java.io.IOException;
import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.io.ndr.arrays.RPCConformantVaryingByteArray;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.dto.FileTime;
import com.rapid7.client.dcerpc.msrrp.dto.RegistryKey;
import com.rapid7.client.dcerpc.msrrp.dto.RegistryKeyInfo;
import com.rapid7.client.dcerpc.msrrp.dto.RegistryValue;
import com.rapid7.client.dcerpc.msrrp.dto.RegistryValueType;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumKeyResponse;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegEnumValueResponse;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryInfoKeyResponse;
import com.rapid7.client.dcerpc.msrrp.messages.BaseRegQueryValueResponse;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.transport.RPCTransport;

import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_FILE_NOT_FOUND;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_INVALID_FUNCTION;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_NO_MORE_ITEMS;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_SUCCESS;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class Test_RegistryService {

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Expecting non-null transport")
    public void constructorNullTransport() {
        new RegistryService(null);
    }

    @Test
    public void doesHiveWithEmptyKeyExistYes() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        assertTrue(registryService.doesKeyExist("HKLM", ""));

        verify(transport, times(1)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse);
    }

    @Test
    public void doesHiveWithNullKeyExistYes() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        assertTrue(registryService.doesKeyExist("HKLM", null));

        verify(transport, times(1)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse);
    }

    @Test
    public void doesHiveWithEmptyKeyExistNo() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());

        assertFalse(registryService.doesKeyExist("HKLM", ""));

        verify(transport, times(1)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse);
    }

    @Test
    public void doesHiveWithNullKeyExistNo() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());

        assertFalse(registryService.doesKeyExist("HKLM", null));

        verify(transport, times(1)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse);
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000001 \\(ERROR_INVALID_FUNCTION\\)")
    public void doesHiveExistUnknown() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_INVALID_FUNCTION.getValue());
        registryService.doesKeyExist("HKLM", "key");
    }

    @Test
    public void doesKeyExistYes() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        assertTrue(registryService.doesKeyExist("HKLM", "key"));

        verify(transport, times(2)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse);
    }

    @Test
    public void doesKeyExistNo() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());

        assertFalse(registryService.doesKeyExist("HKLM", "key"));

        verify(transport, times(2)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse);
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "BaseRegOpenKey returned error code: 0x00000001 \\(ERROR_INVALID_FUNCTION\\)")
    public void doesKeyExistUnknown() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_INVALID_FUNCTION.getValue());
        registryService.doesKeyExist("HKLM", "key");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Unknown hive: ")
    public void doesKeyExistWithEmptyHiveName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.doesKeyExist("", "key");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Invalid hive: null")
    public void doesKeyExistWithNullHiveName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.doesKeyExist(null, "key");
    }

    @Test
    public void doesValueExistYes() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        final RPCConformantVaryingByteArray byteArray = mock(RPCConformantVaryingByteArray.class);
        when(byteArray.getArray()).thenReturn(new byte[]{0x01, 0x23, 0x45, 0x67});

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(valueResponse.getData()).thenReturn(byteArray);
        when(valueResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        assertTrue(registryService.doesValueExist("HKLM", "key", "value"));

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(valueResponse, times(1)).getType();
        verify(valueResponse, times(1)).getData();
        verify(valueResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, valueResponse);
    }

    @Test
    public void doesValueExistNo() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());

        assertFalse(registryService.doesValueExist("HKLM", "key", "value"));

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(valueResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, valueResponse);
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "BaseRegQueryValue returned error code: 0x00000001 \\(ERROR_INVALID_FUNCTION\\)")
    public void doesValueExistUnknown() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getReturnValue()).thenReturn(ERROR_INVALID_FUNCTION.getValue());
        registryService.doesValueExist("HKLM", "key", "value");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Unknown hive: ")
    public void doesValueExistWithEmptyHiveName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.doesValueExist("", "key", "value");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Invalid hive: null")
    public void doesValueExistWithNullHiveName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.doesValueExist(null, "key", "value");
    }

    @Test
    public void doesValueExistWithEmptyKeyName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        final RPCConformantVaryingByteArray byteArray = mock(RPCConformantVaryingByteArray.class);
        when(byteArray.getArray()).thenReturn(new byte[]{0x01, 0x23, 0x45, 0x67});

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(valueResponse.getData()).thenReturn(byteArray);
        when(valueResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        assertTrue(registryService.doesValueExist("HKLM", "key", "value"));

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(valueResponse, times(1)).getType();
        verify(valueResponse, times(1)).getData();
        verify(valueResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, valueResponse);
    }

    @Test
    public void doesValueExistWithNullKeyName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        final RPCConformantVaryingByteArray byteArray = mock(RPCConformantVaryingByteArray.class);
        when(byteArray.getArray()).thenReturn(new byte[]{0x01, 0x23, 0x45, 0x67});

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(valueResponse.getData()).thenReturn(byteArray);
        when(valueResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        assertTrue(registryService.doesValueExist("HKLM", "key", "value"));

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(valueResponse, times(1)).getType();
        verify(valueResponse, times(1)).getData();
        verify(valueResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, valueResponse);
    }

    @Test
    public void doesValueExistWithEmptyName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        final RPCConformantVaryingByteArray byteArray = mock(RPCConformantVaryingByteArray.class);
        when(byteArray.getArray()).thenReturn(new byte[]{0x01, 0x23, 0x45, 0x67});

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(valueResponse.getData()).thenReturn(byteArray);
        when(valueResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        assertTrue(registryService.doesValueExist("HKLM", "key", "value"));

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(valueResponse, times(1)).getType();
        verify(valueResponse, times(1)).getData();
        verify(valueResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, valueResponse);
    }

    @Test
    public void doesValueExistWithNullName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        final RPCConformantVaryingByteArray byteArray = mock(RPCConformantVaryingByteArray.class);
        when(byteArray.getArray()).thenReturn(new byte[]{0x01, 0x23, 0x45, 0x67});

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(valueResponse.getData()).thenReturn(byteArray);
        when(valueResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        assertTrue(registryService.doesValueExist("HKLM", "key", "value"));

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(valueResponse, times(1)).getType();
        verify(valueResponse, times(1)).getData();
        verify(valueResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, valueResponse);
    }

    @Test
    public void getKeyInfo() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryInfoKeyResponse infoResponse = mock(BaseRegQueryInfoKeyResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(infoResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(infoResponse.getSubKeys()).thenReturn(111);
        when(infoResponse.getMaxSubKeyLen()).thenReturn(222);
        when(infoResponse.getMaxClassLen()).thenReturn(333);
        when(infoResponse.getValues()).thenReturn(444);
        when(infoResponse.getMaxValueNameLen()).thenReturn(555);
        when(infoResponse.getMaxValueLen()).thenReturn(666);
        when(infoResponse.getSecurityDescriptor()).thenReturn(777);
        when(infoResponse.getLastWriteTime()).thenReturn(116444736000000000l);
        when(infoResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final RegistryKeyInfo registryKeyInfo = registryService.getKeyInfo("HKLM", "key");

        assertEquals(111, registryKeyInfo.getSubKeys());
        assertEquals(222, registryKeyInfo.getMaxSubKeyLen());
        assertEquals(333, registryKeyInfo.getMaxClassLen());
        assertEquals(444, registryKeyInfo.getValues());
        assertEquals(555, registryKeyInfo.getMaxValueNameLen());
        assertEquals(666, registryKeyInfo.getMaxValueLen());
        assertEquals(777, registryKeyInfo.getSecurityDescriptor());
        assertEquals(116444736000000000l, registryKeyInfo.getLastWriteTime());

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(infoResponse, times(1)).getSubKeys();
        verify(infoResponse, times(1)).getMaxSubKeyLen();
        verify(infoResponse, times(1)).getMaxClassLen();
        verify(infoResponse, times(1)).getValues();
        verify(infoResponse, times(1)).getMaxValueNameLen();
        verify(infoResponse, times(1)).getMaxValueLen();
        verify(infoResponse, times(1)).getSecurityDescriptor();
        verify(infoResponse, times(1)).getLastWriteTime();
        verify(infoResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, infoResponse);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Unknown hive: ")
    public void getKeyInfoWithEmptyHive() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.getKeyInfo("", "key");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Invalid hive: null")
    public void getKeyInfoWithNullHive() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.getKeyInfo(null, "key");
    }

    @Test(expectedExceptions = {RPCException.class}, expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getKeyInfoWhenHiveDoesNotExistWithEmptyKey() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getKeyInfo("HKLM", "");
    }

    @Test(expectedExceptions = {RPCException.class}, expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getKeyInfoWhenHiveDoesNotExistWithNullKey() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getKeyInfo("HKLM", null);
    }

    @Test(expectedExceptions = {RPCException.class}, expectedExceptionsMessageRegExp = "BaseRegOpenKey returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getKeyInfoWhenKeyDoesNotExist() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryInfoKeyResponse infoResponse = mock(BaseRegQueryInfoKeyResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(infoResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getKeyInfo("HKLM", "key");
    }

    @Test
    public void getSubKeys() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegEnumKeyResponse enumResponse1 = mock(BaseRegEnumKeyResponse.class);
        final BaseRegEnumKeyResponse enumResponse2 = mock(BaseRegEnumKeyResponse.class);
        final BaseRegEnumKeyResponse enumResponse3 = mock(BaseRegEnumKeyResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(enumResponse1).thenReturn(enumResponse2).thenReturn(enumResponse3);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(enumResponse1.getLpNameOut()).thenReturn(RPCUnicodeString.NullTerminated.of("subKey1"));
        when(enumResponse1.getLastWriteTime()).thenReturn(116444736000000000l);
        when(enumResponse1.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(enumResponse2.getLpNameOut()).thenReturn(RPCUnicodeString.NullTerminated.of("subKey2"));
        when(enumResponse2.getLastWriteTime()).thenReturn(116444736000000000l);
        when(enumResponse2.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(enumResponse3.getReturnValue()).thenReturn(ERROR_NO_MORE_ITEMS.getValue());

        final List<RegistryKey> subKeys = registryService.getSubKeys("HKLM", "key");
        final RegistryKey subKey1 = new RegistryKey("subKey1", new FileTime(116444736000000000l));
        final RegistryKey subKey2 = new RegistryKey("subKey2", new FileTime(116444736000000000l));

        assertTrue(subKeys.contains(subKey1));
        assertTrue(subKeys.contains(subKey2));
        assertTrue(subKeys.size() == 2);

        verify(transport, times(5)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(enumResponse1, times(1)).getLpNameOut();
        verify(enumResponse1, times(1)).getLastWriteTime();
        verify(enumResponse1, times(1)).getReturnValue();
        verify(enumResponse2, times(1)).getLpNameOut();
        verify(enumResponse2, times(1)).getLastWriteTime();
        verify(enumResponse2, times(1)).getReturnValue();
        verify(enumResponse3, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, enumResponse1, enumResponse2, enumResponse3);
    }

    @Test
    public void getSubKeysWhenEmpty() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegEnumKeyResponse enumResponse = mock(BaseRegEnumKeyResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(enumResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(enumResponse.getReturnValue()).thenReturn(ERROR_NO_MORE_ITEMS.getValue());

        final List<RegistryKey> subKeys = registryService.getSubKeys("HKLM", "key");

        assertTrue(subKeys.isEmpty());

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(enumResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, enumResponse);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Unknown hive: ")
    public void getSubKeysWithEmptyHive() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.getSubKeys("", "key");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Invalid hive: null")
    public void getSubKeysWithNullHive() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.getSubKeys(null, "key");
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getSubKeysWhenHiveDoesNotExistWithEmptyKey() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getSubKeys("HKLM", "");
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getSubKeysWhenHiveDoesNotExistWithNullKey() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getSubKeys("HKLM", null);
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "BaseRegOpenKey returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getSubKeysWhenKeyDoesNotExist() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getSubKeys("HKLM", "key");
    }

    @Test
    public void getValues() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegEnumValueResponse enumResponse1 = mock(BaseRegEnumValueResponse.class);
        final BaseRegEnumValueResponse enumResponse2 = mock(BaseRegEnumValueResponse.class);
        final BaseRegEnumValueResponse enumResponse3 = mock(BaseRegEnumValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        final RPCConformantVaryingByteArray byteArray = mock(RPCConformantVaryingByteArray.class);
        when(byteArray.getArray()).thenReturn(new byte[]{0x01, 0x23, 0x45, 0x67});

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(enumResponse1).thenReturn(enumResponse2).thenReturn(enumResponse3);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(enumResponse1.getName()).thenReturn(RPCUnicodeString.NullTerminated.of("value1"));
        when(enumResponse1.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(enumResponse1.getData()).thenReturn(byteArray);
        when(enumResponse1.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(enumResponse2.getName()).thenReturn(RPCUnicodeString.NullTerminated.of("value2"));
        when(enumResponse2.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(enumResponse2.getData()).thenReturn(byteArray);
        when(enumResponse2.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(enumResponse3.getReturnValue()).thenReturn(ERROR_NO_MORE_ITEMS.getValue());

        final List<RegistryValue> values = registryService.getValues("HKLM", "key");
        final RegistryValue value1 = new RegistryValue("value1", RegistryValueType.REG_BINARY, new byte[]{0x01, 0x23, 0x45, 0x67});
        final RegistryValue value2 = new RegistryValue("value2", RegistryValueType.REG_BINARY, new byte[]{0x01, 0x23, 0x45, 0x67});

        assertTrue(values.contains(value1));
        assertTrue(values.contains(value2));
        assertTrue(values.size() == 2);

        verify(transport, times(5)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(enumResponse1, times(1)).getName();
        verify(enumResponse1, times(1)).getType();
        verify(enumResponse1, times(1)).getData();
        verify(enumResponse1, times(1)).getReturnValue();
        verify(enumResponse2, times(1)).getName();
        verify(enumResponse2, times(1)).getType();
        verify(enumResponse2, times(1)).getData();
        verify(enumResponse2, times(1)).getReturnValue();
        verify(enumResponse3, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, enumResponse1, enumResponse2, enumResponse3);
    }

    @Test
    public void getValuesWhenEmpty() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegEnumValueResponse enumResponse = mock(BaseRegEnumValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(enumResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(enumResponse.getReturnValue()).thenReturn(ERROR_NO_MORE_ITEMS.getValue());

        final List<RegistryValue> values = registryService.getValues("HKLM", "key");

        assertTrue(values.isEmpty());

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(enumResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, enumResponse);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Unknown hive: ")
    public void getValuesWithEmptyHive() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.getValues("", "key");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Invalid hive: null")
    public void getValuesWithNullHive() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.getValues(null, "key");
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getValuesWhenHiveDoesNotExistWithEmptyKey() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getValues("HKLM", "");
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getValuesWhenHiveDoesNotExistWithNullKey() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getValues("HKLM", null);
    }

    @Test(expectedExceptions = {RPCException.class}, expectedExceptionsMessageRegExp = "BaseRegOpenKey returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getValuesWhenKeyDoesNotExist() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getValues("HKLM", "key");
    }

    @Test
    public void getValue() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        final RPCConformantVaryingByteArray byteArray = mock(RPCConformantVaryingByteArray.class);
        when(byteArray.getArray()).thenReturn(new byte[]{0x01, 0x23, 0x45, 0x67});

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(valueResponse.getData()).thenReturn(byteArray);
        when(valueResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final RegistryValue value = registryService.getValue("HKLM", "key", "value");

        assertEquals("value", value.getName());
        assertEquals(RegistryValueType.REG_BINARY, value.getType());
        assertArrayEquals(new byte[]{0x01, 0x23, 0x45, 0x67}, value.getData());

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(valueResponse, times(1)).getType();
        verify(valueResponse, times(1)).getData();
        verify(valueResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, valueResponse);
    }

    @Test
    public void getValueWithEmptyName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        final RPCConformantVaryingByteArray byteArray = mock(RPCConformantVaryingByteArray.class);
        when(byteArray.getArray()).thenReturn(new byte[]{0x01, 0x23, 0x45, 0x67});

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(valueResponse.getData()).thenReturn(byteArray);
        when(valueResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final RegistryValue value = registryService.getValue("HKLM", "key", "");

        assertEquals("", value.getName());
        assertEquals(RegistryValueType.REG_BINARY, value.getType());
        assertArrayEquals(new byte[]{0x01, 0x23, 0x45, 0x67}, value.getData());

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(valueResponse, times(1)).getType();
        verify(valueResponse, times(1)).getData();
        verify(valueResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, valueResponse);
    }

    @Test
    public void getValueWithNullName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        final RPCConformantVaryingByteArray byteArray = mock(RPCConformantVaryingByteArray.class);
        when(byteArray.getArray()).thenReturn(new byte[]{0x01, 0x23, 0x45, 0x67});

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getType()).thenReturn(RegistryValueType.REG_BINARY.getTypeID());
        when(valueResponse.getData()).thenReturn(byteArray);
        when(valueResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final RegistryValue value = registryService.getValue("HKLM", "key", null);

        assertEquals("", value.getName());
        assertEquals(RegistryValueType.REG_BINARY, value.getType());
        assertArrayEquals(new byte[]{0x01, 0x23, 0x45, 0x67}, value.getData());

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verify(valueResponse, times(1)).getType();
        verify(valueResponse, times(1)).getData();
        verify(valueResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse, valueResponse);
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "BaseRegQueryValue returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getValueWhenDoesNotExist() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final BaseRegQueryValueResponse valueResponse = mock(BaseRegQueryValueResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse).thenReturn(valueResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(valueResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getValue("HKLM", "key", "value");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Unknown hive: ")
    public void getValueWithEmptyHive() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.getValue("", "key", "value");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Invalid hive: null")
    public void getValueWithNullHive() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.getValue(null, "key", "value");
    }

    @Test(expectedExceptions = {RPCException.class}, expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getValueWhenHiveDoesNotExistWithEmptyKey() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getValue("HKLM", "", "value");
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getValueWhenHiveDoesNotExistWithNullKey() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getValue("HKLM", null, "value");
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "BaseRegOpenKey returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void getValueWhenKeyDoesNotExist() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.getValue("HKLM", "key", "value");
    }

    @Test
    public void canonicalize() {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);

        assertEquals("hklm", registryService.canonicalize("HKLM"));
        assertEquals("hklm\\software", registryService.canonicalize("HKLM\\Software"));
        assertEquals("hklm\\software", registryService.canonicalize("HKLM\\\\Software"));
        assertEquals("hklm\\software", registryService.canonicalize("HKLM\\\\Software\\"));
        assertEquals("hklm\\software", registryService.canonicalize("HKLM\\\\Software\\\\"));
        assertEquals("hklm\\software", registryService.canonicalize("HKLM\\\\\\Software\\\\\\"));
    }

    @Test
    public void openHive() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getHandle()).thenReturn(Hex.decode("01234567"));
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final byte[] handle = registryService.openHive("HKLM");

        assertArrayEquals(Hex.decode("01234567"), handle);

        verify(transport, times(1)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse);
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void openHiveWhenHiveDoesNotExist() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.openKey("HKLM", "key");
    }

    @Test
    public void openHiveCached() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getHandle()).thenReturn(Hex.decode("01234567"));
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final byte[] handle1 = registryService.openHive("HKLM");
        final byte[] handle2 = registryService.openHive("HKLM");

        assertArrayEquals(Hex.decode("01234567"), handle1);
        assertArrayEquals(Hex.decode("01234567"), handle2);

        verify(transport, times(1)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse);
    }

    @Test
    public void openHiveNotCached() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse1 = mock(HandleResponse.class);
        final HandleResponse hiveResponse2 = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse1).thenReturn(hiveResponse2);
        when(hiveResponse1.getHandle()).thenReturn(Hex.decode("11111111"));
        when(hiveResponse1.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(hiveResponse2.getHandle()).thenReturn(Hex.decode("22222222"));
        when(hiveResponse2.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final byte[] handle1 = registryService.openHive("HKLM");
        final byte[] handle2 = registryService.openHive("HKU");

        assertArrayEquals(Hex.decode("11111111"), handle1);
        assertArrayEquals(Hex.decode("22222222"), handle2);

        verify(transport, times(2)).call(any(RequestCall.class));
        verify(hiveResponse1, times(1)).getHandle();
        verify(hiveResponse1, times(1)).getReturnValue();
        verify(hiveResponse2, times(1)).getHandle();
        verify(hiveResponse2, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse1, hiveResponse2);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Unknown hive: ")
    public void openHiveWithEmptyHiveName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.openHive("");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Invalid hive: null")
    public void openHiveWithNullHiveName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.openHive(null);
    }

    @Test
    public void openKey() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse);
        when(hiveResponse.getHandle()).thenReturn(Hex.decode("76543210"));
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getHandle()).thenReturn(Hex.decode("01234567"));
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final byte[] handle = registryService.openKey("HKLM", "key");

        assertArrayEquals(Hex.decode("01234567"), handle);

        verify(transport, times(2)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse);
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "OpenLocalMachine returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void openKeyWhenHiveDoesNotExist() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.openKey("HKLM", "key");
    }

    @Test(expectedExceptions = {RPCException.class},
            expectedExceptionsMessageRegExp = "BaseRegOpenKey returned error code: 0x00000002 \\(ERROR_FILE_NOT_FOUND\\)")
    public void openKeyWhenKeyDoesNotExist() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse);
        when(hiveResponse.getHandle()).thenReturn(Hex.decode("76543210"));
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getReturnValue()).thenReturn(ERROR_FILE_NOT_FOUND.getValue());
        registryService.openKey("HKLM", "key");
    }

    @Test
    public void openKeyCached() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse);
        when(hiveResponse.getHandle()).thenReturn(Hex.decode("76543210"));
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse.getHandle()).thenReturn(Hex.decode("01234567"));
        when(keyResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final byte[] handle1 = registryService.openKey("HKLM", "key");
        final byte[] handle2 = registryService.openKey("HKLM", "key");

        assertArrayEquals(Hex.decode("01234567"), handle1);
        assertArrayEquals(Hex.decode("01234567"), handle2);

        verify(transport, times(2)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse, times(1)).getHandle();
        verify(keyResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse);
    }

    @Test
    public void openKeyNotCached() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final HandleResponse keyResponse1 = mock(HandleResponse.class);
        final HandleResponse keyResponse2 = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse).thenReturn(keyResponse1).thenReturn(keyResponse2);
        when(hiveResponse.getHandle()).thenReturn(Hex.decode("76543210"));
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse1.getHandle()).thenReturn(Hex.decode("11111111"));
        when(keyResponse1.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());
        when(keyResponse2.getHandle()).thenReturn(Hex.decode("22222222"));
        when(keyResponse2.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final byte[] handle1 = registryService.openKey("HKLM", "key1");
        final byte[] handle2 = registryService.openKey("HKLM", "key2");

        assertArrayEquals(Hex.decode("11111111"), handle1);
        assertArrayEquals(Hex.decode("22222222"), handle2);

        verify(transport, times(3)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verify(keyResponse1, times(1)).getHandle();
        verify(keyResponse1, times(1)).getReturnValue();
        verify(keyResponse2, times(1)).getHandle();
        verify(keyResponse2, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse, keyResponse1, keyResponse2);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Unknown hive: ")
    public void openKeyWithEmptyHiveName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.openKey("", "key");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Invalid hive: null")
    public void openKeyWithNullHiveName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final RegistryService registryService = new RegistryService(transport);
        registryService.openKey(null, "key");
    }

    @Test
    public void openKeyWithEmptyKeyName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getHandle()).thenReturn(Hex.decode("76543210"));
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final byte[] handle = registryService.openKey("HKLM", "");

        assertArrayEquals(Hex.decode("76543210"), handle);

        verify(transport, times(1)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse);
    }

    @Test
    public void openKeyWithNullKeyName() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final HandleResponse hiveResponse = mock(HandleResponse.class);
        final RegistryService registryService = new RegistryService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(hiveResponse);
        when(hiveResponse.getHandle()).thenReturn(Hex.decode("76543210"));
        when(hiveResponse.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final byte[] handle = registryService.openKey("HKLM", null);

        assertArrayEquals(Hex.decode("76543210"), handle);

        verify(transport, times(1)).call(any(RequestCall.class));
        verify(hiveResponse, times(1)).getHandle();
        verify(hiveResponse, times(1)).getReturnValue();
        verifyNoMoreInteractions(transport, hiveResponse);
    }
}
