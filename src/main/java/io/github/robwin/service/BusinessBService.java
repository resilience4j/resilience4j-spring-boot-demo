package io.github.robwin.service;

import io.github.robwin.connnector.Connector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "businessBService")
public class BusinessBService implements BusinessService  {

    private Connector backendBConnector;

    public BusinessBService(@Qualifier("backendBConnector") Connector backendbConnector){
        this.backendBConnector = backendbConnector;
    }

    public String failure() {
        return backendBConnector.failure();
    }

    public String success() {
        return backendBConnector.success();
    }

    @Override
    public String ignore() {
        return backendBConnector.ignoreException();
    }
}
