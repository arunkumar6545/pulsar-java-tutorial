package tutorial.usecases;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MessageQueueTutorial {
    private static final Logger LOG = LoggerFactory.getLogger(MessageQueueTutorial.class);
    private static final String SERVICE_URL = "pulsar://localhost:6650";
    private static final String TOPIC_NAME = "tutorial-topic";
    private static final String SUBSCRIPTION_NAME = "tutorial-subscription";
    private static final int NUM_CONSUMERS = 2;
    private static final int NUM_MSGS = 20;

    static HashSet<String> found = new HashSet<>();

    private static class MQListener implements MessageListener<byte[]> {
        private long serialVersionUID = 1;
        private int i = 0;

        @Override
        public void received(Consumer<byte[]> consumer, Message<byte[]> msg) {
            i++;
            LOG.info("Message processed by consumer {}", consumer.getConsumerName());
            LOG.info("Successfully received message with an ID of \"{}\" and a payload of \"{}\"",
                    new String(msg.getMessageId().toByteArray()),
                    new String(msg.getData()));
            String msg1 = new String(msg.getData());
            if(found.contains(msg1 ))
                LOG.info("found");
            found.add(msg1);
            try {
                if(i == 5){
                    LOG.info("negative ack");


                    consumer.negativeAcknowledge(msg.getMessageId());
                }else
                consumer.acknowledge(msg);
            } catch (PulsarClientException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .build();
        LOG.info("Setting up a producer for the {} topic", TOPIC_NAME);

        Producer<byte[]> producer = client.newProducer()
                .topic(TOPIC_NAME)
                .create();

        LOG.info("Successfully set up a producer for the {} topic", TOPIC_NAME);

        IntStream.range(0, NUM_MSGS).forEach(i -> {
            try {
                MessageId msgId = producer.send(String.format("Message number %d", i).getBytes());
                LOG.info("produced id {}", msgId.toString());
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        });
        ConsumerBuilder<byte[]> consumerBuilder = client.newConsumer().negativeAckRedeliveryDelay(1, TimeUnit.SECONDS)
                .topic(TOPIC_NAME)
                .subscriptionName(SUBSCRIPTION_NAME)
                .subscriptionType(SubscriptionType.Shared)
                .receiverQueueSize(1);

        LOG.info("Setting up an ensemble of {} consumers to process messages on the topic {}", NUM_CONSUMERS, TOPIC_NAME);

        IntStream.range(0, NUM_CONSUMERS).forEach(i -> {
            try {
                consumerBuilder
                        .consumerName(String.format("mq-consumer-%d", i))
                        .messageListener(new MQListener())
                        .subscribe();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        });

        LOG.info("Successfully set up an ensemble of {} consumer to process messages on the topic {}", NUM_CONSUMERS, TOPIC_NAME);



        LOG.info("Successfully ran the message queue tutorial!");

    }
}
