/***************************************************************************
 * COPYRIGHT (C) 2004-2015, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.msvcctl.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * This enumeration is a representation of the services accepted controls.
 */
public enum ServicesAcceptedControls
{
   SERVICE_ACCEPT_TRIGGEREVENT(0x00000400),
   SERVICE_ACCEPT_TIMECHANGE(0x00000200),
   SERVICE_ACCEPT_PRESHUTDOWN(0x00000100),
   SERVICE_ACCEPT_SESSIONCHANGE(0x00000080),
   SERVICE_ACCEPT_POWEREVENT(0x00000040),
   SERVICE_ACCEPT_HARDWAREPROFILECHANGE(0x00000020),
   SERVICE_ACCEPT_NETBINDCHANGE(0x00000010),
   SERVICE_ACCEPT_PARAMCHANGE(0x00000008),
   SERVICE_ACCEPT_SHUTDOWN(0x00000004),
   SERVICE_ACCEPT_PAUSE_CONTINUE(0x00000002),
   SERVICE_ACCEPT_STOP(0x00000001),
   SERVICE_ACCEPT_NONE(0x00000000),
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
   ServicesAcceptedControls(int value)
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
   public static ServicesAcceptedControls fromInt(int value)
   {
      ServicesAcceptedControls type = MS_TYPEDMAP.get(value);
      if (type == null)
         return UNKNOWN;
      return type;
   }

   /////////////////////////////////////////////////////////
   // Non-Public Fields
   /////////////////////////////////////////////////////////

   private final int m_value;
   private static final Map<Integer, ServicesAcceptedControls> MS_TYPEDMAP = new HashMap<>();

   static
   {
      for (ServicesAcceptedControls type : ServicesAcceptedControls.values())
      {
         MS_TYPEDMAP.put(type.getValue(), type);
      }
   }
}
