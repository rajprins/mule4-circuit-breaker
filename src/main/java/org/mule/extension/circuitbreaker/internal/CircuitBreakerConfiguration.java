/**
*
* Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
*
* The software in this package is published under the terms of the CPAL v1.0
* license, a copy of which has been included with this distribution in the
* LICENSE.md file.
*/
package org.mule.extension.circuitbreaker.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;


/**
 * This class represents an extension configuration.
 * Values set in this class are commonly used across multiple operations since 
 * they represent something core from the extension.
 *
 * @Author Roy Prins
 *  
 */
@Operations(CircuitBreakerOperations.class)
public class CircuitBreakerConfiguration {
   
   /**
    * Number of failures before the circuit is opened.
    */
   @Parameter
   @Optional(defaultValue = "3")
   @Placement(order = 1)
   @DisplayName("Failure threshold")
   @Summary("Number of failures before circuit is opened")
   private int threshold;

   /**
    * Period of time in milliseconds for the circuit to stay open.
    */
   @Parameter
   @Optional(defaultValue = "5000")
   @Placement(order = 2)
   @DisplayName("Time out (ms)")
   @Summary("Time in milliseconds for the circuit to remain open before continuing")
   private int timeout;

   /**
    * Unique name of the Circuit Breaker
    * No default value
    */
   @Parameter
   @Placement(order = 3)
   @DisplayName("Circuit breaker name")
   @Summary("Friendly name for the circuit breaker")
   private String breakerName;

   
   //-------------------------------------------------------------------------
   // Getters & Setters
   //-------------------------------------------------------------------------
   public void setThreashold(int threshold) {
      this.threshold = threshold;
   }

   public void setTimeout(int timeout) {
      this.timeout = timeout;
   }

   public void setBreakerName(String name) {
      this.breakerName = name;
   }

   public int getThreshold() {
      return this.threshold;
   }

   public int getTimeout() {
      return this.timeout;
   }

   public String getBreakerName() {
      return this.breakerName;
   }

   
}