package com.mfp.resource.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceTopicListener {

    @Autowired
    private ResourceServiceClient resourceServiceClient;

    @Autowired
    private SongServiceClient songServiceClient;

    @Autowired
    private ResourceProcessorService resourceProcessorService;

    @KafkaListener(topics = "resources", groupId = "resource-processor")
    @RetryableTopic(
        attempts = "3",
        backoff = @Backoff(delay = 500, multiplier = 2.0),
        topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    public void resourceUploaded(@Payload ResourceId resourceId) {
        byte[] resourceData = resourceServiceClient.getResource(resourceId.getId());
        SongMetadata songMetadata = resourceProcessorService.readSongMetadata(new ByteArrayResource(resourceData));
        songMetadata.setResourceId(resourceId.getId());
        songMetadata = songServiceClient.saveSong(songMetadata);
        log.info("SONG ID: " + songMetadata.getId());
    }

    @DltHandler
    public void processDlt(
        @Payload String payload,
        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
        @Header(KafkaHeaders.EXCEPTION_FQCN) String exceptionFqcn,
        @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage,
        @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String exceptionStacktrace) {
        log.error(String.format(
            "[DLT]: Topic: %s Payload: %s Class: %s Message: %s Stacktrace: %s",
            topic, payload, exceptionFqcn, exceptionMessage, exceptionStacktrace
        ));
    }

}
