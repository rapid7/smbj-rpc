package com.rapid7.client.dcerpc.mslsad.objects;

public class LsaTranslatedSid
{
    private int use;
    private int relativeId;
    private int domainIndex;

    public LsaTranslatedSid(int use, int relativeId, int domainIndex) {
        this.use = use;
        this.relativeId = relativeId;
        this.domainIndex = domainIndex;
    }

    public int getUse() {
        return use;
    }

    public int getRelativeId() {
        return relativeId;
    }

    public int getDomainIndex() {
        return domainIndex;
    }
}
