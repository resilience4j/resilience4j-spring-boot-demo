# Spring Boot Demo of javaslang-circuitbreaker

This demo which shows how to use the fault tolerance library [javaslang-circuitbreaker](https://github.com/RobWin/javaslang-circuitbreaker) in a Spring Boot application.

The demo shows how to use a Spring AOP Aspect and a custom `CircuitBreaker` annotation to make your Spring Boot application more fault tolerant. You can either annotate a class in order to protect all public methods or just some specific methods.

Spring Boot Actuator health information can be used to check the status of your running application. It is often used by monitoring software to alert someone if a production system has serious issued. This demo publishes the status and metrics of all CircuiBreakers via a custom HealthIndicator. A closed CircuitBreaker state is mapped to UP, an open state to DOWN and a half-open state to UNKOWN.

You can configure your CircuitBreakers in the `application.yml` config file.
