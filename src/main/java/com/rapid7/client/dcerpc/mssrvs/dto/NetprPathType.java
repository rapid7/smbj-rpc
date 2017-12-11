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

/*
  Path types provided by Microsoft MSDN: https://msdn.microsoft.com/en-us/library/cc213230.aspx
 */
package com.rapid7.client.dcerpc.mssrvs.dto;

import java.util.HashMap;
import java.util.Map;

public enum NetprPathType {
    ITYPE_UNC_COMPNAME(4144),
    ITYPE_UNC_WC(4145),
    ITYPE_UNC(4096),
    ITYPE_UNC_WC_PATH(4097),
    ITYPE_UNC_SYS_SEM(6400),
    ITYPE_UNC_SYS_SHMEM(6656),
    ITYPE_UNC_SYS_MSLOT(6144),
    ITYPE_UNC_SYS_PIPE(6912),
    ITYPE_UNC_SYS_QUEUE(7680),
    ITYPE_PATH_ABSND(8194),
    ITYPE_PATH_ABSD(8198),
    ITYPE_PATH_RELND(8192),
    ITYPE_PATH_RELD(8196),
    ITYPE_PATH_ABSND_WC(8195),
    ITYPE_PATH_ABSD_WC(8199),
    ITYPE_PATH_RELND_WC(8193),
    ITYPE_PATH_RELD_WC(8197),
    ITYPE_PATH_SYS_SEM(10498),
    ITYPE_PATH_SYS_SHMEM(10754),
    ITYPE_PATH_SYS_MSLOT(10242),
    ITYPE_PATH_SYS_PIPE(11010),
    ITYPE_PATH_SYS_COMM(11266),
    ITYPE_PATH_SYS_PRINT(11522),
    ITYPE_PATH_SYS_QUEUE(11778),
    ITYPE_PATH_SYS_SEM_M(43266),
    ITYPE_PATH_SYS_SHMEM_M(43522),
    ITYPE_PATH_SYS_MSLOT_M(43010),
    ITYPE_PATH_SYS_PIPE_M(43778),
    ITYPE_PATH_SYS_COMM_M(44034),
    ITYPE_PATH_SYS_PRINT_M(44290),
    ITYPE_PATH_SYS_QUEUE_M(44546),
    ITYPE_DEVICE_DISK(16384),
    ITYPE_DEVICE_LPT(16400),
    ITYPE_DEVICE_COM(16416),
    ITYPE_DEVICE_CON(16448),
    ITYPE_DEVICE_NUL(16464);

    private final int id;
    private static final Map<Integer, NetprPathType> ids = new HashMap<>();

    NetprPathType(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean is(final int id) {
        return this.id == id;
    }

    public static NetprPathType getid(final int id) {
        return ids.get(id);
    }

    static {
        for (final NetprPathType id : NetprPathType.values()) {
            ids.put(id.getId(), id);
        }
    }
}
