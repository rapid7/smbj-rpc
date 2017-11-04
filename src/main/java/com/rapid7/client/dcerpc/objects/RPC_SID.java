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

package com.rapid7.client.dcerpc.objects;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.google.common.primitives.UnsignedBytes;
import com.google.common.primitives.UnsignedInts;
import java.io.IOException;

/**
 * typedef struct _RPC_SID {
 *   unsigned char Revision;
 *   unsigned char SubAuthorityCount;
 *   RPC_SID_IDENTIFIER_AUTHORITY IdentifierAuthority;
 *   [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
 * } RPC_SID,
 *  *PRPC_SID,
 *  *PSID;
 */
public class RPC_SID implements Unmarshallable, Marshallable {
   // <NDR: unsigned char> unsigned char Revision;
   private char revision;
   // <NDR: unsigned char> unsigned char SubAuthorityCount
   private char subAuthorityCount;
   // <NDR: fixed array> RPC_SID_IDENTIFIER_AUTHORITY IdentifierAuthority;
   /*
    * typedef struct _RPC_SID_IDENTIFIER_AUTHORITY {
    *   byte Value[6];
    * } RPC_SID_IDENTIFIER_AUTHORITY;
    */
   private byte[] identifierAuthority;
   // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
   private long[] subAuthority;

   public char getRevision() {
      return revision;
   }

   public void setRevision(char revision) {
      this.revision = revision;
   }

   public char getSubAuthorityCount() {
      return subAuthorityCount;
   }

   public void setSubAuthorityCount(char subAuthorityCount) {
      this.subAuthorityCount = subAuthorityCount;
   }

   public byte[] getIdentifierAuthority() {
      return identifierAuthority;
   }

   public void setIdentifierAuthority(byte[] identifierAuthority) {
      this.identifierAuthority = identifierAuthority;
   }

   public long[] getSubAuthority() {
      return subAuthority;
   }

   public void setSubAuthority(long[] subAuthority) {
      this.subAuthority = subAuthority;
   }

   @Override
   public Alignment getAlignment() {
      // Revision: 1 (size of char)
      // SubAuthorityCount: 1 (size of char)
      // IdentifierAuthority: 1 (size of byte)
      // SubAuthority[]: 4 (size repr: 4, entry repr: 4)
      return Alignment.FOUR;
   }

   @Override
   public void marshallPreamble(PacketOutput out)
      throws IOException {
      // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
      out.writeInt(this.subAuthority.length);
   }

   @Override
   public void marshallEntity(PacketOutput out)
      throws IOException {
      // <NDR: unsigned char> unsigned char Revision;
      out.writeByte(getRevision());
      // <NDR: unsigned char> unsigned char SubAuthorityCount;
      out.writeByte(getSubAuthorityCount());
      // <NDR: fixed array> RPC_SID_IDENTIFIER_AUTHORITY IdentifierAuthority;
      out.write(this.identifierAuthority, 0, 6);

      // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
      for (long subAuthority : this.subAuthority) {
         out.writeInt((int) subAuthority);
      }
      // Align conformant array
      // TODO: This is likely broken for multiple SIDs in a request
      out.align(Alignment.FOUR);
   }

   @Override
   public void marshallDeferrals(PacketOutput out)
      throws IOException {
      // No deferrals
   }

   @Override
   public void unmarshallPreamble(PacketInput in)
      throws IOException {
      // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
      this.subAuthority = new long[in.readInt()];
   }

   @Override
   public void unmarshallEntity(PacketInput in)
      throws IOException {
      // <NDR: unsigned char> unsigned char Revision;
      this.revision = (char) UnsignedBytes.toInt(in.readByte());
      // <NDR: unsigned char> unsigned char SubAuthorityCount;
      this.subAuthorityCount = (char) UnsignedBytes.toInt(in.readByte());
      if (this.subAuthorityCount != this.subAuthority.length) {
         throw new IllegalArgumentException(String.format("subAuthorityCount (%d) != SubAuthority[] length (%d)",
                                                          (int) this.subAuthorityCount, this.subAuthority.length));
      }
      // <NDR: fixed array> RPC_SID_IDENTIFIER_AUTHORITY IdentifierAuthority;
      this.identifierAuthority = new byte[6];
      in.readRawBytes(this.identifierAuthority);
      // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
      for (int i=0; i<this.subAuthority.length; i++) {
         this.subAuthority[i] = UnsignedInts.toLong(in.readInt());
      }
      // TODO: This is likely broken for multiple SIDs in a request
      in.align(Alignment.FOUR);
   }

   @Override
   public void unmarshallDeferrals(PacketInput in)
      throws IOException {
   }
}
