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

package com.rapid7.client.dcerpc.dto;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * This class represents a <a href="https://msdn.microsoft.com/en-us/library/cc230366.aspx">SECURITY_DESCRIPTOR</a>
 */
public class SecurityDescriptor {
    // Control (2 bytes): An unsigned 16-bit field that specifies control access bit flags.
    private final short control;
    private final SID ownerSID;
    private final SID groupSID;
    private final ACL sacl;
    private final ACL dacl;

    public SecurityDescriptor(final short control, final SID ownerSID, final SID groupSID,
            final ACL sacl, final ACL dacl) {
        this.control = control;
        this.ownerSID = ownerSID;
        this.groupSID = groupSID;
        this.sacl = sacl;
        this.dacl = dacl;
    }

    public short getControl() {
        return control;
    }

    public SID getOwnerSID() {
        return ownerSID;
    }

    public SID getGroupSID() {
        return groupSID;
    }

    public ACL getSACL() {
        return sacl;
    }

    public ACL getDACL() {
        return dacl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getControl(), getOwnerSID(), getGroupSID(), getSACL(), getDACL());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof SecurityDescriptor)) {
            return false;
        }
        final SecurityDescriptor other = (SecurityDescriptor) obj;
        return Objects.equals(getControl(), other.getControl())
                && Objects.equals(getOwnerSID(), other.getOwnerSID())
                && Objects.equals(getGroupSID(), other.getGroupSID())
                && Objects.equals(getSACL(), other.getSACL())
                && Objects.equals(getDACL(), other.getDACL());
    }

    @Override
    public String toString() {
        return String.format("SecurityDescriptor{control: 0x%02X, ownerSID: %s, groupSID: %s, sacl: %s, dacl: %s}",
                getControl(), getOwnerSID(), getGroupSID(), getSACL(), getDACL());
    }

    public enum Control {
        // Self-Relative
        // Set when the security descriptor is in self-relative format. Cleared when the security descriptor is in absolute format.
        SR((short) 0x8000),
        // RM Control Valid
        // Set to 0x1 when the Sbz1 field is to be interpreted as resource manager control bits.
        RM((short) 0x4000),
        // SACL Protected
        PS((short) 0x2000),
        // DACL Protected
        PD((short) 0x1000),
        // SACL Auto-Inherited
        SI((short) 0x0800),
        // DACL Auto-Inherited
        DI((short) 0x0400),
        // SACL Computed Inheritance Required
        SC((short) 0x0200),
        // DACL Computed Inheritance Required
        DC((short) 0x0100),
        // DACL Trusted
        DT((short) 0x0080),
        // Server Security
        SS((short) 0x0040),
        // SACL Defaulted
        SD((short) 0x0020),
        // SACL Present
        SP((short) 0x0010),
        // DACL Defaulted
        DD((short) 0x0008),
        // DACL Present
        DP((short) 0x0004),
        // Group Defaulted
        GD((short) 0x0002),
        //Owner Defaulted
        OD((short) 0x0001);

        private final short value;

        Control(final short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public boolean isSet(int bitField) {
            return (bitField & getValue()) != 0;
        }
    }

    public static class MalformedSecurityDescriptorException extends IllegalArgumentException {

        public MalformedSecurityDescriptorException(Throwable cause) {
            this(null, cause);
        }

        public MalformedSecurityDescriptorException(String msg) {
            this(msg, null);
        }

        public MalformedSecurityDescriptorException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public static SecurityDescriptor read(final byte[] bytes) {
        return read(ByteBuffer.wrap(bytes));
    }

    public static SecurityDescriptor read(final ByteBuffer buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("Expecting not null buffer");
        }
        // TODO put this somewhere else?
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        try {
            final byte revision = buffer.get();
            if (revision != 1) {
                throw new MalformedSecurityDescriptorException(String.format(
                        "Revision must be 1, got: %d", revision));
            }
            // Sbz1 (1 byte): An unsigned 8-bit value with no meaning unless the Control RM bit is set to 0x1.
            // If the RM bit is set to 0x1, Sbz1 is interpreted as the resource manager control bits
            // that contain specific information<70> for the specific resource manager that is accessing
            // the structure. The permissible values and meanings of these bits are determined by the
            // implementation of the resource manager.
            final char sbz1 = (char) buffer.get();
            // Control (2 bytes): An unsigned 16-bit field that specifies control access bit flags.
            // The Self Relative (SR) bit MUST be set when the security descriptor is in self-relative format.
            final short control = buffer.getShort();
            final int offsetOwner = buffer.getInt();
            // This must be a valid offset if the OD flag is not set
            if (!Control.OD.isSet(control) && offsetOwner < 0) {
                throw new MalformedSecurityDescriptorException(String.format(
                        "OD is not set but offsetOwner is: %d", offsetOwner));
            }
            // This must be a valid offset if the GD flag is not set
            final int offsetGroup = buffer.getInt();
            if (!Control.GD.isSet(control) && offsetGroup < 0) {
                throw new MalformedSecurityDescriptorException(String.format(
                        "GD is not set but offsetGroup is: %d", offsetGroup));
            }
            final int offsetSACL = buffer.getInt();
            // This must be a valid offset if the SP flag is set
            if (Control.SP.isSet(control)) {
                if (offsetSACL <= 0) {
                    throw new MalformedSecurityDescriptorException(String.format(
                            "SP is set but offsetSACL <= 0: %d", offsetSACL));
                }
                // If the SP flag is not set, this field MUST be set to zero.
            } else if (offsetSACL != 0) {
                throw new MalformedSecurityDescriptorException(String.format(
                        "SP is not set but offsetSACL != 0: %d", offsetSACL));
            }
            final int offsetDACL = buffer.getInt();
            // This must be a valid offset if the DP flag is set
            if (Control.DP.isSet(control)) {
                if (offsetDACL <= 0) {
                    throw new MalformedSecurityDescriptorException(String.format(
                            "DP is set but offsetDACL <= 0: %d", offsetDACL));
                }
                // If the DP flag is not set, this field MUST be set to zero.
            } else if (offsetDACL != 0) {
                throw new MalformedSecurityDescriptorException(String.format(
                        "DP is not set but offsetDACL != 0: %d", offsetDACL));
            }
            // OwnerSid
            final SID ownerSID;
            if (offsetOwner > 0) {
                buffer.position(offsetOwner);
                ownerSID = readSID(buffer);
            } else {
                ownerSID = null;
            }
            // OwnerGroup
            final SID ownerGroup;
            if (offsetGroup > 0) {
                buffer.position(offsetGroup);
                ownerGroup = readSID(buffer);
            } else {
                ownerGroup = null;
            }
            final ACL sacl;
            if (offsetSACL > 0) {
                buffer.position(offsetSACL);
                sacl = ACL.read(buffer);
            } else {
                sacl = null;
            }
            final ACL dacl;
            if (offsetDACL > 0) {
                buffer.position(offsetDACL);
                dacl = ACL.read(buffer);
            } else {
                dacl = null;
            }
            return new SecurityDescriptor(control, ownerSID, ownerGroup, sacl, dacl);
        } catch (Exception e) {
            // TODO
            throw new MalformedSecurityDescriptorException(e);
        }
    }

    public static SID readSID(final ByteBuffer buffer) {
        final int startPos = buffer.position();
        final SID sid = SID.read(buffer);
        // The length of the SID MUST be a multiple of 4.
        buffer.position(buffer.position() + ((buffer.position() - startPos) % 4));
        return sid;
    }
}
