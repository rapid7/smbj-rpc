/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/

package com.rapid7.client.dcerpc.objects;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class TEST_RPC_SID {

   @Test(expectedExceptions = {NullPointerException.class})
   public void  test_marshallPreamble_null() throws IOException {
      new RPC_SID().marshallPreamble(new PacketOutput(new ByteArrayOutputStream()));
   }

   @Test
   public void test_marshallPreamble() throws IOException {
      RPC_SID rpc_sid = new RPC_SID();
      rpc_sid.setSubAuthority(new long[]{5, 10});
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      rpc_sid.marshallPreamble(new PacketOutput(outputStream));
      assertEquals(Hex.toHexString(outputStream.toByteArray()),
                   "02000000");
   }

   @Test(expectedExceptions = {NullPointerException.class})
   public void test_marshallEntity_null() throws IOException {
      new RPC_SID().marshallEntity(new PacketOutput(new ByteArrayOutputStream()));
   }

   @Test
   public void test_marshallEntity() throws IOException {
      RPC_SID rpc_sid = new RPC_SID();
      rpc_sid.setRevision((char) 25);
      rpc_sid.setSubAuthorityCount((char) 2);
      rpc_sid.setIdentifierAuthority(new byte[]{1, 2, 3, 4, 5, 6});
      rpc_sid.setSubAuthority(new long[]{5, 10});
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      rpc_sid.marshallEntity(new PacketOutput(outputStream));
      assertEquals(Hex.toHexString(outputStream.toByteArray()),
                   "1902010203040506050000000a000000");
   }

   @Test
   public void test_marshallEntity_SubAuthorityCountInvalid() throws IOException {
      RPC_SID rpc_sid = new RPC_SID();
      rpc_sid.setRevision((char) 25);
      rpc_sid.setSubAuthorityCount((char) 30);
      rpc_sid.setIdentifierAuthority(new byte[]{1, 2, 3, 4, 5, 6});
      rpc_sid.setSubAuthority(new long[]{5, 10});
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      IllegalArgumentException actual = null;
      try {
         rpc_sid.marshallEntity(new PacketOutput(outputStream));
      } catch (IllegalArgumentException e) {
         actual = e;
      }
      assertNotNull(actual);
      assertEquals(actual.getMessage(), "SubAuthorityCount (30) != SubAuthority[] length (2)");
   }

   @Test
   public void test_marshallDeferrals() throws IOException {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      new RPC_SID().marshallDeferrals(new PacketOutput(outputStream));
      assertEquals(outputStream.toByteArray(), new byte[0]);
   }

   @Test
   public void test_unmarshallPremable() throws IOException {
      ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(
         "02000000"));
      RPC_SID rpc_sid = new RPC_SID();
      rpc_sid.unmarshallPreamble(new PacketInput(bin));
      assertEquals(rpc_sid.getSubAuthority(), new long[2]);
   }

   @Test
   public void test_unmarshallEntity() throws IOException {
      ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(
         "1902010203040506050000000a000000"));
      RPC_SID rpc_sid = new RPC_SID();
      rpc_sid.setSubAuthority(new long[2]);
      rpc_sid.unmarshallEntity(new PacketInput(bin));
      assertEquals(rpc_sid.getRevision(), (char) 25);
      assertEquals(rpc_sid.getSubAuthorityCount(), (char) 2);
      assertEquals(rpc_sid.getIdentifierAuthority(), new byte[]{1, 2, 3, 4, 5, 6});
      assertEquals(rpc_sid.getSubAuthority(), new long[]{5L, 10L});
   }

   @Test
   public void test_unmarshallEntity_SubAuthorityCountInvalid() throws IOException {
      ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(
         "191e010203040506050000000a000000"));
      RPC_SID rpc_sid = new RPC_SID();
      rpc_sid.setSubAuthority(new long[2]);
      IllegalArgumentException actual = null;
      try {
         rpc_sid.unmarshallEntity(new PacketInput(bin));
      } catch (IllegalArgumentException e) {
         actual = e;
      }
      assertNotNull(actual);
      assertEquals(actual.getMessage(), "SubAuthorityCount (30) != SubAuthority[] length (2)");
   }

   @Test
   public void test_unmarshallDeferrals() throws IOException {
      ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode("191e"));
      assertEquals(bin.available(), 2);
      new RPC_SID().unmarshallDeferrals(new PacketInput(bin));
      assertEquals(bin.available(), 2);
   }

   @Test
   public void test_getters() {
      RPC_SID rpc_sid = new RPC_SID();
      assertEquals(rpc_sid.getRevision(), 0);
      assertEquals(rpc_sid.getSubAuthorityCount(), 0);
      assertNull(rpc_sid.getIdentifierAuthority());
      assertNull(rpc_sid.getSubAuthority());
      assertEquals(rpc_sid.getAlignment(), Alignment.FOUR);
   }

   @Test
   public void test_setters() {
      RPC_SID rpc_sid = new RPC_SID();
      rpc_sid.setRevision((char) 200);
      rpc_sid.setSubAuthorityCount((char) 5);
      byte[] identifierAuthority = new byte[]{1, 2};
      rpc_sid.setIdentifierAuthority(identifierAuthority);
      long[] subAuthority = new long[]{2, 5, 7};
      rpc_sid.setSubAuthority(subAuthority);
      assertEquals(rpc_sid.getRevision(), (char) 200);
      assertEquals(rpc_sid.getSubAuthorityCount(), (char) 5);
      assertSame(rpc_sid.getIdentifierAuthority(), identifierAuthority);
      assertSame(rpc_sid.getSubAuthority(), subAuthority);
   }

   @Test
   public void test_hashCode() {
      RPC_SID rpc_sid = new RPC_SID();
      assertEquals(rpc_sid.hashCode(), 923521);
      rpc_sid.setRevision((char) 200);
      assertEquals(rpc_sid.hashCode(), 6881721);
      rpc_sid.setSubAuthorityCount((char) 5);
      assertEquals(rpc_sid.hashCode(), 6886526);
      rpc_sid.setIdentifierAuthority(new byte[]{1, 2});
      assertEquals(rpc_sid.hashCode(), 6917340);
      rpc_sid.setSubAuthority(new long[]{2, 5, 7});
      assertEquals(rpc_sid.hashCode(), 6949215);
   }

   @Test
   public void test_equals() {
      RPC_SID sid1 = new RPC_SID();
      RPC_SID sid2 = new RPC_SID();
      assertEquals(sid1, sid2);
      sid1.setRevision((char) 200);
      assertNotEquals(sid1, sid2);
      sid2.setRevision((char) 200);
      assertEquals(sid1, sid2);
      sid1.setSubAuthorityCount((char) 2);
      assertNotEquals(sid1, sid2);
      sid2.setSubAuthorityCount((char) 2);
      assertEquals(sid1, sid2);
      sid1.setIdentifierAuthority(new byte[]{1, 2});
      assertNotEquals(sid1, sid2);
      sid2.setIdentifierAuthority(new byte[]{1, 2});
      assertEquals(sid1, sid2);
      sid1.setSubAuthority(new long[]{4, 5, 6});
      assertNotEquals(sid1, sid2);
      sid2.setSubAuthority(new long[]{4, 5, 6});
      assertEquals(sid1, sid2);
   }

   @Test
   public void test_toString() {
      RPC_SID rpc_sid = new RPC_SID();
      assertEquals(rpc_sid.toString(), "RPC_SID{Revision:0, SubAuthorityCount:0, IdentifierAuthority:null, SubAuthority: null}");
      rpc_sid.setRevision((char) 200);
      rpc_sid.setSubAuthorityCount((char) 5);
      rpc_sid.setIdentifierAuthority(new byte[]{1, 2});
      rpc_sid.setSubAuthority(new long[]{2, 5, 7});
      assertEquals(rpc_sid.toString(), "RPC_SID{Revision:200, SubAuthorityCount:5, IdentifierAuthority:[1, 2], SubAuthority: [2, 5, 7]}");
   }
}
