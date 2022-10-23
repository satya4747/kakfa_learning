package com.training.kafkaExample.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {
    
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    
    @Value("${kafka.topic}")
    private String topicName;
    
    @Value("${kafka.topic.partition}")
    private int partition;
    
    @Value("${kafka.topic.replicas}")
    private int isr;
    

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
    
    @Bean
    public NewTopic topic1() {
         return new NewTopic(topicName, partition, (short) isr);
    }
}
