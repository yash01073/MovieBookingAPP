package com.moviebookingapp.config;


import com.moviebookingapp.models.UpdateStatusObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class TestConfig {

    @Bean
    public ConsumerFactory<String, UpdateStatusObject> consumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "up:com.moviebookingapp.models.UpdateStatusObject");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.moviebookingapp.models.UpdateStatusObject");
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<String, UpdateStatusObject>(config, new StringDeserializer(), new JsonDeserializer<>(UpdateStatusObject.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UpdateStatusObject> kafkaLister() {

        ConcurrentKafkaListenerContainerFactory<String, UpdateStatusObject> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setMissingTopicsFatal(false);

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public ProducerFactory<String, UpdateStatusObject> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        config.put(JsonSerializer.TYPE_MAPPINGS, "up:com.moviebookingapp.models.UpdateStatusObject");

        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    public KafkaTemplate<String, UpdateStatusObject> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
