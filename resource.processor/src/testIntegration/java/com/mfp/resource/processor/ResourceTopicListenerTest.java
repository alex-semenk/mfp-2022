package com.mfp.resource.processor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, ports = 9093)
public class ResourceTopicListenerTest {

    @Test
    public void testRetry() throws InterruptedException {
        // given
        // when
        // then
    }
}
