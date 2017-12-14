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
package com.rapid7.client.dcerpc.msvcctl.dto.enums;

/**
 * This enumeration is a representation of the service types.
 */
public enum WindowsOperation {
    START_SERVICE("start"),
    STOP_SERVICE("stop");

    /////////////////////////////////////////////////////////
    // Public Methods
    /////////////////////////////////////////////////////////

    /**
     * This constructor initializes a operation
     * enum type with the provided String name.
     *
     * @param operation The operation value
     */
    WindowsOperation(String operation) {
        m_operationName = operation;
    }

    /**
     * Returns the String value of the enum.
     *
     * @return the service name
     */
    public String getOperationName() {
        return m_operationName;
    }

    /////////////////////////////////////////////////////////
    // Non-Public Fields
    /////////////////////////////////////////////////////////

    private String m_operationName;
}
