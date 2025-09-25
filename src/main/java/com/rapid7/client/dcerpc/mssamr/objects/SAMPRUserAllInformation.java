/**
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
 */

package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.mssamr.dto.UserInformation;
import com.rapid7.client.dcerpc.objects.RPCShortBlob;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <b>Alignment: 8</b>
 * 
 * <pre>
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
 *     unsigned char PrivateDataSensitive;: 1
 * </pre>
 *
 * <a href=
 * "https://msdn.microsoft.com/en-us/library/cc245622.aspx">SAMPR_USER_ALL_INFORMATION</a>
 * <blockquote>
 * 
 * <pre>
 * The SAMPR_USER_ALL_INFORMATION structure contains user attribute information. Most fields are described in section 2.2.7.1. The exceptions are described below.
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
 *  PrivateDataSensitive: Not used. Ignored on receipt at the server and client.
 * </pre>
 * 
 * </blockquote>
 */
public class SAMPRUserAllInformation implements Unmarshallable, Marshallable, UserInformation {
    UserInformationClass userInformationClass = UserInformationClass.USER_ALL_INFORMATION;
    // <NDR: hyper> OLD_LARGE_INTEGER LastLogon;
    private long lastLogon;
    // <NDR: hyper> OLD_LARGE_INTEGER LastLogoff;
    private long lastLogoff;
    // <NDR: hyper> OLD_LARGE_INTEGER PasswordLastSet;
    private long passwordLastSet;
    // <NDR: hyper> OLD_LARGE_INTEGER AccountExpires;
    private long accountExpires;
    // <NDR: hyper> OLD_LARGE_INTEGER PasswordCanChange;
    private long passwordCanChange;
    // <NDR: hyper> OLD_LARGE_INTEGER PasswordMustChange;
    private long passwordMustChange;
    // <NDR: struct> RPC_UNICODE_STRING UserName;
    private RPCUnicodeString.NonNullTerminated userName;
    // <NDR: struct> RPC_UNICODE_STRING FullName;
    private RPCUnicodeString.NonNullTerminated fullName;
    // <NDR: struct> RPC_UNICODE_STRING HomeDirectory;
    private RPCUnicodeString.NonNullTerminated homeDirectory;
    // <NDR: struct> RPC_UNICODE_STRING HomeDirectoryDrive;
    private RPCUnicodeString.NonNullTerminated homeDirectoryDrive;
    // <NDR: struct> RPC_UNICODE_STRING ScriptPath;
    private RPCUnicodeString.NonNullTerminated scriptPath;
    // <NDR: struct> RPC_UNICODE_STRING ProfilePath;
    private RPCUnicodeString.NonNullTerminated profilePath;
    // <NDR: struct> RPC_UNICODE_STRING AdminComment;
    private RPCUnicodeString.NonNullTerminated adminComment;
    // <NDR: struct> RPC_UNICODE_STRING WorkStations;
    private RPCUnicodeString.NonNullTerminated workStations;
    // <NDR: struct> RPC_UNICODE_STRING UserComment;
    private RPCUnicodeString.NonNullTerminated userComment;
    // <NDR: struct> RPC_UNICODE_STRING Parameters;
    private RPCUnicodeString.NonNullTerminated parameters;
    // <NDR: struct> RPC_SHORT_BLOB LmOwfPassword;
    private RPCShortBlob lmOwfPassword;
    // <NDR: struct> RPC_SHORT_BLOB NtOwfPassword;
    private RPCShortBlob ntOwfPassword;
    // <NDR: struct> RPC_UNICODE_STRING PrivateData;
    private RPCUnicodeString.NonNullTerminated privateData;
    // <NDR: struct> SAMPR_SR_SECURITY_DESCRIPTOR SecurityDescriptor;
    private SAMPRSRSecurityDescriptor securityDescriptor;
    // <NDR: unsigned long> unsigned long UserId;
    private long rid;
    // <NDR: unsigned long> unsigned long PrimaryGroupId;
    private long primaryGroupId;
    // <NDR: unsigned long> unsigned long UserAccountControl;
    private long userAccountControl;
    // <NDR: unsigned long> unsigned long WhichFields;
    private long whichFields;
    // <NDR: struct> SAMPR_LOGON_HOURS LogonHours;
    private SAMPRLogonHours logonHours;
    // <NDR: unsigned short> unsigned short BadPasswordCount;
    private int badPasswordCount;
    // <NDR: unsigned short> unsigned short LogonCount;
    private int logonCount;
    // <NDR: unsigned short> unsigned short CountryCode;
    private int countryCode;
    // <NDR: unsigned short> unsigned short CodePage;
    private int codePage;
    // <NDR: boolean> unsigned char LmPasswordPresent;
    private char lmPasswordPresent;
    // <NDR: boolean> unsigned char NtPasswordPresent;
    private char ntPasswordPresent;
    // <NDR: boolean> unsigned char PasswordExpired;
    private char passwordExpired;
    // <NDR: boolean> unsigned char PrivateDataSensitive;
    private char privateDataSensitive;

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

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public long getPrimaryGroupId() {
        return primaryGroupId;
    }

