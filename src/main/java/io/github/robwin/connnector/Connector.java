package io.github.robwin.connnector;

public interface Connector {
    String failure();

    String success();

    String ignoreException();
}
