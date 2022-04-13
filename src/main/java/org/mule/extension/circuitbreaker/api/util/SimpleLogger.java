/**
*
* Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
*
* The software in this package is published under the terms of the CPAL v1.0
* license, a copy of which has been included with this distribution in the
* LICENSE.md file.
*/
package org.mule.extension.circuitbreaker.api.util;

public class SimpleLogger {
   private String className = "<UNKNOWN>";

   public SimpleLogger() throws Exception {
      throw new Exception("Logger error: missing parameter. No classname specified.");
   }

   public SimpleLogger(String className) {
      this.className = className;
   }

   public void info(String message) {
      System.out.println(java.time.LocalDateTime.now() + " INFO " + className + " - " + message);
   }

   public void warn(String message) {
      System.out.println(java.time.LocalDateTime.now() + " WARNING " + className + " - " + message);
   }

   public void error(String message) {
      System.out.println(java.time.LocalDateTime.now() + " ERROR " + className + " - " + message);
   }

   public void debug(String message) {
      System.out.println(java.time.LocalDateTime.now() + " DEBUG " + className + " - " + message);
   }
}