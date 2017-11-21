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

/*
 * <a href="https://msdn.microsoft.com/en-us/library/dd541650.aspx">SAMPR_PSID_ARRAY_OUT</a>
 *
 * The SAMPR_PSID_ARRAY_OUT structure holds an array of SID values.
 *
 * <pre>
 * typedef struct _SAMPR_PSID_ARRAY_OUT {
 * unsigned long Count;
 * [size_is(Count)] PSAMPR_SID_INFORMATION Sids;
 * } SAMPR_PSID_ARRAY_OUT,
 * *PSAMPR_PSID_ARRAY_OUT;
 * </pre>
 *
 * Count: The number of elements in Sids. If zero, Sids MUST be ignored. If nonzero, Sids MUST point to at least Count * sizeof(SAMPR_SID_INFORMATION)
 *        bytes of memory.
 * Sids: An array of pointers to SID values. For more information, see section 2.2.3.5.
 *
 */

public class SAMPRSIDArrayOut extends SAMPREnumerationBuffer<SAMPRSIDInformation> {

    @Override
    protected SAMPRSIDInformation initEntity() {
        return new SAMPRSIDInformation();
    }
}
