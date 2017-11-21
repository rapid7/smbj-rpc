package com.rapid7.client.dcerpc.mslsad.objects;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
   NDR Conformant Array
 */

public class LSAPRSIDInformationArray implements Marshallable
{

    private List<LSAPRSIDInformation> lsaprsidInformations;

    public LSAPRSIDInformationArray(){
        lsaprsidInformations = new ArrayList<>();
    }

    public void addLSAPRSIDInformation(LSAPRSIDInformation SIDInfo){
        lsaprsidInformations.add(SIDInfo);
    }

    @Override public void marshalPreamble(PacketOutput out)
        throws IOException
    {
        out.writeInt(lsaprsidInformations.size());
    }

    @Override public void marshalEntity(PacketOutput out)
        throws IOException
    {
        for (LSAPRSIDInformation lsaprsidInformation: lsaprsidInformations) {
            if (lsaprsidInformation != null) out.writeReferentID();
            else out.writeNull();
        }
    }

    @Override public void marshalDeferrals(PacketOutput out)
        throws IOException
    {
        for (LSAPRSIDInformation lsaprsidInformation: lsaprsidInformations) {
            out.writeMarshallable(lsaprsidInformation);
        }
    }
}
