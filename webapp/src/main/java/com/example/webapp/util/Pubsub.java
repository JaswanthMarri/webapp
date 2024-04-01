package com.example.webapp.util;

import com.example.webapp.service.UserService;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.gson.JsonObject;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.protobuf.ByteString;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public final class Pubsub {



    // Inject Publisher bean
//    @Autowired
//    private Publisher publisher;
    @Autowired private UserService userService;

    @Value("${pubsub.topic}")
    private String topic;
    @Value("${gcp.prjt}")
    private String prjtID;

    // Method to publish message to Pub/Sub
    public void publishMessage(String firstName, String userEmail) throws Exception {
        ProjectTopicName topicName = ProjectTopicName.of(prjtID, topic);

        Credentials credentials = GoogleCredentials.getApplicationDefault();

        // Initialize a Pub/Sub client with the provided credentials
        Publisher publisher = Publisher.newBuilder(topicName)
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();


        // Create message payload
        JsonObject messagePayload = new JsonObject();
        messagePayload.addProperty("first_name", firstName);
        messagePayload.addProperty("user_email", userEmail);
        // Add other relevant information as needed

        // Publish message
        ByteString byteString = ByteString.copyFromUtf8(messagePayload.toString());
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(byteString).build();
        publisher.publish(pubsubMessage);
    }


}
