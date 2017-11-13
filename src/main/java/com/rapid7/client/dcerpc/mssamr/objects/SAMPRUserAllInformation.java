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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCShortBlob;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <b>Alignment: 8</b><pre>
 *     OLD_LARGE_INTEGER LastLogon;: 8
 *     OLD_LARGE_INTEGER LastLogoff;: 8
 *     OLD_LARGE_INTEGER PasswordLastSet;: 8
 *     OLD_LARGE_INTEGER AccountExpires;: 8
 *     OLD_LARGE_INTEGER PasswordCanChange;: 8
 *     OLD_LARGE_INTEGER PasswordMustChange;: 8
 *     RPC_UNICODE_STRING UserName;: 4
 *     RPC_UNICODE_STRING FullName;: 4
 *     RPC_UNICODE_STRING HomeDirectory;: 4
 *     RPC_UNICODE_STRING HomeDirectoryDrive;: 4
 *     RPC_UNICODE_STRING ScriptPath;: 4
 *     RPC_UNICODE_STRING ProfilePath;: 4
 *     RPC_UNICODE_STRING AdminComment;: 4
 *     RPC_UNICODE_STRING WorkStations;: 4
 *     RPC_UNICODE_STRING UserComment;: 4
 *     RPC_UNICODE_STRING Parameters;: 4
 *     RPC_SHORT_BLOB LmOwfPassword;: 4
 *     RPC_SHORT_BLOB NtOwfPassword;: 4
 *     RPC_UNICODE_STRING PrivateData;: 4
 *     SAMPR_SR_SECURITY_DESCRIPTOR SecurityDescriptor;: 4
 *     unsigned long UserId;: 4
 *     unsigned long PrimaryGroupId;: 4
 *     unsigned long UserAccountControl; 4
 *     unsigned long WhichFields; 4
 *     SAMPR_LOGON_HOURS LogonHours;: 4
 *     unsigned short BadPasswordCount;: 2
 *     unsigned short LogonCount;: 2
 *     unsigned short CountryCode;: 2
 *     unsigned short CodePage;: 2
 *     unsigned char LmPasswordPresent;: 1
 *     unsigned char NtPasswordPresent;: 1
 *     unsigned char PasswordExpired;: 1
 *     unsigned char PrivateDataSensitive;: 1</pre>
 * <a href="https://msdn.microsoft.com/en-us/library/cc245622.aspx">SAMPR_USER_ALL_INFORMATION</a>
 * <blockquote><pre>The SAMPR_USER_ALL_INFORMATION structure contains user attribute information. Most fields are described in section 2.2.7.1. The exceptions are described below.
 *      typedef struct _SAMPR_USER_ALL_INFORMATION {
 *          OLD_LARGE_INTEGER LastLogon;
 *          OLD_LARGE_INTEGER LastLogoff;
 *          OLD_LARGE_INTEGER PasswordLastSet;
 *          OLD_LARGE_INTEGER AccountExpires;
 *          OLD_LARGE_INTEGER PasswordCanChange;
 *          OLD_LARGE_INTEGER PasswordMustChange;
 *          RPC_UNICODE_STRING UserName;
 *          RPC_UNICODE_STRING FullName;
 *          RPC_UNICODE_STRING HomeDirectory;
 *          RPC_UNICODE_STRING HomeDirectoryDrive;
 *          RPC_UNICODE_STRING ScriptPath;
 *          RPC_UNICODE_STRING ProfilePath;
 *          RPC_UNICODE_STRING AdminComment;
 *          RPC_UNICODE_STRING WorkStations;
 *          RPC_UNICODE_STRING UserComment;
 *          RPC_UNICODE_STRING Parameters;
 *          RPC_SHORT_BLOB LmOwfPassword;
 *          RPC_SHORT_BLOB NtOwfPassword;
 *          RPC_UNICODE_STRING PrivateData;
 *          SAMPR_SR_SECURITY_DESCRIPTOR SecurityDescriptor;
 *          unsigned long UserId;
 *          unsigned long PrimaryGroupId;
 *          unsigned long UserAccountControl;
 *          unsigned long WhichFields;
 *          SAMPR_LOGON_HOURS LogonHours;
 *          unsigned short BadPasswordCount;
 *          unsigned short LogonCount;
 *          unsigned short CountryCode;
 *          unsigned short CodePage;
 *          unsigned char LmPasswordPresent;
 *          unsigned char NtPasswordPresent;
 *          unsigned char PasswordExpired;
 *          unsigned char PrivateDataSensitive;
 *      } SAMPR_USER_ALL_INFORMATION,
 *      *PSAMPR_USER_ALL_INFORMATION;
 *  LmOwfPassword: An RPC_SHORT_BLOB structure where Length and MaximumLength MUST be 16, and the Buffer MUST be formatted with an ENCRYPTED_LM_OWF_PASSWORD structure with the cleartext value being an LM hash, and the encryption key being the 16-byte SMB session key obtained as specified in either section 3.1.2.3 or section 3.2.2.3.
 *  NtOwfPassword: An RPC_SHORT_BLOB structure where Length and MaximumLength MUST be 16, and the Buffer MUST be formatted with an ENCRYPTED_NT_OWF_PASSWORD structure with the cleartext value being an NT hash, and the encryption key being the 16-byte SMB session key obtained as specified in either section 3.1.2.3 or section 3.2.2.3.
 *  PrivateData: Not used. Ignored on receipt at the server and client. Clients MUST set to zero when sent, and servers MUST set to zero on return.
 *  SecurityDescriptor: Not used. Ignored on receipt at the server and client. Clients MUST set to zero when sent, and servers MUST set to zero on return.
 *  WhichFields: A 32-bit bit field indicating which fields within the SAMPR_USER_ALL_INFORMATION structure will be processed by the server. Section 2.2.1.8 specifies the valid bits and also specifies the structure field to which each bit corresponds.
 *      Note If a given bit is set, the associated field MUST be processed; if a given bit is not set, then the associated field MUST be ignored.
 *  LmPasswordPresent: If zero, LmOwfPassword MUST be ignored; otherwise, LmOwfPassword MUST be processed.
 *  NtPasswordPresent: If zero, NtOwfPassword MUST be ignored; otherwise, NtOwfPassword MUST be processed.
 *  PrivateDataSensitive: Not used. Ignored on receipt at the server and client.</pre></blockquote>
 */
