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

package com.rapid7.client.dcerpc.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * <blockquote><pre>The SID_NAME_USE enumeration specifies the type of account that a SID references.
 *      typedef  enum _SID_NAME_USE
 *      {
 *          SidTypeUser = 1,
 *          SidTypeGroup,
 *          SidTypeDomain,
 *          SidTypeAlias,
 *          SidTypeWellKnownGroup,
 *          SidTypeDeletedAccount,
 *          SidTypeInvalid,
 *          SidTypeUnknown,
 *          SidTypeComputer,
 *          SidTypeLabel
 *      } SID_NAME_USE,
 *      *PSID_NAME_USE;
 * SidTypeUser: Indicates a user object.
 * SidTypeGroup: Indicates a group object.
 * SidTypeDomain: Indicates a domain object.
 * SidTypeAlias: Indicates an alias object.
 * SidTypeWellKnownGroup: Indicates an object whose SID is invariant.
 * SidTypeDeletedAccount: Indicates an object that has been deleted.
 * SidTypeInvalid: This member is not used.
 * SidTypeUnknown: Indicates that the type of object could not be determined. For example, no object with that SID exists.
 * SidTypeComputer: This member is not used.
 * SidTypeLabel: This member is not used.</pre></blockquote>
 */
public enum SIDUse {
    SID_TYPE_USER((short) 1),
    SID_TYPE_GROUP((short) 2),
    SID_TYPE_DOMAIN((short) 3),
    SID_TYPE_ALIAS((short) 4),
    SID_TYPE_WELL_KNOWN_GROUP((short) 5),
    SID_TYPE_DELETED_ACCOUNT((short) 6),
    SID_TYPE_INVALID((short) 7),
    SID_TYPE_UNKNOWN((short) 8),
    SID_TYPE_COMPUTER((short) 9),
    SID_TYPE_LABEL((short) 10);

    private final short value;

    SIDUse(final short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    private static final Map<Short, SIDUse> VALUE_MAP = new HashMap<>();
    static {
        for (SIDUse sidNameUse : SIDUse.values()) {
            VALUE_MAP.put(sidNameUse.getValue(), sidNameUse);
        }
    }

    public static SIDUse fromValue(final short value) {
        return VALUE_MAP.get(value);
    }
}
