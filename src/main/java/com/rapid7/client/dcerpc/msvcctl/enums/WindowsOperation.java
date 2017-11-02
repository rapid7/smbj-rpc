/***************************************************************************
 * COPYRIGHT (C) 2010-2015, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/

package com.rapid7.client.dcerpc.msvcctl.enums;

/**
 * This enumeration is a representation of the service types.
 */
public enum WindowsOperation
{
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
   WindowsOperation(String operation)
   {
      m_operationName = operation;
   }

   /**
    * Returns the String value of the enum.
    *
    * @return the service name
    */
   public String getOperationName()
   {
      return m_operationName;
   }

   /////////////////////////////////////////////////////////
   // Non-Public Fields
   /////////////////////////////////////////////////////////

   private String m_operationName;
}
