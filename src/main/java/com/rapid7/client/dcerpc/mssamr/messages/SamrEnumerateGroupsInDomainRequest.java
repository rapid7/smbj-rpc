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
package com.rapid7.client.dcerpc.mssamr.messages;

/**
 * The SamrEnumerateGroupsInDomain method enumerates all groups.
 *
 * long SamrEnumerateGroupsInDomain(
 *   [in] SAMPR_HANDLE DomainHandle,
 *   [in, out] unsigned long* EnumerationContext,
 *   [out] PSAMPR_ENUMERATION_BUFFER* Buffer,
 *   [in] unsigned long PreferedMaximumLength,
 *   [out] unsigned long* CountReturned
 * );
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/cc245757.aspx">
 *       https://msdn.microsoft.com/en-us/library/cc245757.aspx</a>
 */
public class SamrEnumerateGroupsInDomainRequest extends SamrEnumerateRequest<SamrEnumerateGroupsInDomainResponse> {
    public static final short OP_NUM = 11;

    public SamrEnumerateGroupsInDomainRequest(byte[] handle, int enumContext, int maxLength) {
        super(OP_NUM, handle, enumContext, maxLength);
    }

    @Override
    public SamrEnumerateGroupsInDomainResponse getResponseObject() {
        return new SamrEnumerateGroupsInDomainResponse();
    }
}
