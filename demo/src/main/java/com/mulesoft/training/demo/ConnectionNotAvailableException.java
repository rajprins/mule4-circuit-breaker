package com.mulesoft.training.demo;

public class ConnectionNotAvailableException extends Exception {
   
   private static final long serialVersionUID = 1L;

   public ConnectionNotAvailableException() {
      super("Connection not available");
   }

}
