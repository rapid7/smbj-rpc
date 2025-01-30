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

package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**GROUP_ATTRIBUTE_INFORMATION
 * 
 * <blockquote><pre>
 * The GROUP_ATTRIBUTE_INFORMATION structure contains group fields.

     typedef struct _GROUP_ATTRIBUTE_INFORMATION {
       unsigned long Attributes;
     } GROUP_ATTRIBUTE_INFORMATION,
      *PGROUP_ATTRIBUTE_INFORMATION;

For information on each field, see section 2.2.5.1.
Is this page helpful?
</pre></blockquote>
@see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/cb80061b-7801-4082-bbe7-20d88b118eaa">GROUP_ATTRIBUTE_INFORMATION</a>
 */

public class SAMPRGroupAttributeInformation implements Unmarshallable, Marshallable {
	public SAMPRGroupAttributeInformation(SAMPRGroupGeneralInformation generalInformation) {
		attributes = generalInformation.getAttributes();
	}
    // <NDR: unsigned long> unsigned long Attributes;
    // This is a bit field so representing as an int is fine
    private int attributes;

    public int getAttributes() {
        return attributes;
    }

    public void setAttributes(int attributes) {
        this.attributes = attributes;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        attributes = in.readInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttributes());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof SAMPRGroupAttributeInformation)) {
            return false;
        }
        SAMPRGroupAttributeInformation other = (SAMPRGroupAttributeInformation) obj;
        return Objects.equals(getAttributes(), other.getAttributes());
    }

    @Override
    public String toString() {
        return String.format("SAMPR_GROUP_ATTRIBUTES_INFORMATION {Attributes:%d}",
                getAttributes());
    }

	@Override 
	public void marshalPreamble(PacketOutput out) throws IOException {
	}

	@Override
	public void marshalEntity(PacketOutput out) throws IOException {
        // <NDR: unsigned long> unsigned long Attributes;
        out.align(Alignment.FOUR);
        out.writeInt(attributes);
	}
	
	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException {
	}
}
