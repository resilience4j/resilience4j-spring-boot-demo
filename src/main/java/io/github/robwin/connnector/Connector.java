package io.github.robwin.connnector;

import io.reactivex.Flowable;

public interface Connector {
    String failure();

    String success();

    String ignoreException();

    Flowable<String> eventStream();
}
