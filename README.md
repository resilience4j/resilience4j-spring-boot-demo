# javaslang-circuitbreaker-demo
javaslang-circuitbreaker DEMO

This is a Spring Boot demo to show how to use the [javaslang-circuitbreaker](https://github.com/RobWin/javaslang-circuitbreaker).

The demo shows how to use Spring AOP and annotations to protect all public methods of a class.

The demo publishes the status of all CircuiBreakers via a custom HealthIndicator.
You can configure the CircuitBreakers in the config `application.yml` file.