    public void setPrimaryGroupId(long primaryGroupId) {
        this.primaryGroupId = primaryGroupId;
    }

    public long getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(long userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public long getWhichFields() {
        return whichFields;
    }

    public void setWhichFields(long whichFields) {
        this.whichFields = whichFields;
    }

    public SAMPRLogonHours getLogonHours() {
        return logonHours;
    }

    public void setLogonHours(SAMPRLogonHours logonHours) {
        this.logonHours = logonHours;
    }

    public int getBadPasswordCount() {
        return badPasswordCount;
    }

    public void setBadPasswordCount(int badPasswordCount) {
        this.badPasswordCount = badPasswordCount;
    }

    public int getLogonCount() {
        return logonCount;
    }

    public void setLogonCount(int logonCount) {
        this.logonCount = logonCount;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getCodePage() {
        return codePage;
    }

    public void setCodePage(int codePage) {
        this.codePage = codePage;
    }

    public char getLmPasswordPresent() {
        return lmPasswordPresent;
    }

    public void setLmPasswordPresent(char lmPasswordPresent) {
        this.lmPasswordPresent = lmPasswordPresent;
    }

    public char getNtPasswordPresent() {
        return ntPasswordPresent;
    }

    public void setNtPasswordPresent(char ntPasswordPresent) {
        this.ntPasswordPresent = ntPasswordPresent;
    }

    public char getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(char passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public char getPrivateDataSensitive() {
        return privateDataSensitive;
    }

    public void setPrivateDataSensitive(char privateDataSensitive) {
        this.privateDataSensitive = privateDataSensitive;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // <NDR struct> RPC_UNICODE_STRING UserName;
        userName = new RPCUnicodeString.NonNullTerminated();
        userName.unmarshalPreamble(in);
        // <NDR struct> RPC_UNICODE_STRING FullName;
        fullName = new RPCUnicodeString.NonNullTerminated();
        fullName.unmarshalPreamble(in);
        // <NDR struct> RPC_UNICODE_STRING HomeDirectory;
        homeDirectory = new RPCUnicodeString.NonNullTerminated();
        homeDirectory.unmarshalPreamble(in);
        // <NDR struct> RPC_UNICODE_STRING HomeDirectoryDrive;
        homeDirectoryDrive = new RPCUnicodeString.NonNullTerminated();
        homeDirectoryDrive.unmarshalPreamble(in);
        // <NDR struct> RPC_UNICODE_STRING ScriptPath;
        scriptPath = new RPCUnicodeString.NonNullTerminated();
        scriptPath.unmarshalPreamble(in);
        // <NDR struct> RPC_UNICODE_STRING ProfilePath;
        profilePath = new RPCUnicodeString.NonNullTerminated();
        profilePath.unmarshalPreamble(in);
        // <NDR struct> RPC_UNICODE_STRING AdminComment;
        adminComment = new RPCUnicodeString.NonNullTerminated();
        adminComment.unmarshalPreamble(in);
        // <NDR struct> RPC_UNICODE_STRING WorkStations;
        workStations = new RPCUnicodeString.NonNullTerminated();
        workStations.unmarshalPreamble(in);
        // <NDR struct> RPC_UNICODE_STRING UserComment;
        userComment = new RPCUnicodeString.NonNullTerminated();
        userComment.unmarshalPreamble(in);
        // <NDR struct> RPC_UNICODE_STRING Parameters;
        parameters = new RPCUnicodeString.NonNullTerminated();
        parameters.unmarshalPreamble(in);
        // <NDR: struct> RPC_SHORT_BLOB LmOwfPassword;
        lmOwfPassword = new RPCShortBlob();
        lmOwfPassword.unmarshalPreamble(in);
        // <NDR: struct> RPC_SHORT_BLOB NtOwfPassword;
        ntOwfPassword = new RPCShortBlob();
        ntOwfPassword.unmarshalPreamble(in);
        // <NDR: struct> RPC_UNICODE_STRING PrivateData;
        privateData = new RPCUnicodeString.NonNullTerminated();
        privateData.unmarshalPreamble(in);
        // <NDR: struct> SAMPR_SR_SECURITY_DESCRIPTOR SecurityDescriptor;
        securityDescriptor = new SAMPRSRSecurityDescriptor();
        securityDescriptor.unmarshalPreamble(in);
        // <NDR: struct> SAMPR_LOGON_HOURS LogonHours;
        logonHours = new SAMPRLogonHours();
        logonHours.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.EIGHT);
        // <NDR: hyper> OLD_LARGE_INTEGER LastLogon;
        // Alignment: 8 - Already aligned
        lastLogon = in.readLong();
        // <NDR: hyper> OLD_LARGE_INTEGER LastLogoff;
        // Alignment: 8 - Already aligned
        lastLogoff = in.readLong();
        // <NDR: hyper> OLD_LARGE_INTEGER PasswordLastSet;
        // Alignment: 8 - Already aligned
        passwordLastSet = in.readLong();
        // <NDR: hyper> OLD_LARGE_INTEGER AccountExpires;
        // Alignment: 8 - Already aligned
        accountExpires = in.readLong();
        // <NDR: hyper> OLD_LARGE_INTEGER PasswordCanChange;
        // Alignment: 8 - Already aligned
        passwordCanChange = in.readLong();
        // <NDR: hyper> OLD_LARGE_INTEGER PasswordMustChange;
        // Alignment: 8 - Already aligned;
        passwordMustChange = in.readLong();
        // <NDR: struct> RPC_UNICODE_STRING UserName;
        // Alignment: 4 - Already aligned
        userName.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING FullName;
        // Alignment: 4 - Already aligned
        fullName.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING HomeDirectory;
        // Alignment: 4 - Already aligned
        homeDirectory.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING HomeDirectoryDrive;
        // Alignment: 4 - Already aligned
        homeDirectoryDrive.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING ScriptPath;
        // Alignment: 4 - Already aligned
        scriptPath.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING ProfilePath;
        // Alignment: 4 - Already aligned
        profilePath.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING AdminComment;
        // Alignment: 4 - Already aligned
        adminComment.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING WorkStations;
        // Alignment: 4 - Already aligned
        workStations.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING UserComment;
        // Alignment: 4 - Already aligned
        userComment.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING Parameters;
        // Alignment: 4 - Already aligned
        parameters.unmarshalEntity(in);
        // <NDR: struct> RPC_SHORT_BLOB LmOwfPassword;
        // Alignment: 4 - Already aligned
        lmOwfPassword.unmarshalEntity(in);
        // <NDR: struct> RPC_SHORT_BLOB NtOwfPassword;
        // Alignment: 4 - Already aligned
        ntOwfPassword.unmarshalEntity(in);
        // <NDR: struct> RPC_UNICODE_STRING PrivateData;
        // Alignment: 4 - Already aligned
        privateData.unmarshalEntity(in);
        // <NDR: struct> SAMPR_SR_SECURITY_DESCRIPTOR SecurityDescriptor;
        // Alignment: 4 - Already aligned
        securityDescriptor.unmarshalEntity(in);
        // <NDR: unsigned long> unsigned long UserId;
        // Alignment: 4 - Already aligned
        rid = in.readUnsignedInt();
        // <NDR: unsigned long> unsigned long PrimaryGroupId;
        // Alignment: 4 - Already aligned
        primaryGroupId = in.readUnsignedInt();
        // <NDR: unsigned long> unsigned long UserAccountControl;
        // Alignment: 4 - Already aligned
        userAccountControl = in.readUnsignedInt();
        // <NDR: unsigned long> unsigned long WhichFields;
        // Alignment: 4 - Already aligned
        whichFields = in.readUnsignedInt();
        // <NDR: struct> SAMPR_LOGON_HOURS LogonHours;
        // Alignment: 4 - Already aligned
        logonHours.unmarshalEntity(in);
        // <NDR: unsigned short> unsigned short BadPasswordCount;
        in.align(Alignment.TWO);
        badPasswordCount = in.readUnsignedShort();
        // <NDR: unsigned short> unsigned short LogonCount;
        // Alignment: 2 - Already aligned
        logonCount = in.readUnsignedShort();
        // <NDR: unsigned short> unsigned short CountryCode;
        // Alignment: 2 - Already aligned
        countryCode = in.readUnsignedShort();
        // <NDR: unsigned short> unsigned short CodePage;
        // Alignment: 2 - Already aligned
        codePage = in.readUnsignedShort();
        // <NDR: unsigned char> unsigned char LmPasswordPresent;
        lmPasswordPresent = in.readUnsignedByte();
        // <NDR: unsigned char> unsigned char NtPasswordPresent;
        ntPasswordPresent = in.readUnsignedByte();
        // <NDR: unsigned char> unsigned char PasswordExpired;
        passwordExpired = in.readUnsignedByte();
        // <NDR: unsigned char> unsigned char PrivateDataSensitive;
        privateDataSensitive = in.readUnsignedByte();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING UserName;
        userName.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING FullName;
        fullName.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING HomeDirectory;
        homeDirectory.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING HomeDirectoryDrive;
        homeDirectoryDrive.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING ScriptPath;
        scriptPath.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING ProfilePath;
        profilePath.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING AdminComment;
        adminComment.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING WorkStations;
        workStations.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING UserComment;
        userComment.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING Parameters;
        parameters.unmarshalDeferrals(in);
        // <NDR: struct> RPC_SHORT_BLOB LmOwfPassword;
        lmOwfPassword.unmarshalDeferrals(in);
        // <NDR: struct> RPC_SHORT_BLOB NtOwfPassword;
        ntOwfPassword.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING PrivateData;
        privateData.unmarshalDeferrals(in);
        // <NDR: struct> SAMPR_SR_SECURITY_DESCRIPTOR SecurityDescriptor;
        securityDescriptor.unmarshalDeferrals(in);
        // <NDR: struct> SAMPR_LOGON_HOURS LogonHours;
        logonHours.unmarshalDeferrals(in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLastLogon(), getLastLogoff(), getPasswordLastSet(), getAccountExpires(),
            getPasswordCanChange(), getPasswordMustChange(), getUserName(), getFullName(), getHomeDirectory(),
            getHomeDirectoryDrive(), getScriptPath(), getProfilePath(), getAdminComment(), getWorkStations(),
            getUserComment(), getParameters(), getLmOwfPassword(), getNtOwfPassword(), getPrivateData(),
            getSecurityDescriptor(), getRid(), getPrimaryGroupId(), getUserAccountControl(), getWhichFields(),
            getLogonHours(), getBadPasswordCount(), getLogonCount(), getCountryCode(), getCodePage(),
            getLmPasswordPresent(), getNtPasswordPresent(), getPasswordExpired(), getPrivateDataSensitive());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SAMPRUserAllInformation)) {
            return false;
        }
        SAMPRUserAllInformation other = (SAMPRUserAllInformation) obj;
        return Objects.equals(getLastLogon(), other.getLastLogon())
            && Objects.equals(getLastLogoff(), other.getLastLogoff())
            && Objects.equals(getPasswordLastSet(), other.getPasswordLastSet())
            && Objects.equals(getAccountExpires(), other.getAccountExpires())
            && Objects.equals(getPasswordCanChange(), other.getPasswordCanChange())
            && Objects.equals(getPasswordMustChange(), other.getPasswordMustChange())
            && Objects.equals(getUserName(), other.getUserName()) && Objects.equals(getFullName(), other.getFullName())
            && Objects.equals(getHomeDirectory(), other.getHomeDirectory())
            && Objects.equals(getHomeDirectoryDrive(), other.getHomeDirectoryDrive())
            && Objects.equals(getScriptPath(), other.getScriptPath())
            && Objects.equals(getProfilePath(), other.getProfilePath())
            && Objects.equals(getAdminComment(), other.getAdminComment())
            && Objects.equals(getWorkStations(), other.getWorkStations())
            && Objects.equals(getUserComment(), other.getUserComment())
            && Objects.equals(getParameters(), other.getParameters())
            && Objects.equals(getLmOwfPassword(), other.getLmOwfPassword())
            && Objects.equals(getNtOwfPassword(), other.getNtOwfPassword())
            && Objects.equals(getPrivateData(), other.getPrivateData())
            && Objects.equals(getSecurityDescriptor(), other.getSecurityDescriptor())
            && Objects.equals(getRid(), other.getRid())
            && Objects.equals(getPrimaryGroupId(), other.getPrimaryGroupId())
            && Objects.equals(getUserAccountControl(), other.getUserAccountControl())
            && Objects.equals(getWhichFields(), other.getWhichFields())
            && Objects.equals(getLogonHours(), other.getLogonHours())
            && Objects.equals(getBadPasswordCount(), other.getBadPasswordCount())
            && Objects.equals(getLogonCount(), other.getLogonCount())
            && Objects.equals(getCountryCode(), other.getCountryCode())
            && Objects.equals(getCodePage(), other.getCodePage())
            && Objects.equals(getLmPasswordPresent(), other.getLmPasswordPresent())
            && Objects.equals(getNtPasswordPresent(), other.getNtPasswordPresent())
            && Objects.equals(getPasswordExpired(), other.getPasswordExpired())
            && Objects.equals(getPrivateDataSensitive(), other.getPrivateDataSensitive());
    }

