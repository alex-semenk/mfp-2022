package com.mfp.resource.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@TestComponent
public class ResourceTopicConsumer<K, V> {

    @Value("${test.kafka.consumer.timeout}")
    private long timeout;

    private final BlockingQueue<ConsumerRecord<K, V>> queue = new LinkedBlockingQueue<>();

    @KafkaListener(topics = "${kafka.resource.topic.name}")
    public void receive(ConsumerRecord<K, V> consumerRecord) throws InterruptedException {
        queue.put(consumerRecord);
    }

    public ConsumerRecord<K, V> poolConsumerRecord() throws InterruptedException {
        return queue.poll(timeout, TimeUnit.MILLISECONDS);
    }
}