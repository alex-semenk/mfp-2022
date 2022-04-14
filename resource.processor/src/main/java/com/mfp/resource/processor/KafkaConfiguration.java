package com.mfp.resource.processor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConfiguration {

    /**
     * StringJsonMessageConverter reads message type date from @KafkaListener @Payload parameter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new StringJsonMessageConverter();
    }

}
