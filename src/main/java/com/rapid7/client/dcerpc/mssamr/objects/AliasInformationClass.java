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

/** *
 * <b>Alignment: 2</b>
 * <br>
 * <a href="https://msdn.microsoft.com/en-us/library/cc245593.aspx">ALIAS_INFORMATION_CLASS</a>
 * <blockquote><pre>The ALIAS_INFORMATION_CLASS enumeration indicates how to interpret the Buffer parameter for SamrQueryInformationAlias and SamrSetInformationAlias. For a list of the structures associated with each enumeration, see section 2.2.6.6.
 *      typedef  enum _ALIAS_INFORMATION_CLASS
 *      {
 *          AliasGeneralInformation = 1,
 *          AliasNameInformation,
 *          AliasAdminCommentInformation
 *      } ALIAS_INFORMATION_CLASS;
 *  AliasGeneralInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_ALIAS_GENERAL_INFORMATION structure (see section 2.2.6.2).
 *  AliasNameInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_ALIAS_NAME_INFORMATION structure (see section 2.2.6.3).
 *  AliasAdminCommentInformation:  Indicates the Buffer parameter is to be interpreted as a SAMPR_ALIAS_ADM_COMMENT_INFORMATION structure (see section 2.2.6.4).</pre></blockquote>
 */
public enum AliasInformationClass {
    ALIAS_GENERALINFORMATION(1);

    private final int infoLevel;

    AliasInformationClass(final int infoLevel) {
        this.infoLevel = infoLevel;
    }

    public int getInfoLevel() {
        return infoLevel;
    }

    @Override
    public String toString() {
        return String.format("ALIAS_INFORMATION_CLASS{name:%s, infoLevel:%d}", name(), getInfoLevel());
    }

}
