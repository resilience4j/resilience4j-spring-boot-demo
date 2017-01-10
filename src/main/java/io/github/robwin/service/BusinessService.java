package io.github.robwin.service;

import javaslang.control.Try;

public interface BusinessService {
    String failure();

    String success();

    String ignore();

    Try<String> methodWithRecovery();
}
