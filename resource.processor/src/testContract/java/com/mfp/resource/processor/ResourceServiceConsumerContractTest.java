package com.mfp.resource.processor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;
import java.util.Map;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "resource.service", hostInterface="localhost", port = "8081")
public class ResourceServiceConsumerContractTest {

    @Autowired
    private ResourceServiceClient resourceServiceClient;

    private final Long testResourceId = 1L;
    private final String testResourceString = "RESOURCE_DATA";
    private final byte[] testResourceData = testResourceString.getBytes();

    @Pact(consumer = "resource.processor")
    public RequestResponsePact getResourcePact(PactDslWithProvider builder) {
        Map<String, Object> params = Map.of("resourceData", Base64.getEncoder().encodeToString(testResourceData));
        return builder
            .given(String.format("Resource with ID %d", testResourceId), params)
            .uponReceiving("GET request to get resource data")
            .path("/resources/" + testResourceId)
            .method("GET")
            .willRespondWith()
            .status(200)
            .headers(Map.of("Content-Type", "audio/mpeg"))
            .body(testResourceString)
            .toPact();
    }

    @Test
    @PactTestFor
    public void givenResourceData_whenSendGetRequest_shouldReturnByteData() {
        // when
        byte[] receivedResourceData = resourceServiceClient.getResource(testResourceId);
        // then
        assertThat(receivedResourceData).isEqualTo(testResourceData);
    }

}
