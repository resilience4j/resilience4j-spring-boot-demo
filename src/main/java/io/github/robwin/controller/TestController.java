package io.github.robwin.controller;

import io.github.robwin.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class TestController {

    private TestService testService;

    public TestController(TestService testService){
        this.testService = testService;
    }

    @GetMapping(value = "helloworld")
    public String helloWorld(){
        return testService.sayHelloWorld();
    }
}
