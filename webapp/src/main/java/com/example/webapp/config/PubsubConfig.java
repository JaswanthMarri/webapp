package com.example.webapp.config;

import com.google.cloud.pubsub.v1.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class PubsubConfig {

//    // Define a bean of type Publisher
//    Credentials credentials = GoogleCredentials.getApplicationDefault();
//
//    @Bean
//    public Publisher publisher() throws IOException {
//        // Create and configure the Publisher instance
//        // You may need to provide additional configuration parameters here
//        return Publisher.newBuilder(ProjectTopicName.of(projectId, topicId))
//                .setCredentials(credentials)
//                .build();
//    }
}
