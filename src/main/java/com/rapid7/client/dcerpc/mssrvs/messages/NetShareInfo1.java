/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssrvs.messages;

import java.util.Objects;

public class NetShareInfo1 extends NetShareInfo0 {
    private final int type;
    private final String comment;

    public NetShareInfo1(final String name, final int type, final String comment) {
        super(name);
        this.type = type;
        this.comment = comment;
    }

    public int getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return String.format("%s, type=%d, comment=%s", super.toString(), type, comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, comment);
    }

    @Override
    public boolean equals(final Object anObject) {
        return super.equals(anObject) && anObject instanceof NetShareInfo1 && Objects.equals(((NetShareInfo1) anObject).type, type) && Objects.equals(((NetShareInfo1) anObject).comment, comment);
    }
}
