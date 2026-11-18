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

/**
 * <b>Alignment: 2</b> <br>
 * <a href=
 * "https://msdn.microsoft.com/en-us/library/cc245617.aspx">USER_INFORMATION_CLASS</a>
 * <blockquote>
 * 
 * <pre>
 * The USER_INFORMATION_CLASS enumeration indicates how to interpret the Buffer parameter for SamrSetInformationUser, SamrQueryInformationUser, SamrSetInformationUser2, and SamrQueryInformationUser2. For a list of associated structures, see section 2.2.7.29.
 *      typedef  enum _USER_INFORMATION_CLASS
 *      {
 *          UserGeneralInformation = 1,
 *          UserPreferencesInformation = 2,
 *          UserLogonInformation = 3,
 *          UserLogonHoursInformation = 4,
 *          UserAccountInformation = 5,
 *          UserNameInformation = 6,
 *          UserAccountNameInformation = 7,
 *          UserFullNameInformation = 8,
 *          UserPrimaryGroupInformation = 9,
 *          UserHomeInformation = 10,
 *          UserScriptInformation = 11,
 *          UserProfileInformation = 12,
 *          UserAdminCommentInformation = 13,
 *          UserWorkStationsInformation = 14,
 *          UserControlInformation = 16,
 *          UserExpiresInformation = 17,
 *          UserInternal1Information = 18,
 *          UserParametersInformation = 20,
 *          UserAllInformation = 21,
 *          UserInternal4Information = 23,
 *          UserInternal5Information = 24,
 *          UserInternal4InformationNew = 25,
 *          UserInternal5InformationNew = 26
 *      } USER_INFORMATION_CLASS,
 *      *PUSER_INFORMATION_CLASS;
 *  UserGeneralInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_GENERAL_INFORMATION structure (see section 2.2.7.7).
 *  UserPreferencesInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_PREFERENCES_INFORMATION structure (see section 2.2.7.8).
 *  UserLogonInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_LOGON_INFORMATION structure (see section 2.2.7.10).
 *  UserLogonHoursInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_LOGON_HOURS_INFORMATION structure (see section 2.2.7.20).
 *  UserAccountInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_ACCOUNT_INFORMATION structure (see section 2.2.7.11).
 *  UserNameInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_NAME_INFORMATION structure (see section 2.2.7.14).
 *  UserAccountNameInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_A_NAME_INFORMATION structure (see section 2.2.7.12).
 *  UserFullNameInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_F_NAME_INFORMATION structure (see section 2.2.7.13).
 *  UserPrimaryGroupInformation:  Indicates the Buffer parameter is to be interpreted as a USER_PRIMARY_GROUP_INFORMATION structure (see section 2.2.7.2).
 *  UserHomeInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_HOME_INFORMATION structure (see section 2.2.7.15).
 *  UserScriptInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_SCRIPT_INFORMATION structure (see section 2.2.7.16).
 *  UserProfileInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_PROFILE_INFORMATION structure (see section 2.2.7.17).
 *  UserAdminCommentInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_ADMIN_COMMENT_INFORMATION structure (see section 2.2.7.18).
 *  UserWorkStationsInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_WORKSTATIONS_INFORMATION structure (see section 2.2.7.19).
 *  UserControlInformation:  Indicates the Buffer parameter is to be interpreted as a USER_CONTROL_INFORMATION structure (see section 2.2.7.3).
 *  UserExpiresInformation:  Indicates the Buffer parameter is to be interpreted as a USER_EXPIRES_INFORMATION structure (see section 2.2.7.4).
 *  UserInternal1Information:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_INTERNAL1_INFORMATION structure (see section 2.2.7.23).
 *  UserParametersInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_PARAMETERS_INFORMATION structure (see section 2.2.7.9).
 *  UserAllInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_ALL_INFORMATION structure (see section 2.2.7.6).
 *  UserInternal4Information:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_INTERNAL4_INFORMATION structure (see section 2.2.7.24).
 *  UserInternal5Information:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_INTERNAL5_INFORMATION structure (see section 2.2.7.26).
 *  UserInternal4InformationNew:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_INTERNAL4_INFORMATION_NEW structure (see section 2.2.7.25).
 *  UserInternal5InformationNew:  Indicates the Buffer parameter is to be interpreted as a SAMPR_USER_INTERNAL5_INFORMATION_NEW structure (see section 2.2.7.27).
 * </pre>
 * 
 * </blockquote>
 */
public enum UserInformationClass {
    USER_GENERAL_INFORMATION(1),
    USER_PREFERENCES_INFORMATION(2),
    USER_LOGON_INFORMATION(3),
    USER_LOGONHOURS_INFORMATION(4),
    USER_ACCOUNT_INFORMATION(5),
    USER_NAME_INFORMATION(6),
    USER_ACCOUNTNAME_INFORMATION(7),
    USER_FULLNAME_INFORMATION(8),
    USER_PRIMARYGROUP_INFORMATION(9),
    USER_HOME_INFORMATION(10),
    USER_SCRIPT_INFORMATION(11),
    USER_PROFILE_INFORMATION(12),
    USER_ADMINCOMMENT_INFORMATION(13),
    USER_WORKSTATIONS_INFORMATION(14),
    USER_CONTROL_INFORMATION(16),
    USER_EXPIRES_INFORMATION(17),
    USER_INTERNAL1_INFORMATION(18),
    USER_PARAMETERS_INFORMATION(20),
    USER_ALL_INFORMATION(21),
    USER_INTERNAL4_INFORMATION(23),
    USER_INTERNAL5_INFORMATION(24),
    USER_INTERNAL4_INFORMATION_NEW(25),
    USER_INTERNAL5_INFORMATION_NEW(26);

    private final int infoLevel;

    UserInformationClass(final int infoLevel) {
        this.infoLevel = infoLevel;
    }

    public int getInfoLevel() {
        return infoLevel;
    }

    @Override
    public String toString() {
        return String.format("USER_INFORMATION_CLASS{name:%s, infoLevel:%d}", name(), getInfoLevel());
    }
}
