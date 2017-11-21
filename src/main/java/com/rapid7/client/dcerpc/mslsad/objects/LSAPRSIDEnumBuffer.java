package com.rapid7.client.dcerpc.mslsad.objects;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.objects.MalformedSIDException;
import com.rapid7.client.dcerpc.objects.RPCSID;
import java.io.IOException;

/**
 * <h1 class="title">2.2.18 LSAPR_SID_ENUM_BUFFER</h1>
 *
 * <p>The LSAPR_SID_ENUM_BUFFER structure defines a set of <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_83f2020d-0804-4840-a5ac-e06439d50f8d">SIDs</a>. This structure is
 * used during a translation request for a batch of SIDs to names.</p>
 *
 * <dl>
 * <dd>
 * <div><pre> typedef struct _LSAPR_SID_ENUM_BUFFER {
 *    [range(0,20480)] unsigned long Entries;
 *    [size_is(Entries)] PLSAPR_SID_INFORMATION SidInfo;
 *  } LSAPR_SID_ENUM_BUFFER,
 *   *PLSAPR_SID_ENUM_BUFFER;
 * </pre></div>
 * </dd></dl>
 *
 * <p><strong>Entries:</strong>  Contains the number of
 * translated SIDs.<a id="Appendix_A_Target_9"></a><a href="https://msdn.microsoft.com/en-us/library/cc234510.aspx#Appendix_A_9">&lt;9&gt;</a></p>
 *
 * <p><strong>SidInfo:</strong>  Contains a set of
 * structures that contain SIDs, as specified in section <a href="https://msdn.microsoft.com/en-us/library/cc234459.aspx">2.2.17</a>. If the <strong>Entries</strong>
 * field in this structure is not 0, this field MUST be non-NULL. If <strong>Entries</strong>
 * is 0, this field MUST be ignored.</p>
 */

public class LSAPRSIDEnumBuffer implements Marshallable {
    private final int entries;
    private final LSAPRSIDInformationArray lsaprSIDInformationArray;

    public LSAPRSIDEnumBuffer(String ...SIDs)
        throws MalformedSIDException
    {
        lsaprSIDInformationArray = new LSAPRSIDInformationArray();
        for (String SID: SIDs) {
            LSAPRSIDInformation lsaprSIDInformation = new LSAPRSIDInformation(new RPCSID().fromString(SID));
            lsaprSIDInformationArray.addLSAPRSIDInformation(lsaprSIDInformation);
        }
        entries = SIDs.length;
    }

    @Override public void marshalPreamble(PacketOutput out)
        throws IOException
    {

    }

    @Override public void marshalEntity(PacketOutput out)
        throws IOException
    {
        out.writeInt(entries);
        if (lsaprSIDInformationArray != null) out.writeReferentID();
        else out.writeNull();
    }

    @Override public void marshalDeferrals(PacketOutput out)
        throws IOException
    {
        out.writeMarshallable(lsaprSIDInformationArray);
    }
}
