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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/*
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-rsp/d74aa51d-d481-4dc5-b0a2-750871916106">2.3 Shutdown Reasons</a>
 * <br>
 * Usage:<pre>
 *   EnumSet<ShutdownReason> flags = EnumSet.of(ShutdownReason.SHTDN_REASON_MAJOR_APPLICATION,
 *           ShutdownReason.SHTDN_REASON_MINOR_BLUESCREEN);
 *   int reason = ShutdownReason.getReason(flags);
 *   System.out.printf("0x%08X\n", reason);
 * </pre>
 */
public enum ShutdownReason {
    SHTDN_REASON_MAJOR_APPLICATION(0x00040000),
    SHTDN_REASON_MAJOR_HARDWARE(0x00010000),
    SHTDN_REASON_MAJOR_LEGACY_API(0x00070000),
    SHTDN_REASON_MAJOR_OPERATINGSYSTEM(0x00020000),
    SHTDN_REASON_MAJOR_OTHER(0x00000000),
    SHTDN_REASON_MAJOR_POWER(0x00060000),
    SHTDN_REASON_MAJOR_SOFTWARE(0x00030000),
    SHTDN_REASON_MAJOR_SYSTEM(0x00050000),

    SHTDN_REASON_MINOR_BLUESCREEN(0x0000000F),
    SHTDN_REASON_MINOR_CORDUNPLUGGED(0x0000000b),
    SHTDN_REASON_MINOR_DISK(0x00000007),
    SHTDN_REASON_MINOR_ENVIRONMENT(0x0000000c),
    SHTDN_REASON_MINOR_HARDWARE_DRIVER(0x0000000d),
    SHTDN_REASON_MINOR_HOTFIX(0x00000011),
    SHTDN_REASON_MINOR_HOTFIX_UNINSTALL(0x00000017),
    SHTDN_REASON_MINOR_HUNG(0x00000005),
    SHTDN_REASON_MINOR_INSTALLATION(0x00000002),
    SHTDN_REASON_MINOR_MAINTENANCE(0x00000001),
    SHTDN_REASON_MINOR_MMC(0x00000019),
    SHTDN_REASON_MINOR_NETWORK_CONNECTIVITY(0x00000014),
    SHTDN_REASON_MINOR_NETWORKCARD(0x00000009),
    SHTDN_REASON_MINOR_OTHER(0x00000000),
    SHTDN_REASON_MINOR_OTHERDRIVER(0x0000000e),
    SHTDN_REASON_MINOR_POWER_SUPPLY(0x0000000a),
    SHTDN_REASON_MINOR_PROCESSOR(0x00000008),
    SHTDN_REASON_MINOR_RECONFIG(0x00000004),
    SHTDN_REASON_MINOR_SECURITY(0x00000013),
    SHTDN_REASON_MINOR_SECURITYFIX(0x00000012),
    SHTDN_REASON_MINOR_SECURITYFIX_UNINSTALL(0x00000018),
    SHTDN_REASON_MINOR_SERVICEPACK(0x00000010),
    SHTDN_REASON_MINOR_SERVICEPACK_UNINSTALL(0x00000016),
    SHTDN_REASON_MINOR_TERMSRV(0x00000020),
    SHTDN_REASON_MINOR_UNSTABLE(0x00000006),
    SHTDN_REASON_MINOR_UPGRADE(0x00000003),
    SHTDN_REASON_MINOR_WMI(0x00000015),

    SHTDN_REASON_FLAG_USER_DEFINED(0x40000000),
    SHTDN_REASON_FLAG_PLANNED(0x80000000);

    private final int reasonID;

    ShutdownReason(final int reasonID) {
        this.reasonID = reasonID;
    }

    public long getReason() {
        return reasonID;
    }

    public static int getReason(final EnumSet<ShutdownReason> reasons) {
        int finalReason = 0;
        if (reasons == null) {
            return ShutdownReason.getReason(EnumSet.of(ShutdownReason.SHTDN_REASON_MAJOR_OTHER,
                    ShutdownReason.SHTDN_REASON_MINOR_OTHER));
        }
        for (ShutdownReason shReason : reasons) {
            finalReason |= shReason.getReason();
        }
        return finalReason;
    }
}
