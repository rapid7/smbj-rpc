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

/**
 * * <b>Alignment: 2</b> <br>
 * <a href=
 * "https://msdn.microsoft.com/en-us/library/cc245586.aspx">GROUP_INFORMATION_CLASS</a>
 * <blockquote>
 * 
 * <pre>
 * The GROUP_INFORMATION_CLASS enumeration indicates how to interpret the Buffer parameter for SamrSetInformationGroup and SamrQueryInformationGroup. For a list of associated structures, see section 2.2.5.7.
 *      typedef  enum _GROUP_INFORMATION_CLASS
 *      {
 *          GroupGeneralInformation = 1,
 *          GroupNameInformation,
 *          GroupAttributeInformation,
 *          GroupAdminCommentInformation,
 *          GroupReplicationInformation
 *          } GROUP_INFORMATION_CLASS;
 *  GroupGeneralInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_GROUP_GENERAL_INFORMATION structure (see section 2.2.5.3).
 *  GroupNameInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_GROUP_NAME_INFORMATION structure (see section 2.2.5.4).
 *  GroupAttributeInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_GROUP_ATTRIBUTE_INFORMATION structure (see section 2.2.5.2).
 *  GroupAdminCommentInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_GROUP_ADM_COMMENT_INFORMATION structure (see section 2.2.5.5).
 *  GroupReplicationInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_GROUP_GENERAL_INFORMATION structure (see section 2.2.5.3).
 * </pre>
 * 
 * </blockquote>
 */
public enum GroupInformationClass {
    GROUP_GENERAL_INFORMATION(1),
    GROUP_NAME_INFORMATION(2),
    GROUP_ATTRIBUTE_INFORMATION(3),
    GROUP_ADMINCOMMENT_INFORMATION(4),
    GROUP_REPLICATION_INFORMATION(5);

    private final int infoLevel;

    GroupInformationClass(final int infoLevel) {
        this.infoLevel = infoLevel;
    }

    public int getInfoLevel() {
        return infoLevel;
    }

    @Override
    public String toString() {
        return String.format("GROUP_INFORMATION_CLASS{name:%s, infoLevel:%d}", name(), getInfoLevel());
    }

}
