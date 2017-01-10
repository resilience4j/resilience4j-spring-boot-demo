package io.github.robwin.service;

import io.github.robwin.connnector.Connector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "businessAService")
public class BusinessAService implements BusinessService {

    private Connector backendAConnector;

    public BusinessAService(@Qualifier("backendAConnector") Connector backendAConnector){
        this.backendAConnector = backendAConnector;
    }

    @Override
    public String failure() {
        return backendAConnector.failure();
    }

    @Override
    public String success() {
        return backendAConnector.success();
    }

    @Override
    public String ignore() {
        return backendAConnector.ignoreException();
    }
}
