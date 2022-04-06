package com.mfp.resource.processor;

public class ResourceProcessorException extends RuntimeException {

    public ResourceProcessorException(String message, Exception reason) {
        super(message, reason);
    }

}
