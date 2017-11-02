/***************************************************************************
 * COPYRIGHT (C) 2004-2015, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/

package com.rapid7.client.dcerpc.msvcctl.enums;

/**
 * This enumeration is a representation of the service manager access level types.
 */
public enum ServiceManagerAccessLevel
{
   CONNECT(0x0001),
   CREATE_SERVICE(0x0002),
   ENUMERATE_SERVICE(0x0004),
   LOCK(0x0008),
   QUERY_LOCK_STATUS(0x0010),
   MODIFY_BOOT_CONFIG(0x0020),
   ALL_ACCESS(0xF003F);

   /////////////////////////////////////////////////////////
   // Public Methods
   /////////////////////////////////////////////////////////

   /**
    * This constructor initializes a service action enum type
    * with the provided integer representation for that type.
    *
    * @param value The integer representation for the enum type
    */
   ServiceManagerAccessLevel(int value)
   {
      m_value = value;
   }

   /**
    * Returns the int value of the enum.
    *
    * @return the int value
    */
   public int getValue()
   {
      return m_value;
   }

   /////////////////////////////////////////////////////////
   // Non-Public Fields
   /////////////////////////////////////////////////////////

   private final int m_value;
}
