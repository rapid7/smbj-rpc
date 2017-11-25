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

package com.rapid7.client.dcerpc.dto.ace;

import java.nio.ByteBuffer;
import java.util.Objects;
import com.rapid7.client.dcerpc.dto.SID;

/**
 * This class represents an access control entry <a href="https://msdn.microsoft.com/en-us/library/cc230295.aspx">ACE</a>
 */
public abstract class ACE {
    private final ACEHeader header;
    private final int mask;

    public ACE(final ACEHeader header, final int mask) {
        if (header == null) {
            throw new IllegalArgumentException("Expecting non-null header");
        }
        this.header = header;
        this.mask = mask;
    }

    public ACEType getType() {
        return getHeader().getType();
    }

    public ACEHeader getHeader() {
        return header;
    }

    public int getMask() {
        return mask;
    }

    public abstract SID getSID();

    @Override
    public int hashCode() {
        return Objects.hash(getHeader(), getMask(), getSID());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ACE)) {
            return false;
        }
        final ACE other = (ACE) obj;
        return Objects.equals(getHeader(), other.getHeader())
                && this.mask == other.mask
                && Objects.equals(getSID(), other.getSID());
    }

    @Override
    public String toString() {
        return String.format("ACE{header: %s, mask: 0x%02X, sid: %s}",
                getHeader(), getMask(), getSID());
    }

    public static ACE read(final ByteBuffer buffer) {
        // All ACE's have a header and mask
        // Header (4 bytes): An ACE_HEADER structure that specifies the size and type of ACE
        final int startPos = buffer.position();
        final ACEHeader header = new ACEHeader(buffer);
        // Mask (4 bytes): An ACCESS_MASK that specifies the user rights allowed by this ACE.
        final int mask = buffer.getInt();
        // Now construct the ACE based on type
        final ACE ace;
        switch (header.getType()) {
            case ACCESS_ALLOWED_ACE_TYPE:
                ace = new AccessAllowedACE(header, mask, buffer);
                break;
            case ACCESS_DENIED_ACE_TYPE:
                ace = new AccessDeniedACE(header, mask, buffer);
                break;
            case SYSTEM_AUDIT_ACE_TYPE:
                ace = new SystemAuditACE(header, mask, buffer);
                break;
            case ACCESS_ALLOWED_OBJECT_ACE_TYPE:
                ace = new AccessAllowedObjectACE(header, mask, buffer);
                break;
            case ACCESS_DENIED_OBJECT_ACE_TYPE:
                ace = new AccessDeniedObjectACE(header, mask, buffer);
                break;
            case SYSTEM_AUDIT_OBJECT_ACE_TYPE:
                ace = new SystemAuditObjectACE(header, mask, buffer, startPos);
                break;
            case ACCESS_ALLOWED_CALLBACK_ACE_TYPE:
                ace = new AccessAllowedCallbackACE(header, mask, buffer, startPos);
                break;
            case ACCESS_DENIED_CALLBACK_ACE_TYPE:
                ace = new AccessDeniedCallbackACE(header, mask, buffer, startPos);
                break;
            case ACCESS_ALLOWED_CALLBACK_OBJECT_ACE_TYPE:
                ace = new AccessAllowedCallbackObjectACE(header, mask, buffer, startPos);
                break;
            case ACCESS_DENIED_CALLBACK_OBJECT_ACE_TYPE:
                ace = new AccessDeniedCallbackObjectACE(header, mask, buffer, startPos);
                break;
            case SYSTEM_AUDIT_CALLBACK_ACE_TYPE:
                ace = new SystemAuditCallbackACE(header, mask, buffer, startPos);
                break;
            case SYSTEM_AUDIT_CALLBACK_OBJECT_ACE_TYPE:
                ace = new SystemAuditCallbackObjectACE(header, mask, buffer, startPos);
                break;
            case SYSTEM_MANDATORY_LABEL_ACE_TYPE:
                ace = new SystemMandatoryLabelACE(header, mask, buffer);
                break;
            case SYSTEM_RESOURCE_ATTRIBUTE_ACE_TYPE:
                ace = new SystemResourceAttributeACE(header, mask, buffer, startPos);
                break;
            case SYSTEM_SCOPED_POLICY_ID_ACE_TYPE:
                ace = new SystemScopePolicyIDACE(header, mask, buffer);
                break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Unsupported AceType: %d", header.getType().getValue()));
        }
        // The AceSize field can be greater than the sum of the individual fields,
        // but MUST be a multiple of 4 to ensure alignment on a DWORD boundary.
        buffer.position(buffer.position() + ((buffer.position() - startPos) % 4));
        return ace;
    }
}
