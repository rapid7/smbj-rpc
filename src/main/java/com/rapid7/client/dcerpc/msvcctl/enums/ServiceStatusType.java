/***************************************************************************
 * COPYRIGHT (C) 2004-2015, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.msvcctl.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * This enumeration is a representation of the service status types.
 */
public enum ServiceStatusType
{
   SERVICE_PAUSED(0x00000007),
   SERVICE_RUNNING(0x00000004),
   SERVICE_STOPPED(0x00000001),
   SERVICE_CONTINUE_PENDING(0x00000005),
   SERVICE_PAUSE_PENDING(0x00000006),
   SERVICE_START_PENDING(0x00000002),
   SERVICE_STOP_PENDING(0x00000003),
   ACCESS_MASK(0xF01FF),
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
   ServiceStatusType(int value)
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

   /**
    * Takes an int value and returns the enum that is matched.
    *
    * @param value Value to match
    * @return enum found or UNKNOWN
    */
   public static ServiceStatusType fromInt(int value)
   {
      ServiceStatusType type = MS_TYPEDMAP.get(value);
      if (type == null)
         return UNKNOWN;
      return type;
   }

   /////////////////////////////////////////////////////////
   // Non-Public Fields
   /////////////////////////////////////////////////////////

   private final int m_value;
   private static final Map<Integer, ServiceStatusType> MS_TYPEDMAP = new HashMap<>();

   static
   {
      for (ServiceStatusType type : ServiceStatusType.values())
      {
         MS_TYPEDMAP.put(type.getValue(), type);

      }
   }
}
