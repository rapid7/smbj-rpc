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
package com.rapid7.client.dcerpc.mssamr.objects;

/**
 * The DOMAIN_DISPLAY_INFORMATION enumeration indicates how to interpret the Buffer parameter for SamrQueryDisplayInformation, SamrQueryDisplayInformation2, SamrQueryDisplayInformation3, SamrGetDisplayEnumerationIndex, and SamrGetDisplayEnumerationIndex2. See section 2.2.8.13 for the list of the structures that are associated with each enumeration.
 *
 * <pre>
 * typedef enum _DOMAIN_DISPLAY_INFORMATION
 * {
 *   DomainDisplayUser = 1,
 *   DomainDisplayMachine,
 *   DomainDisplayGroup,
 *   DomainDisplayOemUser,
 *   DomainDisplayOemGroup
 * } DOMAIN_DISPLAY_INFORMATION,
 *   PDOMAIN_DISPLAY_INFORMATION;
 * </pre>
 *
 * DomainDisplayUser: Indicates the Buffer parameter is to be interpreted as a SAMPR_DOMAIN_DISPLAY_USER_BUFFER structure (see section 2.2.8.7).
 * DomainDisplayMachine: Indicates the Buffer parameter is to be interpreted as a SAMPR_DOMAIN_DISPLAY_MACHINE_BUFFER structure (see section 2.2.8.8).
 * DomainDisplayGroup: Indicates the Buffer parameter is to be interpreted as a SAMPR_DOMAIN_DISPLAY_GROUP_BUFFER structure (see section 2.2.8.9).
 * DomainDisplayOemUser: Indicates the Buffer parameter is to be interpreted as a SAMPR_DOMAIN_DISPLAY_OEM_USER_BUFFER structure (see section 2.2.8.10).
 * DomainDisplayOemGroup: Indicates the Buffer parameter is to be interpreted as a SAMPR_DOMAIN_DISPLAY_OEM_GROUP_BUFFER structure (see section 2.2.8.11).
 */
public enum DisplayInformationClass {
    DomainDisplayUser(1),
    DomainDisplayMachine(2),
    DomainDisplayGroup(3),
    DomainDisplayOemUser(4),
    DomainDisplayOemGroup(5);

    private DisplayInformationClass(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private final int value;
}
