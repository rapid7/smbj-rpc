package com.rapid7.client.dcerpc.mssamr.objects;

import java.math.BigInteger;

/**
 * Password Properties A 32-bit bit field indicating the password properties policy setting. The defined bits are shown in the following table. All bits can be combined using a logical OR in any combination. Undefined bits SHOULD be persisted by the server (that is, stored in its database) and returned to future queries. Clients SHOULD ignore undefined bits.
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/d275ab19-10b0-40e0-94bb-45b7fc130025">Domain Field => PasswordProperties</a>
 */
public final class PasswordProperties {
	private PasswordProperties() {}
	
	public static final byte DOMAIN_PASSWORD_COMPLEX = (byte) 0x00000001; // The server enforces password complexity policy.
	public static final byte DOMAIN_PASSWORD_NO_ANON_CHANGE = (byte) 0x00000002; // Reserved. No effect on password policy.
	public static final byte DOMAIN_PASSWORD_NO_CLEAR_CHANGE = (byte) 0x00000004; // Change-password methods that provide the cleartext password are disabled by the server.
	public static final byte DOMAIN_PASSWORD_LOCKOUT_ADMINS = (byte) 0x00000008; // Reserved. No effect on password policy.
	public static final byte DOMAIN_PASSWORD_STORE_CLEARTEXT = (byte) 0x00000010; // The server MUST store the cleartext password, not just the computed hashes.
	public static final byte DOMAIN_PASSWORD_REFUSE_PASSWORD_CHANGE = (byte) 0x00000020; // Reserved. No effect on password policy.

	public static String toString(int pp) {  //NOSONAR
		String output = "";		
		if (BigInteger.valueOf(pp).testBit(0))
			output += "DOMAIN_PASSWORD_COMPLEX, ";
		if (BigInteger.valueOf(pp).testBit(1))
			output += "DOMAIN_PASSWORD_NO_ANON_CHANGE, ";
		if (BigInteger.valueOf(pp).testBit(2))
			output += "DOMAIN_PASSWORD_NO_CLEAR_CHANGE, ";
		if (BigInteger.valueOf(pp).testBit(3))
			output += "DOMAIN_PASSWORD_LOCKOUT_ADMINS, ";
		if (BigInteger.valueOf(pp).testBit(4))
			output += "DOMAIN_PASSWORD_STORE_CLEARTEXT, ";
		if (BigInteger.valueOf(pp).testBit(5))
			output += "DOMAIN_PASSWORD_REFUSE_PASSWORD_CHANGE, ";
		if (output.equals(""))
			output = "NONE";

	    if (output.length() > 1 && output.charAt(output.length() - 2) == ',') {
	    	output = output.substring(0, output.length() - 2);
	    }
	    
		return output;
	}
}
