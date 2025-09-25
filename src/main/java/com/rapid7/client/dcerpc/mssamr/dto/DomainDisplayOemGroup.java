package com.rapid7.client.dcerpc.mssamr.dto;

/**
 * This class represents domain display information for a oemGroup.
 */
public class DomainDisplayOemGroup {
    private final String oemAccountName;

    public DomainDisplayOemGroup(String oemAccountName) {
        this.oemAccountName = oemAccountName;
    }

    public String getOemAccountName() {
        return oemAccountName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((oemAccountName == null) ? 0 : oemAccountName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DomainDisplayOemGroup other = (DomainDisplayOemGroup) obj;
        if (oemAccountName == null) {
            if (other.oemAccountName != null)
                return false;
        } else if (!oemAccountName.equals(other.oemAccountName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DomainDisplayOemGroup [oemAccountName=" + oemAccountName + "]";
    }
}
