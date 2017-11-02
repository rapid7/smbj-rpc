/***************************************************************************
 * COPYRIGHT (C) 2004-2015, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.msvcctl.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * This enumeration is a representation of the service control types.
 */
public enum ServiceControl
{
   STOP(0x00000001),
   PAUSE(0x00000002),
   CONTINUE(0x00000003),
   INTERROGATE(0x00000004),
   PARAM_CHANGE(0x00000006);

   /////////////////////////////////////////////////////////
   // Public Methods
   /////////////////////////////////////////////////////////

   /**
    * This constructor initializes a service action enum type
    * with the provided integer representation for that type.
    *
    * @param value The integer representation for the enum type
    */
   ServiceControl(int value)
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
