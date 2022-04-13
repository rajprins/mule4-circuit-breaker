# Circuit Breaker Module Extension

## Introduction: the Circuit Breaker design pattern
The basic idea behind the circuit breaker is very simple. You wrap a protected function call in a circuit breaker object, which monitors for failures. Once the failures reach a certain threshold, the circuit breaker trips, and all further calls to the circuit breaker return with an error, without the protected call being made at all. Usually you'll also want some kind of monitor alert if the circuit breaker trips.

# Circuit Breaker mule module
Mule does not have such functionalities, although it has excellent error handling and retry features.
This project is a circuit breaker implementation using the Mule SDK, resulting in an extension (module) that can be installed in Anypoint Studio, and will appear in the Palette.  

This circuit breaker implementation monitors the execution of components in a Mule flow. When everything is working as expected, they are in a closed state. When the number of fails, like timeouts, reaches a specified threshold, circuit breakers will stop processing further requests. We call this the open state. As a result, API clients will receive instant information that something went wrong without waiting for the timeout.  

The circuit is opened for a specified period of time. After timeout occurs, the circuit breaker goes into a half-opened state. Next, the API call will hit the external system/API. After that, the circuit will decide whether to close or open itself.


## Building the module
* Check out the source code from [Github](https://github.com/rajprins/circuit-breaker-module.git)
* Build the module using Maven: `mvn clean compile -DskipTests`
* Install the module in your local Maven repository: `mvn install -DskipTests`

## Module installation
* Open Anypoint Studio 7.
* Create a new Mule 4 project.
* Add this dependency to your application's pom.xml.
```
<depenency>
    <groupId>fd604b43-365c-42e8-810f-733a2b7f411f</groupId>
    <artifactId>circuit-breaker-module</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <classifier>mule-plugin</classifier>
</dependency>
```
The module will appear in the Mule Palette
![](https://raw.githubusercontent.com/rajprins/circuit-breaker-module/main/docs/images/palette.png)

## Usage
* Drag the `filter` component to your Mule flow. Place it **before** any message processors that should be monitored.
* Click the `filter` component. In the properties view, add a new module configuration.
* Fill out the details
![](https://raw.githubusercontent.com/rajprins/circuit-breaker-module/main/docs/images/config.png)
* In the error handling section, place an `On Error Propagete` component.
* Inside the `on error propagate` scope, place the `Record failure` component.

# Demo
A sample application is added to demonstrate how to use the circuit breaker module.
Simply import the project from the `/demo` folder into Anypoint Studio.

## Demo app functionalities
The demo application uses a custom Java component that simulates an outbound connection which failes, and throws an exception.  
![](https://raw.githubusercontent.com/rajprins/circuit-breaker-module/main/docs/images/flow.png)

The error handler catches the exception, and executes the `Record failure` component. After 3 attempts, the circuit breaker will go into "circuit open" state and will temporarily pauze processing. 
After 5 seconds, processing will be resumed.

## Running the demo app
* Import the demo app into Anypoint Studio.
* Run the app.
* Open a browser and enter this URL: http://localhost:8081/test
* Notice the error message.  
![](https://raw.githubusercontent.com/rajprins/circuit-breaker-module/main/docs/images/circuit-closed.png)
* Reload the URL several times until the Circuit Breaker goes into "open" state and displays its own error message.
  ![](https://raw.githubusercontent.com/rajprins/circuit-breaker-module/main/docs/images/circuit-open.png)
* Wait for 5 seconds and reload the URL.
* Notice how the connection error message appears again.
![](https://raw.githubusercontent.com/rajprins/circuit-breaker-module/main/docs/images/circuit-closed.png)