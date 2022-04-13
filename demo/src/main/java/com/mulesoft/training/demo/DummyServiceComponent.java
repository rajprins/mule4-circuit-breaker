package com.mulesoft.training.demo;

public class DummyServiceComponent {
   
   private final long duration_in_ms = 1000;
   
   public DummyServiceComponent() {
      System.out.println("DummyServiceComponent initialized.");
   }
   
   public String mockServiceCall() throws Exception {
      
      //Emulate some processing time
      Thread.sleep(duration_in_ms);      
      
      //Throw an exception to simulate a connection error
      throw new ConnectionNotAvailableException(); 
      
   }

}
