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
package com.rapid7.client.dcerpc.msrrp.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class FileTime {
    private final Date date;
    private final long windowsTime;

    public FileTime(final long windowsTime) {
        if (windowsTime < 116444736000000000l) {
            throw new IllegalArgumentException("WindowsTime is invalid: " + windowsTime + " < 116444736000000000");
        }
        date = new Date((windowsTime - 116444736000000000l) / 10000);
        this.windowsTime = windowsTime;
    }

    public Date getTime() {
        return date;
    }

    public long getWindowsTime() {
        return windowsTime;
    }

    @Override
    public String toString() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return String.format("%s (%d)", format.format(date), windowsTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, windowsTime);
    }

    @Override
    public boolean equals(final Object anObject) {
        return anObject instanceof FileTime && Objects.equals(date, ((FileTime) anObject).date) && Objects.equals(windowsTime, ((FileTime) anObject).windowsTime);
    }
}
