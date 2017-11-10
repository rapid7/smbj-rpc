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
package com.rapid7.client.dcerpc.io.ndr;

/**
 * Represents an NDR data type. NDR data types always have a fixed alignment.
 * NDR data types are one of:
 *  * Construct
 *     * Struct
 *     * Union
 *     * Array
 *       * Fixed
 *       * Varying
 *       * Conformant
 *       * Conformant Varying
 *     * Pointer
 *  * Primitive
 *     * boolean
 *     * byte
 *     * unsigned byte
 *     * short
 *     * unsigned short
 *     * long
 *     * unsigned long
 *     * hyper
 *     * unsigned hyper
 *     * float
 *     * unsigned float
 *     * double
 *     * unsigned double
 */
public interface DataType {

}
