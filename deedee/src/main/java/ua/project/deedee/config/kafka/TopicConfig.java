package ua.project.deedee.config.kafka;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicConfig {

    @Value(value = "${kafka.auth.topic.name}")
    String authTopicName;

    @Value(value = "${kafka.bootstrap-server}")
    String bootstrapAddress;

    @Bean
    public NewTopic userAuthTopic() {
        return TopicBuilder.name(authTopicName)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

}
