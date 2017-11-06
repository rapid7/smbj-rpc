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
package com.rapid7.client.dcerpc.msvcctl.objects;

import com.rapid7.client.dcerpc.msvcctl.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;

public interface IServiceConfigInfo {
    ServiceType getServiceType();

    ServiceStartType getStartType();

    ServiceError getErrorControl();

    String getBinaryPathName();

    String getLoadOrderGroup();

    int getTagId();

    String getDependencies();

    String getServiceStartName();

    String getDisplayName();

    String getPassword();

    void setPassword(String password);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
