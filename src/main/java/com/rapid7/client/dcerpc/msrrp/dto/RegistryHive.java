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

import java.util.HashMap;
import java.util.Map;
import com.rapid7.client.dcerpc.msrrp.messages.*;

/**
 * An application must open a key before it can add data to the registry. To open a key, an application must supply a
 * handle to another key in the registry that is already open. The system defines predefined keys that are always open.
 * Predefined keys help an application navigate in the registry and make it possible to develop tools that allow a
 * system administrator to manipulate categories of data. Applications that add data to the registry should always work
 * within the framework of predefined keys, so administrative tools can find and use the new data.<br>
 * <br>
 * An application can use handles to these keys as entry points to the registry. These handles are valid for all
 * implementations of the registry, although the use of the handles may vary from platform to platform. In addition,
 * other predefined handles have been defined for specific platforms. The following are handles to the predefined keys.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/ms724836(v=vs.85).aspx">Predefined Keys</a>
 */
public enum RegistryHive {
    /**
     * Registry entries subordinate to this key define types (or classes) of documents and the properties associated
     * with those types. Shell and COM applications use the information stored under this key.<br>
     * <br>
     * This key also provides backward compatibility with the Windows 3.1 registration database by storing information
     * for DDE and OLE support. File viewers and user interface extensions store their OLE class identifiers in
     * HKEY_CLASSES_ROOT, and in-process servers are registered in this key.<br>
     * <br>
     * This handle should not be used in a service or an application that impersonates different users.
     */
    HKEY_CLASSES_ROOT("HKEY_CLASSES_ROOT", "HKCR", "OpenClassesRoot", OpenClassesRoot.OP_NUM),
    /**
     * Contains information about the current hardware profile of the local computer system. The information under
     * HKEY_CURRENT_CONFIG describes only the differences between the current hardware configuration and the standard
     * configuration. Information about the standard hardware configuration is stored under the Software and System keys
     * of HKEY_LOCAL_MACHINE.<br>
     * <br>
     * HKEY_CURRENT_CONFIG is an alias for HKEY_LOCAL_MACHINE\System\CurrentControlSet\Hardware Profiles\Current.
     */
    HKEY_CURRENT_CONFIG("HKEY_CURRENT_CONFIG", "HKCC", "OpenCurrentConfig", OpenCurrentConfig.OP_NUM),
    /**
     * Registry entries subordinate to this key define the preferences of the current user. These preferences include
     * the settings of environment variables, data about program groups, colors, printers, network connections, and
     * application preferences. This key makes it easier to establish the current user's settings; the key maps to the
     * current user's branch in HKEY_USERS. In HKEY_CURRENT_USER, software vendors store the current user-specific
     * preferences to be used within their applications. Microsoft, for example, creates the
     * HKEY_CURRENT_USER\Software\Microsoft key for its applications to use, with each application creating its own
     * subkey under the Microsoft key.<br>
     * <br>
     * The mapping between HKEY_CURRENT_USER and HKEY_USERS is per process and is established the first time the process
     * references HKEY_CURRENT_USER. The mapping is based on the security context of the first thread to reference
     * HKEY_CURRENT_USER. If this security context does not have a registry hive loaded in HKEY_USERS, the mapping is
     * established with HKEY_USERS\.Default. After this mapping is established it persists, even if the security context
     * of the thread changes.
     */
    HKEY_CURRENT_USER("HKEY_CURRENT_USER", "HKCU", "OpenCurrentUser", OpenCurrentUser.OP_NUM),
    /**
     * Registry entries subordinate to this key define the physical state of the computer, including data about the bus
     * type, system memory, and installed hardware and software. It contains subkeys that hold current configuration
     * data, including Plug and Play information (the Enum branch, which includes a complete list of all hardware that
     * has ever been on the system), network logon preferences, network security information, software-related
     * information (such as server names and the location of the server), and other system information.
     */
    HKEY_LOCAL_MACHINE("HKEY_LOCAL_MACHINE", "HKLM", "OpenLocalMachine", OpenLocalMachine.OP_NUM),
    /**
     * Registry entries subordinate to this key allow you to access performance data. The data is not actually stored in
     * the registry; the registry functions cause the system to collect the data from its source.
     */
    HKEY_PERFORMANCE_DATA("HKEY_PERFORMANCE_DATA", "", "OpenPerformanceData", OpenPerformanceData.OP_NUM),
    /**
     * Registry entries subordinate to this key reference the text strings that describe counters in the local language
     * of the area in which the computer system is running.
     */
    HKEY_PERFORMANCE_NLSTEXT("HKEY_PERFORMANCE_NLSTEXT", "", "OpenPerformanceNlsText", OpenPerformanceNlsText.OP_NUM),
    /**
     * Registry entries subordinate to this key reference the text strings that describe counters in US English.
     */
    HKEY_PERFORMANCE_TEXT("HKEY_PERFORMANCE_TEXT", "", "OpenPerformanceText", OpenPerformanceText.OP_NUM),
    /**
     * Registry entries subordinate to this key define the default user configuration for new users on the local
     * computer and the user configuration for the current user.
     */
    HKEY_USERS("HKEY_USERS", "HKU", "OpenUsers", OpenUsers.OP_NUM);

    private final String fullName;
    private final String shortName;
    private final String opName;
    private final short opNum;
    private static final Map<String, RegistryHive> registryHiveByName = new HashMap<>();

    RegistryHive(final String fullName, final String shortName, final String opName, final short opNum) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.opName = opName;
        this.opNum = opNum;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getOpName() {
        return opName;
    }

    public short getOpNum() {
        return opNum;
    }

    public HandleRequest getRequest(int accessMask) {
        return new HandleRequest(opNum, accessMask);
    }

    public static RegistryHive getRegistryHiveByName(final String fullName) {
        return registryHiveByName.get(fullName.trim().toUpperCase());
    }

    static {
        for (final RegistryHive hive : RegistryHive.values()) {
            final String fullName = hive.getFullName();
            registryHiveByName.put(fullName, hive);
            final String shortName = hive.getShortName();
            if (!shortName.isEmpty()) {
                registryHiveByName.put(shortName, hive);
            }
        }
    }
}
