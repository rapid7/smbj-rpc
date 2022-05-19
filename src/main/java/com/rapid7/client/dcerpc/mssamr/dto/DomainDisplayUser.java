package com.rapid7.client.dcerpc.mssamr.dto;

/**
 * This class represents domain display information for a user.
 */
public class DomainDisplayUser extends DomainDisplay {
	private final int accountControl;

	public DomainDisplayUser(final long relativeID, final String name, final String comment, int accountControl) {
		super(relativeID, name, comment);
		this.accountControl = accountControl;
	}
	public int getAccountControl() { return accountControl; }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + accountControl;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainDisplayUser other = (DomainDisplayUser) obj;
		if (accountControl != other.accountControl)
			return false;
		return true;
	}
	@Override
	public String toString() {
		final String nameStr = (getName() != null) ? String.format("\"%s\"", getName()) : "null";
		final String commentStr = (getComment() != null) ? String.format("\"%s\"", getComment()) : "null";
		return String.format(
				"DomainDisplayUser{relativeID: %d, name: %s, comment: %s, accountControl: %d}",
				getRelativeID(), nameStr, commentStr, getAccountControl()
		);
	}
}
