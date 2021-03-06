package tutorial.subscriptions;

import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class SharedSubscriptionTutorial {
    private static final Logger LOG = LoggerFactory.getLogger(SharedSubscriptionTutorial.class);
    private static final String SERVICE_URL = "pulsar://localhost:6650";
    private static final String TOPIC_NAME = "tutorial-topic";
    private static final String SUBSCRIPTION_NAME = "tutorial-subscription";
    private static final SubscriptionType SUBSCRIPTION_TYPE = SubscriptionType.Shared;

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .build();

        Producer<byte[]> producer = client.newProducer()
                .topic(TOPIC_NAME)
                .create();

        ConsumerBuilder<byte[]> consumerBuilder = client.newConsumer()
                .topic(TOPIC_NAME)
                .subscriptionName(SUBSCRIPTION_NAME)
                .subscriptionType(SUBSCRIPTION_TYPE);

        IntStream.range(0, 4).forEach(i -> {
           try {
               consumerBuilder
                       .consumerName(String.format("consumer-%d", i))
                       .subscribe();
           } catch (PulsarClientException e) {
               LOG.error(e.getMessage());
           }
        });

        IntStream.range(0, 999).forEach(i -> {

            try {
                producer.send(String.format("message-%d", i).getBytes());
            } catch (PulsarClientException e) {
                LOG.error(e.getMessage());
            }
        });

        LOG.info("Tutorial finished!");
        System.exit(0);
    }
}
