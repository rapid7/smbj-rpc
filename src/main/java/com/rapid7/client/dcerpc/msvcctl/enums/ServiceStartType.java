/***************************************************************************
 * COPYRIGHT (C) 2004-2015, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.msvcctl.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * This enumeration is a representation of the service start types.
 */
public enum ServiceStartType
{
   SYSTEM_START(0x00000001),
   AUTO_START(0x00000002),
   DEMAND_START(0x00000003),
   DISABLED(0x00000004),
   BOOT_START(0x00000000),
   NO_CHANGE(0xffffffff),
   UNKNOWN(-1);

   /////////////////////////////////////////////////////////
   // Public Methods
   /////////////////////////////////////////////////////////

   /**
    * This constructor initializes a service action enum type
    * with the provided integer representation for that type.
    *
    * @param value The integer representation for the enum type
    */
   ServiceStartType(int value)
   {
      this.m_value = value;
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

   /**
    * Takes an int value and returns the enum that is matched.
    *
    * @param value Value to match
    * @return enum found or UNKNOWN
    */
   public static ServiceStartType fromInt(int value)
   {
      ServiceStartType type = MS_TYPEDMAP.get(value);
      if (type == null)
         return UNKNOWN;
      return type;
   }

   /////////////////////////////////////////////////////////
   // Non-Public Fields
   /////////////////////////////////////////////////////////

   private final int m_value;
   private static final Map<Integer, ServiceStartType> MS_TYPEDMAP = new HashMap<>();

   static
   {
      for (ServiceStartType type : ServiceStartType.values())
      {
         MS_TYPEDMAP.put(type.getValue(), type);
      }
   }
}
