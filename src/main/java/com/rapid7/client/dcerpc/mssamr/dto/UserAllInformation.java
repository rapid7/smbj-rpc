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

package com.rapid7.client.dcerpc.mssamr.dto;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents information retrieved from SamrQueryUserInfomration using info level 21 (UserAllInformation).
 */
public class UserAllInformation {
    private final long lastLogon;
    private final long lastLogoff;
    private final long passwordLastSet;
    private final long accountExpires;
    private final long passwordCanChange;
    private final long passwordMustChange;
    private final String userName;
    private final String fullName;
    private final String homeDirectory;
    private final String homeDirectoryDrive;
    private final String scriptPath;
    private final String profilePath;
    private final String adminComment;
    private final String workStations;
    private final String userComment;
    private final String parameters;
    private final int[] lmOwfPassword;
    private final int[] ntOwfPassword;
    private final String privateData;
    private final byte[] securityDescriptor;
    private final long userId;
    private final long primaryGroupId;
    private final long userAccountControl;
    private final long whichFields;
    private final LogonHours logonHours;
    private final int badPasswordCount;
    private final int logonCount;
    private final int countryCode;
    private final int codePage;
    private final boolean lmPasswordPresent;
    private final boolean ntPasswordPresent;
    private final boolean passwordExpired;
    private final boolean privateDataSensitive;

    public UserAllInformation(long lastLogon, long lastLogoff, long passwordLastSet, long accountExpires,
            long passwordCanChange, long passwordMustChange, String userName, String fullName, String homeDirectory,
            String homeDirectoryDrive, String scriptPath, String profilePath, String adminComment, String workStations,
            String userComment, String parameters, int[] lmOwfPassword, int[] ntOwfPassword, String privateData,
            byte[] securityDescriptor, long userId, long primaryGroupId, long userAccountControl,
            long whichFields, LogonHours logonHours, int badPasswordCount, int logonCount, int countryCode,
            int codePage, boolean lmPasswordPresent, boolean ntPasswordPresent, boolean passwordExpired,
            boolean privateDataSensitive) {
        this.lastLogon = lastLogon;
        this.lastLogoff = lastLogoff;
        this.passwordLastSet = passwordLastSet;
        this.accountExpires = accountExpires;
        this.passwordCanChange = passwordCanChange;
        this.passwordMustChange = passwordMustChange;
        this.userName = userName;
        this.fullName = fullName;
        this.homeDirectory = homeDirectory;
        this.homeDirectoryDrive = homeDirectoryDrive;
        this.scriptPath = scriptPath;
        this.profilePath = profilePath;
        this.adminComment = adminComment;
        this.workStations = workStations;
        this.userComment = userComment;
        this.parameters = parameters;
        this.lmOwfPassword = lmOwfPassword;
        this.ntOwfPassword = ntOwfPassword;
        this.privateData = privateData;
        this.securityDescriptor = securityDescriptor;
        this.userId = userId;
        this.primaryGroupId = primaryGroupId;
        this.userAccountControl = userAccountControl;
        this.whichFields = whichFields;
        this.logonHours = logonHours;
        this.badPasswordCount = badPasswordCount;
        this.logonCount = logonCount;
        this.countryCode = countryCode;
        this.codePage = codePage;
        this.lmPasswordPresent = lmPasswordPresent;
        this.ntPasswordPresent = ntPasswordPresent;
        this.passwordExpired = passwordExpired;
        this.privateDataSensitive = privateDataSensitive;
    }

    public long getLastLogon() {
        return lastLogon;
    }

    public long getLastLogoff() {
        return lastLogoff;
    }

    public long getPasswordLastSet() {
        return passwordLastSet;
    }

    public long getAccountExpires() {
        return accountExpires;
    }

    public long getPasswordCanChange() {
        return passwordCanChange;
    }

