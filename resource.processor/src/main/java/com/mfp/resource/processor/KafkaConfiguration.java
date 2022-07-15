package com.mfp.resource.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    /**
     * StringJsonMessageConverter reads message type date from @KafkaListener @Payload parameter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new StringJsonMessageConverter();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
        @Autowired ConsumerFactory<String, String> consumerFactory,
        @Autowired MessageConverter messageConverter) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

}
