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
package com.rapid7.client.dcerpc.mslsad.objects;

/*
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/3e8738b2-5df6-499f-907d-ac2471bf0281">DOMAIN_INFORMATION_CLASS</a>
 */
public enum DomainInformationClass {
    DOMAIN_PASSWORD_INFORMATION(1),
    DOMAIN_GENERAL_INFORMATION(2),
    DOMAIN_LOGOFF_INFORMATION(3),
    DOMAIN_OEM_INFORMATION (4),
    DOMAIN_NAME_INFORMATION (5),
    DOMAIN_REPLICATION_INFORMATION (6),
    DOMAIN_SERVERROLE_INFORMATION (7),
    DOMAIN_MODIFIED_INFORMATION (8),
    DOMAIN_STATE_INFORMATION (9),
    DOMAIN_GENERAL_INFORMATION2 (11),
    DOMAIN_LOCKOUT_INFORMATION(12),
    DOMAIN_MODIFIED_INFORMATION2 (13),
    ;

	DomainInformationClass(final int infoLevel) {
        this.infoLevel = infoLevel;
    }

    public int getInfoLevel() {
        return infoLevel;
    }

    private final int infoLevel;
}
