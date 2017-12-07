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

package com.rapid7.client.dcerpc.mssrvs.objects;

import java.io.IOException;
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * <b>Alignment: 4</b>
 * <a href="https://msdn.microsoft.com/en-us/library/cc247161.aspx">SHARE_ENUM_STRUCT</a>
 * This class also encompasses <a href="https://msdn.microsoft.com/en-us/library/cc247119.aspx">SHARE_ENUM_UNION</a>, as
 * that class adds no real value as its own entity.
 * <blockquote><pre>The SHARE_ENUM_STRUCT structure specifies the information level that the client requests in the NetrShareEnum method and encapsulates the SHARE_ENUM_UNION union that receives the entries enumerated by the server.
 *
 *      typedef struct _SHARE_ENUM_STRUCT {
 *          DWORD Level;
 *          [switch_is(Level)] SHARE_ENUM_UNION ShareInfo;
 *      } SHARE_ENUM_STRUCT,
 *      *PSHARE_ENUM_STRUCT,
 *      *LPSHARE_ENUM_STRUCT;
 *
 * Level: Specifies the information level of the data. This parameter MUST have one of the following values.
 *      0       SHARE_INFO_0_CONTAINER
 *      1       SHARE_INFO_1_CONTAINER
 *      2       SHARE_INFO_2_CONTAINER
 *      501     SHARE_INFO_501_CONTAINER
 *      502     SHARE_INFO_502_CONTAINER
 *      503     SHARE_INFO_503_CONTAINER</pre></blockquote>
 */
public abstract class ShareEnumStruct<T extends ShareInfoContainer> implements Unmarshallable {

    private T shareInfoContainer;

    public abstract ShareEnumLevel getLevel();

    public T getShareInfoContainer() {
        return shareInfoContainer;
    }

    abstract T createShareInfoContainer();

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: unsigned long> DWORD Level;
        // Alignment: 4 - Already aligned
        final int level = in.readInt();
        if (level != getLevel().getInfoLevel()) {
            throw new UnmarshalException(String.format(
                    "Expected info level %d, got: %d", getLevel().getInfoLevel(), level));
        }
        // <NDR: unsigned long> [switch_is(Level)]
        // Alignment: 4 - Already aligned
        final int enumLevel = in.readInt();
        if (enumLevel != level) {
            throw new UnmarshalException(String.format(
                    "Expected info level %d to match enum level, got: %d", level, enumLevel));
        }
        // <NDR: pointer[struct]> [switch_is(Level)] SHARE_ENUM_UNION ShareInfo;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.shareInfoContainer = createShareInfoContainer();
        else
            this.shareInfoContainer = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (getShareInfoContainer() != null) {
            in.readUnmarshallable(getShareInfoContainer());
        }
    }

    public static class ShareEnumStruct0 extends ShareEnumStruct<ShareInfoContainer.ShareInfo0Container> {
        @Override
        public ShareEnumLevel getLevel() {
            return ShareEnumLevel.SHARE_INFO_0_CONTAINER;
        }

        @Override
        ShareInfoContainer.ShareInfo0Container createShareInfoContainer() {
            return new ShareInfoContainer.ShareInfo0Container();
        }
    }

    public static class ShareEnumStruct1 extends ShareEnumStruct<ShareInfoContainer.ShareInfo1Container> {
        @Override
        public ShareEnumLevel getLevel() {
            return ShareEnumLevel.SHARE_INFO_1_CONTAINER;
        }

        @Override
        ShareInfoContainer.ShareInfo1Container createShareInfoContainer() {
            return new ShareInfoContainer.ShareInfo1Container();
        }
    }

    public static class ShareEnumStruct2 extends ShareEnumStruct<ShareInfoContainer.ShareInfo2Container> {
        @Override
        public ShareEnumLevel getLevel() {
            return ShareEnumLevel.SHARE_INFO_2_CONTAINER;
        }

        @Override
        ShareInfoContainer.ShareInfo2Container createShareInfoContainer() {
            return new ShareInfoContainer.ShareInfo2Container();
        }
    }

    public static class ShareEnumStruct501 extends ShareEnumStruct<ShareInfoContainer.ShareInfo501Container> {
        @Override
        public ShareEnumLevel getLevel() {
            return ShareEnumLevel.SHARE_INFO_501_CONTAINER;
        }

        @Override
        ShareInfoContainer.ShareInfo501Container createShareInfoContainer() {
            return new ShareInfoContainer.ShareInfo501Container();
        }
    }

    public static class ShareEnumStruct502 extends ShareEnumStruct<ShareInfoContainer.ShareInfo502Container> {
        @Override
        public ShareEnumLevel getLevel() {
            return ShareEnumLevel.SHARE_INFO_502_CONTAINER;
        }

        @Override
        ShareInfoContainer.ShareInfo502Container createShareInfoContainer() {
            return new ShareInfoContainer.ShareInfo502Container();
        }
    }

    public static class ShareEnumStruct503 extends ShareEnumStruct<ShareInfoContainer.ShareInfo503Container> {
        @Override
        public ShareEnumLevel getLevel() {
            return ShareEnumLevel.SHARE_INFO_503_CONTAINER;
        }

        @Override
        ShareInfoContainer.ShareInfo503Container createShareInfoContainer() {
            return new ShareInfoContainer.ShareInfo503Container();
        }
    }
}
