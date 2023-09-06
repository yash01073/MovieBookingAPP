package com.moviebookingapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic ticketAvailabilityTopic() {
        return new NewTopic("ticket-availability-topic", 1, (short) 1);
    }

    /*@Bean
    public NewTopic deleteMovieTopic(){
        return new NewTopic("delete-movie-topic",1,(short) 1);
    }
*/
}
