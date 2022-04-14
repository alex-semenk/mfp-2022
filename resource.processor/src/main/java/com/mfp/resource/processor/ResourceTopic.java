package com.mfp.resource.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ResourceTopic {

    @Autowired
    private ResourceClient resourceClient;

    @Autowired
    private SongClient songClient;

    @Autowired
    private ResourceProcessorService resourceProcessorService;

    @KafkaListener(topics = "resources")
    public void resourceUploaded(@Payload ResourceId resourceId) {
        byte[] resourceData = resourceClient.getResource(resourceId.getId());
        SongMetadata songMetadata = resourceProcessorService.readSongMetadata(new ByteArrayResource(resourceData));
        songMetadata.setResourceId(resourceId.getId());
        songMetadata = songClient.saveSong(songMetadata);
        System.out.println("SONG ID: " + songMetadata.getId());
    }

}
