package com.mfp.resource.processor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "song.service", url = "${mfp.song-service.url}")
public interface SongServiceClient {

    @PostMapping("/songs")
    @Retryable(maxAttempts=5, backoff=@Backoff(delay=100, maxDelay=300), recover = "saveSongFallback")
    SongMetadata saveSong(SongMetadata song);

    @Recover
    default SongMetadata saveSongFallback(Exception e, SongMetadata song) {
        var log = org.slf4j.LoggerFactory.getLogger(SongServiceClient.class);
        log.error("Failed to save song metadata: " + song, e);
        return song;
    }

}
