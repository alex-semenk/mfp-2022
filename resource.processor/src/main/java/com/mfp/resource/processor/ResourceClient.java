package com.mfp.resource.processor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "resource", url = "http://localhost:8081/")
public interface ResourceClient {

    @Retryable(maxAttempts=2, backoff=@Backoff(delay=100, maxDelay=300))
    @RequestMapping(method = RequestMethod.GET, value = "/resources/{id}", produces = "audio/mpeg")
    byte[] getResource(@PathVariable("id") Long resourceId);

}
