package io.github.robwin.service;

import io.github.robwin.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@CircuitBreaker(backend = "testBackend")
public class TestService {

    public String sayHelloWorld() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
    }
}
