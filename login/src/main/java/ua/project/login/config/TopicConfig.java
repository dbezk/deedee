package ua.project.login.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TopicConfig {

    @Value(value = "${kafka.bootstrap-server}")
    private String bootstrapAddress;

    @Value(value = "${kafka.auth.done.topic.name}")
    private String authDoneTopicName;

    @Bean
    public NewTopic authDoneTopic() {
        return TopicBuilder
                .name(authDoneTopicName)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(config);
    }

}
