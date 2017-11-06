/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
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
   /**
    * All NDR data types are aligned to a static value based on factors unique to the type.
    *
    * Primitives:
    * NDR enforces NDR alignment of primitive data; that is, any primitive of size n octets is aligned
    * at a octet stream index that is a multiple of n. (In this version of NDR, n is one of {1, 2, 4, 8}.)
    * An octet stream index indicates the number of an octet in an octet stream when octets are numbered,
    * beginning with 0, from the first octet in the stream. Where necessary, an alignment gap,
    * consisting of octets of unspecified value, precedes the representation of a primitive.
    * The gap is of the smallest size sufficient to align the primitive.
    *
    * Constructs:
    * NDR enforces NDR alignment of structured data. As with primitive data types, an alignment, n,
    * is determined for the structure. Where necessary, an alignment gap of octets of unspecified value precedes
    * the data in the NDR octet stream. This gap is the smallest size sufficient to align the first field of the
    * structure on an NDR octet stream index of n.
    * The rules for calculating the alignment of constructed types are as follows:
    *    * If a conformant structure-that is, a conformant or conformant varying array,
    *    or a structure containing a conformant or conformant varying array-is embedded in the constructed type,
    *    and is the outermost structure-that is, is not contained in another structure-then the size information
    *    from the contained conformant structure is positioned so that it precedes both the containing constructed
    *    type and any alignment gap for the constructed type. (See Structures Containing Arrays for information
    *    about structures containing arrays.) The size information is itself aligned according to the alignment
    *    rules for primitive data types. (See Alignment of Primitive Types .) The data of the constructed type is
    *    then aligned according to the alignment rules for the constructed type. In other words, the size information
    *    precedes the structure and is aligned independently of the structure alignment.
    *    * The alignment of a structure in the octet stream is the largest of the alignments of the fields it contains.
    *    These fields may also be constructed types. The same alignment rules apply recursively to nested constructed types.
    *    The fields within the structure are aligned according to the following rules:
    *    * Scalar primitives are aligned according to the rules in Alignment of Primitive Types .
    *    * Pointer alignment is always modulo 4.
    *    * Structure alignment is determined by recursive application of these rules.
    *    * Array alignment is the largest alignment of the array element type and the size information type, if any.
    *    * Union alignment is the largest alignment of the union discriminator and all of the union arms.
    *
    * The above definitions of union alignment and array alignment apply only to the calculation the NDR alignment
    * of a structure and do not apply to the actual NDR alignment of a union or an array.
    * For example, the NDR alignment of a union is determined by the tag type and the arm actually to be transmitted,
    * not the largest of the union arms. Similarly, the NDR alignment of an array is determined by the element type
    * alignment, which would be the largest arm of the union in the case of an array of unions.
    *
    * @return The alignment of the NDR type.
    */
   Alignment getAlignment();
}
