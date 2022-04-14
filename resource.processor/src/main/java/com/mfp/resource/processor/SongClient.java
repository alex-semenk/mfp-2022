package com.mfp.resource.processor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "song", url = "http://localhost:8082/")
public interface SongClient {

    @Retryable(maxAttempts=5, backoff=@Backoff(delay=100, maxDelay=300), recover = "saveSongFallback")
    @RequestMapping(method = RequestMethod.POST, value = "/songs")
    SongMetadata saveSong(SongMetadata song);

    @Recover
    default SongMetadata saveSongFallback(Exception e, SongMetadata song) {
        return song;
    }

}
