package com.rapid7.client.dcerpc.mssamr.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import java.io.IOException;

/**
 * <a href="https://learn.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/acb3204a-da8b-478e-9139-1ea589edb880">SamrUnicodeChangePasswordUser2</a>
 * <blockquote><pre>
 * The SamrUnicodeChangePasswordUser2 method changes a user's password.
 *
 * long SamrUnicodeChangePasswordUser2(
 *  [in] handle_t BindingHandle,
 *  [in, unique] PRPC_UNICODE_STRING ServerName,
 *  [in] PRPC_UNICODE_STRING UserName,
 *  [in, unique] PSAMPR_ENCRYPTED_USER_PASSWORD NewPasswordEncryptedWithOldNt,
 *  [in, unique] PENCRYPTED_NT_OWF_PASSWORD OldNtOwfPasswordEncryptedWithNewNt,
 *  [in] unsigned char LmPresent,
 *  [in, unique] PSAMPR_ENCRYPTED_USER_PASSWORD NewPasswordEncryptedWithOldLm,
 *  [in, unique] PENCRYPTED_LM_OWF_PASSWORD OldLmOwfPasswordEncryptedWithNewNt
 );
 * </pre></blockquote>
 */
public class SamrUnicodeChangePasswordUser2Request extends RequestCall<SamrUnicodeChangePasswordUser2Response> {

    public static final short OP_NUM = 55;
    private final byte[] domainHandle;
    private RPCUnicodeString.NonNullTerminated serverName;
    private RPCUnicodeString.NonNullTerminated userName;
    private byte[] newPasswordEncryptedWithOldNt;
    private byte[] oldNtOwfPasswordEncryptedWithNewNt;
    private int lmPresent;
    private byte[] newPasswordEncryptedWithOldLm;
    private byte[] oldLmOwfPasswordEncryptedWithNewNt;

    public SamrUnicodeChangePasswordUser2Request(byte[] domainHandle, RPCUnicodeString.NonNullTerminated serverName, RPCUnicodeString.NonNullTerminated userName, byte[] newPasswordEncryptedWithOldNt, byte[] oldNtOwfPasswordEncryptedWithNewNt,  byte[] newPasswordEncryptedWithOldLm, byte[] oldLmOwfPasswordEncryptedWithNewNt) {
        super(OP_NUM);
        this.domainHandle = domainHandle;
        this.serverName = serverName;
        this.userName = userName;
        this.newPasswordEncryptedWithOldNt = newPasswordEncryptedWithOldNt;
        this.oldNtOwfPasswordEncryptedWithNewNt = oldNtOwfPasswordEncryptedWithNewNt;
        this.newPasswordEncryptedWithOldLm = newPasswordEncryptedWithOldLm;
        this.oldLmOwfPasswordEncryptedWithNewNt = oldLmOwfPasswordEncryptedWithNewNt;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        lmPresent = 0;
        packetOut.write(this.domainHandle);
        packetOut.writeMarshallable(this.serverName);
        packetOut.writeMarshallable(this.userName);
        packetOut.write(this.newPasswordEncryptedWithOldNt);
        packetOut.write(this.oldNtOwfPasswordEncryptedWithNewNt);
        packetOut.writeInt(this.lmPresent);
        packetOut.write(this.newPasswordEncryptedWithOldLm);
        packetOut.write(this.oldLmOwfPasswordEncryptedWithNewNt);
    }

    @Override
    public SamrUnicodeChangePasswordUser2Response getResponseObject() {
        return new SamrUnicodeChangePasswordUser2Response();
    }
}