public class SAMPRUserAllInformation implements Unmarshallable {
    private long lastLogon;
    private long lastLogoff;
    private long passwordLastSet;
    private long accountExpires;
    private long passwordCanChange;
    private long passwordMustChange;
    private RPCUnicodeString.NonNullTerminated userName;
    private RPCUnicodeString.NonNullTerminated fullName;
    private RPCUnicodeString.NonNullTerminated homeDirectory;
    private RPCUnicodeString.NonNullTerminated homeDirectoryDrive;
    private RPCUnicodeString.NonNullTerminated scriptPath;
    private RPCUnicodeString.NonNullTerminated profilePath;
    private RPCUnicodeString.NonNullTerminated adminComment;
    private RPCUnicodeString.NonNullTerminated workStations;
    private RPCUnicodeString.NonNullTerminated userComment;
    private RPCUnicodeString.NonNullTerminated parameters;
    private RPCShortBlob lmOwfPassword;
    private RPCShortBlob ntOwfPassword;
    private RPCUnicodeString.NonNullTerminated privateData;
    private SAMPRSRSecurityDescriptor securityDescriptor;
    private int userId;
    private int primaryGroupId;
    private int userAccountControl;
    private int whichFields;
    private SAMPRLogonHours logonHours;
    private short badPasswordCount;
    private short logonCount;
    private short countryCode;
    private short codePage;
    private boolean lmPasswordPresent;
    private boolean ntPasswordPresent;
    private boolean passwordExpired;
    private boolean privateDataSensitive;

    public long getLastLogon() {
        return lastLogon;
    }

    public void setLastLogon(long lastLogon) {
        this.lastLogon = lastLogon;
    }

    public long getLastLogoff() {
        return lastLogoff;
    }

    public void setLastLogoff(long lastLogoff) {
        this.lastLogoff = lastLogoff;
    }

    public long getPasswordLastSet() {
        return passwordLastSet;
    }

    public void setPasswordLastSet(long passwordLastSet) {
        this.passwordLastSet = passwordLastSet;
    }

    public long getAccountExpires() {
        return accountExpires;
    }

    public void setAccountExpires(long accountExpires) {
        this.accountExpires = accountExpires;
    }

    public long getPasswordCanChange() {
        return passwordCanChange;
    }

    public void setPasswordCanChange(long passwordCanChange) {
        this.passwordCanChange = passwordCanChange;
    }

    public long getPasswordMustChange() {
        return passwordMustChange;
    }