    public long getPasswordMustChange() {
        return passwordMustChange;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public String getHomeDirectoryDrive() {
        return homeDirectoryDrive;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public String getWorkStations() {
        return workStations;
    }

    public String getUserComment() {
        return userComment;
    }

    public String getParameters() {
        return parameters;
    }

    public int[] getLmOwfPassword() {
        return lmOwfPassword;
    }

    public int[] getNtOwfPassword() {
        return ntOwfPassword;
    }

    public String getPrivateData() {
        return privateData;
    }

    public byte[] getSecurityDescriptor() {
        return securityDescriptor;
    }

    public long getUserId() {
        return userId;
    }

    public long getPrimaryGroupId() {
        return primaryGroupId;
    }

    public long getUserAccountControl() {
        return userAccountControl;
    }

    public long getWhichFields() {
        return whichFields;
    }

    public LogonHours getLogonHours() {
        return logonHours;
    }

    public int getBadPasswordCount() {
        return badPasswordCount;
    }

    public int getLogonCount() {
        return logonCount;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public int getCodePage() {
        return codePage;
    }

    public boolean isLmPasswordPresent() {
        return lmPasswordPresent;
    }

    public boolean isNtPasswordPresent() {
        return ntPasswordPresent;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public boolean isPrivateDataSensitive() {
        return privateDataSensitive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLastLogon(), getLastLogoff(), getPasswordLastSet(),
                getAccountExpires(), getPasswordCanChange(), getPasswordMustChange(),
                getUserName(), getFullName(), getHomeDirectory(), getHomeDirectoryDrive(),
                getScriptPath(), getProfilePath(), getAdminComment(), getWorkStations(),
                getUserComment(), getParameters(), getLmOwfPassword(), getNtOwfPassword(),
                getPrivateData(), getSecurityDescriptor(), getUserId(), getPrimaryGroupId(),
                getUserAccountControl(), getWhichFields(), getLogonHours(), getBadPasswordCount(),
                getLogonCount(), getCountryCode(), getCodePage(), isLmPasswordPresent(),
                isNtPasswordPresent(), isPasswordExpired(), isPrivateDataSensitive());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof UserAllInformation)) {
            return false;
        }
        UserAllInformation other = (UserAllInformation) obj;
        return Objects.equals(getLastLogon(), other.getLastLogon())
                && Objects.equals(getLastLogoff(), other.getLastLogoff())
                && Objects.equals(getPasswordLastSet(), other.getPasswordLastSet())
                && Objects.equals(getAccountExpires(), other.getAccountExpires())
                && Objects.equals(getPasswordCanChange(), other.getPasswordCanChange())
                && Objects.equals(getPasswordMustChange(), other.getPasswordMustChange())
                && Objects.equals(getUserName(), other.getUserName())
                && Objects.equals(getFullName(), other.getFullName())
                && Objects.equals(getHomeDirectory(), other.getHomeDirectory())
                && Objects.equals(getHomeDirectoryDrive(), other.getHomeDirectoryDrive())
                && Objects.equals(getScriptPath(), other.getScriptPath())
                && Objects.equals(getProfilePath(), other.getProfilePath())
                && Objects.equals(getAdminComment(), other.getAdminComment())
                && Objects.equals(getWorkStations(), other.getWorkStations())
                && Objects.equals(getUserComment(), other.getUserComment())
                && Objects.equals(getParameters(), other.getParameters())
                && Arrays.equals(getLmOwfPassword(), other.getLmOwfPassword())
                && Arrays.equals(getNtOwfPassword(), other.getNtOwfPassword())
                && Objects.equals(getPrivateData(), other.getPrivateData())
                && Arrays.equals(getSecurityDescriptor(), other.getSecurityDescriptor())
                && Objects.equals(getUserId(), other.getUserId())
                && Objects.equals(getPrimaryGroupId(), other.getPrimaryGroupId())
                && Objects.equals(getUserAccountControl(), other.getUserAccountControl())
                && Objects.equals(getWhichFields(), other.getWhichFields())
                && Objects.equals(getLogonHours(), other.getLogonHours())
                && Objects.equals(getBadPasswordCount(), other.getBadPasswordCount())
                && Objects.equals(getLogonCount(), other.getLogonCount())
                && Objects.equals(getCountryCode(), other.getCountryCode())
                && Objects.equals(getCodePage(), other.getCodePage())
                && Objects.equals(isLmPasswordPresent(), other.isLmPasswordPresent())
                && Objects.equals(isNtPasswordPresent(), other.isNtPasswordPresent())
                && Objects.equals(isPasswordExpired(), other.isPasswordExpired())
                && Objects.equals(isPrivateDataSensitive(), other.isPrivateDataSensitive());
    }

    @Override
    public String toString() {
        return String.format("UserAllInformation{UserId:%d, PrimaryGroupId:%d, UserName:%s, FullName:%s}",
            getUserId(), getPrimaryGroupId(), getUserName(), getFullName());
    }
}
