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

package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.objects.GroupInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGroupGeneralInformation;

public abstract class SamrQueryInformationGroupResponse<T extends Unmarshallable> extends RequestResponse {
    private T groupInformation;

    public T getGroupInformation() {
        return groupInformation;
    }

    public abstract GroupInformationClass getGroupInformationClass();

    abstract T createGroupInformation();

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        if(packetIn.readReferentID() != 0) {
            final int infoLevel = packetIn.readUnsignedShort();
            if (infoLevel != getGroupInformationClass().getInfoLevel()) {
                throw new UnmarshalException(String.format(
                        "Incoming GROUP_INFORMATION_CLASS %d does not match expected: %d",
                        infoLevel, getGroupInformationClass().getInfoLevel()));
            }
            this.groupInformation = createGroupInformation();
            packetIn.readUnmarshallable(this.groupInformation);
        } else {
            this.groupInformation = null;
        }
    }

    public static class GroupGeneralInformation extends SamrQueryInformationGroupResponse<SAMPRGroupGeneralInformation> {
        @Override
        public GroupInformationClass getGroupInformationClass() {
            return GroupInformationClass.GROUP_GENERAL_INFORMATION;
        }

        @Override
        SAMPRGroupGeneralInformation createGroupInformation() {
            return new SAMPRGroupGeneralInformation();
        }
    }
}
