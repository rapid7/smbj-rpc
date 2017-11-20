/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc;

public enum Interface {
    WINREG_V1_0("winreg interface", "338cd001-2244-31f1-aaaa-900038001003:v1.0"),
    SRVSVC_V3_0("srvsvc interface", "4b324fc8-1670-01d3-1278-5a47bf6ee188:v3.0"),
    LSASVC_V0_0("lsarpc interface", "12345778-1234-ABCD-EF00-0123456789AB:v0.0"),
    SAMSVC_V1_0("samr interface", "12345778-1234-ABCD-EF00-0123456789AC:v1.0"),
    SVCCTL_V2_0("svcctl_interface", "367abb81-9844-35f1-ad32-98f038001003:v2.0"),
    NDR_32BIT_V2("NDR transfer syntax identifier", "8a885d04-1ceb-11c9-9fe8-08002b104860:v2.0");

    private final String name;
    private final String repr;
    private final byte[] uuid = new byte[16];
    private final short majorVersion;
    private final short minorVersion;

    Interface(final String name, final String uuid) {
        this.name = name;

        repr = uuid;

        final String[] interfaceComponents = uuid.split(":", 2);
        final String[] uuidComponents = interfaceComponents[0].split("-", 5);
        final String[] uuidBEComponents = {uuidComponents[0], uuidComponents[1], uuidComponents[2]};
        final String[] uuidLEComponents = {uuidComponents[3], uuidComponents[4]};

        int uuidIndex = 0;
        int uuidIndexNibble = 4;

        for (final String uuidComponent : uuidBEComponents) {
            final byte[] uuidComponentBytes = uuidComponent.getBytes();
            for (int byteIndex = uuidComponent.length() - 2; byteIndex >= 0; byteIndex -= 2) {
                for (int nibbleIndex = byteIndex; nibbleIndex < byteIndex + 2; nibbleIndex++) {
                    final byte uuidComponentByte = uuidComponentBytes[nibbleIndex];
                    this.uuid[uuidIndex] |= (byte) (Character.digit(uuidComponentByte, 16) << uuidIndexNibble);
                    if (uuidIndexNibble == 0) {
                        uuidIndexNibble = 4;
                        uuidIndex++;
                    } else {
                        uuidIndexNibble = 0;
                    }
                }
            }
        }

        for (final String uuidComponent : uuidLEComponents) {
            for (final byte uuidComponentByte : uuidComponent.getBytes()) {
                this.uuid[uuidIndex] |= (byte) (Character.digit(uuidComponentByte, 16) << uuidIndexNibble);
                if (uuidIndexNibble == 0) {
                    uuidIndexNibble = 4;
                    uuidIndex++;
                } else {
                    uuidIndexNibble = 0;
                }
            }
        }

        final String[] versionComponents = interfaceComponents[1].split("\\.", 2);
        majorVersion = Short.valueOf(versionComponents[0].substring(1));
        minorVersion = Short.valueOf(versionComponents[1]);
    }

    public String getName() {
        return name;
    }

    public String getRepr() {
        return repr;
    }

    public byte[] getUUID() {
        return uuid;
    }

    public short getMajorVersion() {
        return majorVersion;
    }

    public short getMinorVersion() {
        return minorVersion;
    }
}
