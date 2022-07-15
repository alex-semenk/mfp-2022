package com.mfp.resource.service;

import java.util.Base64;
import java.util.Map;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.ActiveProfiles;

@PactBroker
@Provider("resource.service")
@ExtendWith(PactVerificationSpringProvider.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceServiceProviderContractTest {

    @LocalServerPort
    private Integer serverPort;

    @Autowired
    private ResourceService resourceService;

    @BeforeAll
    static void enablePublishingPact() {
        System.setProperty("pact.verifier.publishResults", "true");
    }

    @BeforeEach
    public void setTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", serverPort));
    }

    @TestTemplate
    public void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Resource with ID 1")
    public void createResourceWithId1(Map<String, String> params) {
        byte[] resourceData = Base64.getDecoder().decode(params.get("resourceData"));
        resourceService.uploadResource(new ByteArrayResource(resourceData));
    }

}
