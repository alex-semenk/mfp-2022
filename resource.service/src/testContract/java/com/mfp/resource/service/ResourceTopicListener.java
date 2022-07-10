package com.mfp.resource.service;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import au.com.dius.pact.provider.MessageAndMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

@TestComponent
public class ResourceTopicListener {

    @Value("${test.kafka.consumer.timeout}")
    private long timeout;

    private final BlockingQueue<MessageAndMetadata> queue = new LinkedBlockingQueue<>();

    @KafkaListener(topics = "${kafka.resource.topic.name}")
    public void receive(@Payload String message, @Headers Map<String, Object> headers) throws InterruptedException {
        queue.put(new MessageAndMetadata(message.getBytes(), headers));
    }

    public MessageAndMetadata poolMessageAndMetadata() throws InterruptedException {
        return queue.poll(timeout, TimeUnit.MILLISECONDS);
    }
}