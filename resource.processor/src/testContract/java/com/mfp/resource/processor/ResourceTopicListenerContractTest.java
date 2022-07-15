package com.mfp.resource.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "resource.service.publisher", providerType = ProviderType.ASYNCH)
public class ResourceTopicListenerContractTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Pact(consumer = "resource.processor.listener")
    public MessagePact validResourceIdMessageFromResourceService(MessagePactBuilder builder) {
        return builder
            .expectsToReceive("Resource with ID 1")
            .withContent(new PactDslJsonBody().numberValue("id", 1L))
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "validResourceIdMessageFromResourceService")
    public void testValidResourceIdMessageFromResourceService(List<Message> messages) {
        // String messageContent = new String(messages.get(0).contentsAsBytes());
        String messageContent = messages.get(0).contentsAsString();
        assertDoesNotThrow(() -> {
            ResourceId resourceId = objectMapper.readValue(messageContent, ResourceId.class);
            assertThat(resourceId.getId()).isEqualTo(1L);
        });
    }

}
