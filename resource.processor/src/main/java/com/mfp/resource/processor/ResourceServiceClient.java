package com.mfp.resource.processor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resource", url = "http://localhost:8081/")
public interface ResourceServiceClient {

    @GetMapping(path = "/resources/{id}", produces = "audio/mpeg")
    @Retryable(maxAttempts=2, backoff=@Backoff(delay=100, maxDelay=300))
    byte[] getResource(@PathVariable("id") Long resourceId);

}
