package com.mfp.resource.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ResourceTopicConfiguration {

    @Value("${kafka.resource.topic.name}")
    private String topicName;

    @Bean
    public NewTopic resourcesTopic() {
        return new NewTopic(topicName, Optional.empty(), Optional.empty());
    }

}
