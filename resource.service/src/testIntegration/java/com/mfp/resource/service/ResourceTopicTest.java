package com.mfp.resource.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasKey;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@Import(ResourceTopicConsumer.class)
@EmbeddedKafka
public class ResourceTopicTest {

    @Autowired
    private ResourceTopic resourceTopic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourceTopicConsumer<String, String> resourceTopicConsumer;

    @Test
    public void sendMessage_shouldSendJsonMessageWithResourceId() throws InterruptedException {
        // given
        Long resourceId = 1L;

        // when
        resourceTopic.sendMessage(resourceId);

        // then
        ConsumerRecord<String, String> consumerRecord = resourceTopicConsumer.poolConsumerRecord();
        assertNotNull(consumerRecord);
        assertThat(consumerRecord, hasKey(null));
        assertThat(consumerRecord, hasValue(toJson(new ResourceId(resourceId))));
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