    public void setPasswordMustChange(long passwordMustChange) {
        this.passwordMustChange = passwordMustChange;
    }

    public RPCUnicodeString.NonNullTerminated getUserName() {
        return userName;
    }

    public void setUserName(RPCUnicodeString.NonNullTerminated userName) {
        this.userName = userName;
    }

    public RPCUnicodeString.NonNullTerminated getFullName() {
        return fullName;
    }

    public void setFullName(RPCUnicodeString.NonNullTerminated fullName) {
        this.fullName = fullName;
    }

    public RPCUnicodeString.NonNullTerminated getHomeDirectory() {
        return homeDirectory;
    }

    public void setHomeDirectory(RPCUnicodeString.NonNullTerminated homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public RPCUnicodeString.NonNullTerminated getHomeDirectoryDrive() {
        return homeDirectoryDrive;
    }

    public void setHomeDirectoryDrive(RPCUnicodeString.NonNullTerminated homeDirectoryDrive) {
        this.homeDirectoryDrive = homeDirectoryDrive;
    }

    public RPCUnicodeString.NonNullTerminated getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(RPCUnicodeString.NonNullTerminated scriptPath) {
        this.scriptPath = scriptPath;
    }

    public RPCUnicodeString.NonNullTerminated getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(RPCUnicodeString.NonNullTerminated profilePath) {
        this.profilePath = profilePath;
    }

    public RPCUnicodeString.NonNullTerminated getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(RPCUnicodeString.NonNullTerminated adminComment) {
        this.adminComment = adminComment;
    }

    public RPCUnicodeString.NonNullTerminated getWorkStations() {
        return workStations;
    }

    public void setWorkStations(RPCUnicodeString.NonNullTerminated workStations) {
        this.workStations = workStations;
    }

    public RPCUnicodeString.NonNullTerminated getUserComment() {
        return userComment;
    }

    public void setUserComment(RPCUnicodeString.NonNullTerminated userComment) {
        this.userComment = userComment;
    }

    public RPCUnicodeString.NonNullTerminated getParameters() {
        return parameters;
    }

    public void setParameters(RPCUnicodeString.NonNullTerminated parameters) {
        this.parameters = parameters;
    }

    public RPCShortBlob getLmOwfPassword() {
        return lmOwfPassword;
    }

    public void setLmOwfPassword(RPCShortBlob lmOwfPassword) {
        this.lmOwfPassword = lmOwfPassword;
    }

    public RPCShortBlob getNtOwfPassword() {
        return ntOwfPassword;
    }

    public void setNtOwfPassword(RPCShortBlob ntOwfPassword) {
        this.ntOwfPassword = ntOwfPassword;
    }

    public RPCUnicodeString.NonNullTerminated getPrivateData() {
        return privateData;
    }

    public void setPrivateData(RPCUnicodeString.NonNullTerminated privateData) {
        this.privateData = privateData;
    }

    public SAMPRSRSecurityDescriptor getSecurityDescriptor() {
        return securityDescriptor;
    }

    public void setSecurityDescriptor(SAMPRSRSecurityDescriptor securityDescriptor) {
        this.securityDescriptor = securityDescriptor;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPrimaryGroupId() {
        return primaryGroupId;
    }

    public void setPrimaryGroupId(int primaryGroupId) {
        this.primaryGroupId = primaryGroupId;
    }

    public int getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(int userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public int getWhichFields() {
        return whichFields;
    }

    public void setWhichFields(int whichFields) {
        this.whichFields = whichFields;
    }

    public SAMPRLogonHours getLogonHours() {
        return logonHours;
    }

    public void setLogonHours(SAMPRLogonHours logonHours) {
        this.logonHours = logonHours;
    }

    public short getBadPasswordCount() {
        return badPasswordCount;
    }

    public void setBadPasswordCount(short badPasswordCount) {
        this.badPasswordCount = badPasswordCount;
    }

    public short getLogonCount() {
        return logonCount;
    }

    public void setLogonCount(short logonCount) {
        this.logonCount = logonCount;
    }

    public short getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(short countryCode) {
        this.countryCode = countryCode;
    }

    public short getCodePage() {
        return codePage;
    }

    public void setCodePage(short codePage) {
        this.codePage = codePage;
    }

    public boolean isLmPasswordPresent() {
        return lmPasswordPresent;
    }

    public void setLmPasswordPresent(boolean lmPasswordPresent) {
        this.lmPasswordPresent = lmPasswordPresent;
    }

    public boolean isNtPasswordPresent() {
        return ntPasswordPresent;
    }

    public void setNtPasswordPresent(boolean ntPasswordPresent) {
        this.ntPasswordPresent = ntPasswordPresent;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public boolean isPrivateDataSensitive() {
        return privateDataSensitive;
    }

    public void setPrivateDataSensitive(boolean privateDataSensitive) {
        this.privateDataSensitive = privateDataSensitive;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        userName = new RPCUnicodeString.NonNullTerminated();
        userName.unmarshalPreamble(in);
        fullName = new RPCUnicodeString.NonNullTerminated();
        fullName.unmarshalPreamble(in);
        homeDirectory = new RPCUnicodeString.NonNullTerminated();
        homeDirectory.unmarshalPreamble(in);
        homeDirectoryDrive = new RPCUnicodeString.NonNullTerminated();
        homeDirectoryDrive.unmarshalPreamble(in);
        scriptPath = new RPCUnicodeString.NonNullTerminated();
        scriptPath.unmarshalPreamble(in);
        profilePath = new RPCUnicodeString.NonNullTerminated();
        profilePath.unmarshalPreamble(in);
        adminComment = new RPCUnicodeString.NonNullTerminated();
        adminComment.unmarshalPreamble(in);
        workStations = new RPCUnicodeString.NonNullTerminated();
        workStations.unmarshalPreamble(in);
        userComment = new RPCUnicodeString.NonNullTerminated();
        userComment.unmarshalPreamble(in);
        parameters = new RPCUnicodeString.NonNullTerminated();
        parameters.unmarshalPreamble(in);
        lmOwfPassword = new RPCShortBlob();
        lmOwfPassword.unmarshalPreamble(in);
        ntOwfPassword = new RPCShortBlob();
        ntOwfPassword.unmarshalPreamble(in);
        privateData = new RPCUnicodeString.NonNullTerminated();
        privateData.unmarshalPreamble(in);
        securityDescriptor = new SAMPRSRSecurityDescriptor();
        securityDescriptor.unmarshalPreamble(in);
        logonHours = new SAMPRLogonHours();
        logonHours.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.EIGHT);
        // TODO
        lastLogon = in.readLong();
        // TODO
        lastLogoff = in.readLong();
        // TODO
        passwordLastSet = in.readLong();
        // TODO
        accountExpires = in.readLong();
        // TODO
        passwordCanChange = in.readLong();
        // TODO
        passwordMustChange = in.readLong();
        userName.unmarshalEntity(in);
        fullName.unmarshalEntity(in);
        homeDirectory.unmarshalEntity(in);
        homeDirectoryDrive.unmarshalEntity(in);
        scriptPath.unmarshalEntity(in);
        profilePath.unmarshalEntity(in);
        adminComment.unmarshalEntity(in);
        workStations.unmarshalEntity(in);
        userComment.unmarshalEntity(in);
        parameters.unmarshalEntity(in);
        lmOwfPassword.unmarshalEntity(in);
        ntOwfPassword.unmarshalEntity(in);
        privateData.unmarshalEntity(in);
        securityDescriptor.unmarshalEntity(in);

        userId = in.readInt();
        primaryGroupId = in.readInt();
        userAccountControl = in.readInt();
        whichFields = in.readInt();
        logonHours.unmarshalEntity(in);
        in.align(Alignment.TWO);
        badPasswordCount = in.readShort();
        logonCount = in.readShort();
        countryCode = in.readShort();
        codePage = in.readShort();
        lmPasswordPresent = in.readBoolean();
        ntPasswordPresent = in.readBoolean();
        passwordExpired = in.readBoolean();
        privateDataSensitive = in.readBoolean();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        userName.unmarshalDeferrals(in);
        fullName.unmarshalDeferrals(in);
        homeDirectory.unmarshalDeferrals(in);
        homeDirectoryDrive.unmarshalDeferrals(in);
        scriptPath.unmarshalDeferrals(in);
        profilePath.unmarshalDeferrals(in);
        adminComment.unmarshalDeferrals(in);
        workStations.unmarshalDeferrals(in);
        userComment.unmarshalDeferrals(in);
        parameters.unmarshalDeferrals(in);
        lmOwfPassword.unmarshalDeferrals(in);
        ntOwfPassword.unmarshalDeferrals(in);
        securityDescriptor.unmarshalDeferrals(in);
        logonHours.unmarshalDeferrals(in);
    }
}
