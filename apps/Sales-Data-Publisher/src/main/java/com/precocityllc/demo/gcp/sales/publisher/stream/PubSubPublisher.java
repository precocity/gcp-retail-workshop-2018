package com.precocityllc.demo.gcp.sales.publisher.stream;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PubSubPublisher {
    private Logger log = LogManager.getLogger(getClass());

    private String topic;
    private static Publisher pubsubPublisher;

    public PubSubPublisher(String topic) throws Exception {
        this.topic = topic;
        log.info("Configuring publisher to use {}", topic);
        pubsubPublisher = Publisher.newBuilder(topic).build();

    }


    public void publish(final List<String> messages) {
        List<ApiFuture<String>> responses = new ArrayList<>();
        for (String message : messages) {
            PubsubMessage psm = PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(message)).build();
            responses.add(pubsubPublisher.publish(psm));
            try {
                for (ApiFuture<String> response : responses) {
                    //wait til all the current messages are published
                    response.get();
                }
            } catch (InterruptedException e) {
                log.error("error", e);
            } catch (ExecutionException e) {
                log.error("error", e);
            }
        }
    }

}
