/**
 * Copyright 2020, Vadim Frolov.
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
package com.rapid7.client.dcerpc.initshutdown.dto;

import org.junit.Test;

import java.util.EnumSet;

import static com.rapid7.client.dcerpc.initshutdown.dto.ShutdownReason.*;
import static org.junit.Assert.*;

public class Test_ShutdownReason {
    @Test
    public void MAJOR_APPLICATION_getReason() {
        assertEquals(0x00040000, SHTDN_REASON_MAJOR_APPLICATION.getReason());
    }

    @Test
    public void MAJOR_MINOR_OPTIONAL() {
        final int expected = 0x8004000F;
        final EnumSet<ShutdownReason> flags = EnumSet.of(ShutdownReason.SHTDN_REASON_MAJOR_APPLICATION,
                ShutdownReason.SHTDN_REASON_MINOR_BLUESCREEN, ShutdownReason.SHTDN_REASON_FLAG_PLANNED);
        final int actual = ShutdownReason.getReason(flags);
        assertEquals(expected, actual);
    }

    @Test
    public void MAJOR_POWER() {
        final int expected = 0x00060000;
        final EnumSet<ShutdownReason> flags = EnumSet.of(SHTDN_REASON_MAJOR_POWER);
        final int actual = ShutdownReason.getReason(flags);
        assertEquals(expected, actual);
    }

    @Test
    public void SOFTWARE_DISK() {
        final int expected = 0x00030007;
        final EnumSet<ShutdownReason> flags = EnumSet.of(SHTDN_REASON_MAJOR_SOFTWARE,
                SHTDN_REASON_MINOR_DISK);
        final int actual = ShutdownReason.getReason(flags);
        assertEquals(expected, actual);
    }

    @Test
    public void NullReason() {
        final int expected = 0;
        final int actual = ShutdownReason.getReason(null);
        assertEquals(expected, actual);
    }
}
