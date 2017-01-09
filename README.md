# Spring Boot Demo of javaslang-circuitbreaker

This demo which shows how to use the fault-tolerance library [javaslang-circuitbreaker](https://github.com/RobWin/javaslang-circuitbreaker) in a Spring Boot application.

The demo shows how to use a Spring AOP Aspect and a custom `CircuitBreaker` annotation to protect all public methods of a class. You can either annotate a class in order to protect all public methods or just a method.

Health information can be used to check the status of your running application. It is often used by monitoring software to alert someone if a production system goes down. The status and metrics of all CircuiBreakers are published via a custom HealthIndicator and can be monitored via the Health endpoint of Spring Boot Actuator. A closed CircuitBreaker state is mapped to UP, an open state to DOWN and a half-open state to UNKOWN.
You can configure the CircuitBreakers in the config `application.yml` file.