    @Override
    public UserInformationClass getUserInformationClass() {
        return UserInformationClass.USER_ALL_INFORMATION;
    }

    @Override
    public String toString() {
        return String.format("SAMPR_USER_ALL_INFORMATION{UserId:%d, PrimaryGroupId:%d, UserName:%s, FullName:%s}",
            getRid(), getPrimaryGroupId(), getUserName(), getFullName());
    }

    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        // <NDR struct> RPC_UNICODE_STRING UserName;
        userName.marshalPreamble(out);
        // <NDR struct> RPC_UNICODE_STRING FullName;
        fullName.marshalPreamble(out);
        // <NDR struct> RPC_UNICODE_STRING HomeDirectory;
        homeDirectory.marshalPreamble(out);
        // <NDR struct> RPC_UNICODE_STRING HomeDirectoryDrive;
        homeDirectoryDrive.marshalPreamble(out);
        // <NDR struct> RPC_UNICODE_STRING ScriptPath;
        scriptPath.marshalPreamble(out);
        // <NDR struct> RPC_UNICODE_STRING ProfilePath;
        profilePath.marshalPreamble(out);
        // <NDR struct> RPC_UNICODE_STRING AdminComment;
        adminComment.marshalPreamble(out);
        // <NDR struct> RPC_UNICODE_STRING WorkStations;
        workStations.marshalPreamble(out);
        // <NDR struct> RPC_UNICODE_STRING UserComment;
        userComment.marshalPreamble(out);
        // <NDR struct> RPC_UNICODE_STRING Parameters;
        parameters.marshalPreamble(out);
        // <NDR: struct> RPC_SHORT_BLOB LmOwfPassword;
        lmOwfPassword.marshalPreamble(out);
        // <NDR: struct> RPC_SHORT_BLOB NtOwfPassword;
        ntOwfPassword.marshalPreamble(out);
        // <NDR: struct> RPC_UNICODE_STRING PrivateData;
        privateData.marshalPreamble(out);
        // <NDR: struct> SAMPR_SR_SECURITY_DESCRIPTOR SecurityDescriptor;
        securityDescriptor.marshalPreamble(out);
        // <NDR: struct> SAMPR_LOGON_HOURS LogonHours;
        logonHours.marshalPreamble(out);
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment: 4
        out.align(Alignment.EIGHT);
        // <NDR: hyper> OLD_LARGE_INTEGER LastLogon;
        // Alignment: 8 - Already aligned
        out.writeLong(lastLogon);
        // <NDR: hyper> OLD_LARGE_INTEGER LastLogoff;
        // Alignment: 8 - Already aligned
        out.writeLong(lastLogoff);
        // <NDR: hyper> OLD_LARGE_INTEGER PasswordLastSet;
        // Alignment: 8 - Already aligned
        out.writeLong(passwordLastSet);
        // <NDR: hyper> OLD_LARGE_INTEGER AccountExpires;
        // Alignment: 8 - Already aligned
        out.writeLong(accountExpires);
        // <NDR: hyper> OLD_LARGE_INTEGER PasswordCanChange;
        // Alignment: 8 - Already aligned
        out.writeLong(passwordCanChange);
        // <NDR: hyper> OLD_LARGE_INTEGER PasswordMustChange;
        // Alignment: 8 - Already aligned;
        out.writeLong(passwordMustChange);
        // <NDR: struct> RPC_UNICODE_STRING UserName;
        // Alignment: 4 - Already aligned
        userName.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING FullName;
        // Alignment: 4 - Already aligned
        fullName.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING HomeDirectory;
        // Alignment: 4 - Already aligned
        homeDirectory.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING HomeDirectoryDrive;
        // Alignment: 4 - Already aligned
        homeDirectoryDrive.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING ScriptPath;
        // Alignment: 4 - Already aligned
        scriptPath.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING ProfilePath;
        // Alignment: 4 - Already aligned
        profilePath.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING AdminComment;
        // Alignment: 4 - Already aligned
        adminComment.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING WorkStations;
        // Alignment: 4 - Already aligned
        workStations.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING UserComment;
        // Alignment: 4 - Already aligned
        userComment.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING Parameters;
        // Alignment: 4 - Already aligned
        parameters.marshalEntity(out);
        // <NDR: struct> RPC_SHORT_BLOB LmOwfPassword;
        // Alignment: 4 - Already aligned
        lmOwfPassword.marshalEntity(out);
        // <NDR: struct> RPC_SHORT_BLOB NtOwfPassword;
        // Alignment: 4 - Already aligned
        ntOwfPassword.marshalEntity(out);
        // <NDR: struct> RPC_UNICODE_STRING PrivateData;
        // Alignment: 4 - Already aligned
        privateData.marshalEntity(out);
        // <NDR: struct> SAMPR_SR_SECURITY_DESCRIPTOR SecurityDescriptor;
        // Alignment: 4 - Already aligned
        securityDescriptor.marshalEntity(out);
        // <NDR: unsigned long> unsigned long UserId;
        // Alignment: 4 - Already aligned
        out.writeInt(rid);
        // <NDR: unsigned long> unsigned long PrimaryGroupId;
        // Alignment: 4 - Already aligned
        out.writeInt(primaryGroupId);
        // <NDR: unsigned long> unsigned long UserAccountControl;
        // Alignment: 4 - Already aligned
        out.writeInt(userAccountControl);
        // <NDR: unsigned long> unsigned long WhichFields;
        // Alignment: 4 - Already aligned
        out.writeInt(whichFields);
        // <NDR: struct> SAMPR_LOGON_HOURS LogonHours;
        // Alignment: 4 - Already aligned
        logonHours.marshalEntity(out);
        // <NDR: unsigned short> unsigned short BadPasswordCount;
        out.align(Alignment.TWO);
        out.writeShort(badPasswordCount);
        // <NDR: unsigned short> unsigned short LogonCount;
        // Alignment: 2 - Already aligned
        out.writeShort(logonCount);
        // <NDR: unsigned short> unsigned short CountryCode;
        // Alignment: 2 - Already aligned
        out.writeShort(countryCode);
        // <NDR: unsigned short> unsigned short CodePage;
        // Alignment: 2 - Already aligned
        out.writeShort(codePage);
        // <NDR: unsigned char> unsigned char LmPasswordPresent;
        out.writeByte(lmPasswordPresent);
        // <NDR: unsigned char> unsigned char NtPasswordPresent;
        out.writeByte(ntPasswordPresent);
        // <NDR: unsigned char> unsigned char PasswordExpired;
        out.writeByte(passwordExpired);
        // <NDR: unsigned char> unsigned char PrivateDataSensitive;
        out.writeByte(privateDataSensitive);
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING UserName;
        userName.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING FullName;
        fullName.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING HomeDirectory;
        homeDirectory.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING HomeDirectoryDrive;
        homeDirectoryDrive.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING ScriptPath;
        scriptPath.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING ProfilePath;
        profilePath.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING AdminComment;
        adminComment.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING WorkStations;
        workStations.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING UserComment;
        userComment.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING Parameters;
        parameters.marshalDeferrals(out);
        // <NDR: struct> RPC_SHORT_BLOB LmOwfPassword;
        lmOwfPassword.marshalDeferrals(out);
        // <NDR: struct> RPC_SHORT_BLOB NtOwfPassword;
        ntOwfPassword.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING PrivateData;
        privateData.marshalDeferrals(out);
        // <NDR: struct> SAMPR_SR_SECURITY_DESCRIPTOR SecurityDescriptor;
        securityDescriptor.marshalDeferrals(out);
        // <NDR: struct> SAMPR_LOGON_HOURS LogonHours;
        logonHours.marshalDeferrals(out);
    }
}
