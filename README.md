# Spring Boot Demo of javaslang-circuitbreaker

This is a demo which shows how to use the [javaslang-circuitbreaker](https://github.com/RobWin/javaslang-circuitbreaker) in a Spring Boot application.

The demo shows how to use a Spring AOP Aspect and a custom `CircuitBreaker` annotation to protect all public methods of a class.

The status (and metrics) of all CircuiBreakers are published via a custom HealthIndicator and can be monitored via the Health endpoint of Spring Boot Actuator.
You can configure the CircuitBreakers in the config `application.yml` file.
