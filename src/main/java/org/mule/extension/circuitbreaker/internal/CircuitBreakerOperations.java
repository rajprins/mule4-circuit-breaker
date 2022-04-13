/**
 *
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */
package org.mule.extension.circuitbreaker.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import javax.inject.Inject;

import org.mule.extension.circuitbreaker.api.exceptions.CircuitOpenException;
import org.mule.extension.circuitbreaker.api.util.ObjectStoreHelper;
import org.mule.extension.circuitbreaker.api.util.SimpleLogger;
import org.mule.runtime.api.store.ObjectStoreManager;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 *
 *  @Author Roy Prins
 * 
 */
public class CircuitBreakerOperations {
   
   private static final SimpleLogger logger = new SimpleLogger(CircuitBreakerOperations.class.getCanonicalName());


   @Inject
   private ObjectStoreManager objectStoreManager;

   public CircuitBreakerOperations() {
      logger.info("*** CircuitBreakerOperations initialized ***");
   }

   /**
    * Record one more failure by saving it in Object Store
    * 
    * @param configuration Circuit Breaker configuration
    */
   @MediaType(value = ANY, strict = false)
   public void recordFailure(@Config CircuitBreakerConfiguration configuration) {
      ObjectStoreHelper objectStore = new ObjectStoreHelper(objectStoreManager);
      Integer failureCount = 0;
      synchronized (failureCount) {
         failureCount = Integer.parseInt(objectStore.get("failureCount", "0", configuration.getBreakerName()));
         int nextFailureCount = failureCount + 1;

         logger.info("*** Failure count: " + nextFailureCount + " ***");
         objectStore.set("failureCount", String.valueOf(nextFailureCount), configuration.getBreakerName());

         if ((nextFailureCount) == configuration.getThreshold()) {
            objectStore.set("openedAt", String.valueOf(System.currentTimeMillis()), configuration.getBreakerName());
         }
      }
   }

   /**
    * Filter the message if the failure threshold has been met. 
    * In that case, the Circuit Breaker has been opened and no new messages are allowed to pass further.
    * 
    * @param configuration Circuit Breaker configuration
    * 
    * @throws OpenedCicuitException The exception is thrown when the Circuit Breaker is opened
    */
   @MediaType(value = ANY, strict = false)
   public void filter(@Config CircuitBreakerConfiguration configuration) throws CircuitOpenException {
      
      ObjectStoreHelper objectStore = new ObjectStoreHelper(objectStoreManager);
      long openedAt = Long.parseLong(objectStore.get("openedAt", "0", configuration.getBreakerName()));

      if (openedAt != 0 && (System.currentTimeMillis() - openedAt) > configuration.getTimeout()) {
         logger.info("*** Timeout expired. Resetting failure count ***");
         objectStore.set("openedAt", "0", configuration.getBreakerName());
         objectStore.set("failureCount", "0", configuration.getBreakerName());
      }
      else if (openedAt == 0) {
         // do nothing
      }
      else {
         logger.info("*** Failure threshold reached, processing will temporarily be suspended ***");
         throw new CircuitOpenException();
      }
   }
}
