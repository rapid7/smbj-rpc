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

package com.rapid7.client.dcerpc.mslsad.objects;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**

 */
public class LSAPRTranslatedSIDs implements Unmarshallable
{
    private LSAPRTranslatedSID[] lsaprTranslatedSIDArray;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        int entries = in.readInt();
        if (in.readReferentID() != 0) {
            lsaprTranslatedSIDArray = new LSAPRTranslatedSID[entries];
        } else lsaprTranslatedSIDArray = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (lsaprTranslatedSIDArray != null) {
            in.align(Alignment.FOUR);
            in.readInt(); //count
            
            for (int i = 0; i < lsaprTranslatedSIDArray.length; i++){
                LSAPRTranslatedSID lsaprTranslatedSID = new LSAPRTranslatedSID();
                in.readUnmarshallable(lsaprTranslatedSID);
                lsaprTranslatedSIDArray[i] = lsaprTranslatedSID;
            }
        }
    }

    public LSAPRTranslatedSID[] getLsaprTranslatedSIDArray() {
        return lsaprTranslatedSIDArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LSAPRTranslatedSIDs that = (LSAPRTranslatedSIDs) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(lsaprTranslatedSIDArray, that.lsaprTranslatedSIDArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(lsaprTranslatedSIDArray);
    }

    @Override
    public String toString() {
        return "LSAPRTranslatedSIDs{" +
                "lsaprTranslatedSIDArray=" + Arrays.toString(lsaprTranslatedSIDArray) +
                '}';
    }
}
