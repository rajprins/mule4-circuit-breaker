/**
*
* Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
*
* The software in this package is published under the terms of the CPAL v1.0
* license, a copy of which has been included with this distribution in the
* LICENSE.md file.
*/
package org.mule.extension.circuitbreaker.api.util;

import org.mule.runtime.api.store.ObjectStore;
import org.mule.runtime.api.store.ObjectStoreException;
import org.mule.runtime.api.store.ObjectStoreManager;

public class ObjectStoreHelper {

   private ObjectStore<String> objectStore;

   public ObjectStoreHelper(ObjectStoreManager objectStoreManager) {
      objectStore = objectStoreManager.getDefaultPartition();
   }

   /**
    * Get synchronously property from the Object Store for given circuit
    * 
    * @param propertyName       Name of the property to extract
    * @param defaultValue       Default value to return in case no value found
    * @param circuitBreakerName Circuit Breaker's name
    * 
    * @return
    */
   synchronized public String get(String propertyName, String defaultValue, String circuitBreakerName) {

      String value = defaultValue;
      try {
         value = objectStore.retrieve(String.format("%s.%s", circuitBreakerName, propertyName));
      }
      catch (ObjectStoreException e) {
      }

      return value;
   }

   /**
    * Synchronously set new property into the Object Store for given Circuit
    * 
    * @param propertyName Name of the property to insert
    * @param value        Value related to the property
    * @param circuitName  Circuit Breaker's name
    */
   synchronized public void set(String propertyName, String value, String circuitName) {
      String key = String.format("%s.%s", circuitName, propertyName);

      try {
         if (objectStore.contains(key))
            objectStore.remove(key);

         objectStore.store(key, value);
      }
      catch (ObjectStoreException e) {
      }
   }
}