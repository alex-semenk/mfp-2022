package com.mfp.resource.processor;

import static org.springframework.kafka.retrytopic.TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceTopic {

    @Autowired
    private ResourceClient resourceClient;

    @Autowired
    private SongClient songClient;

    @Autowired
    private ResourceProcessorService resourceProcessorService;

    @KafkaListener(topics = "resources")
    @RetryableTopic(
        attempts = "3",
        backoff = @Backoff(delay = 500, multiplier = 2.0),
        topicSuffixingStrategy = SUFFIX_WITH_INDEX_VALUE)
    public void resourceUploaded(@Payload ResourceId resourceId) {
        byte[] resourceData = resourceClient.getResource(resourceId.getId());
        SongMetadata songMetadata = resourceProcessorService.readSongMetadata(new ByteArrayResource(resourceData));
        songMetadata.setResourceId(resourceId.getId());
        songMetadata = songClient.saveSong(songMetadata);
        log.info("SONG ID: " + songMetadata.getId());
    }

    @DltHandler
    public void processDlt(@Payload String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("[DLT] Topic: " + topic + " Payload: " + payload);
    }

}
