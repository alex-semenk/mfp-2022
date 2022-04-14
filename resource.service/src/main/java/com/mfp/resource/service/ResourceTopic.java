package com.mfp.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class ResourceTopic {

    @Value("${kafka.resource.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, ResourceId> kafkaTemplate;

    public void sendMessage(Long resourceId) {
        kafkaTemplate.send(topicName, new ResourceId(resourceId));
    }

}
