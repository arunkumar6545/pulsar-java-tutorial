package tutorial;

import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Reader;
import org.apache.pulsar.client.api.ReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ReaderTutorial {
    private static final Logger log = LoggerFactory.getLogger(ReaderTutorial.class);
    private static final String SERVICE_URL = "pulsar://localhost:6650";
    private static final String TOPIC_NAME = "tutorial-topic";

    public static void main(String[] args) throws IOException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .build();

        ReaderBuilder<byte[]> readerBuilder = client.newReader()
                .topic(TOPIC_NAME);

        Reader<byte[]> earliestReader = readerBuilder
                .startMessageId(MessageId.earliest)
                .create();

        Reader<byte[]> latestReader = readerBuilder
                .startMessageId(MessageId.latest)
                .create();

//        log.info("Reading messages from earliest ({})", MessageId.earliest);
//        do {
//            Message<byte[]> msg = earliestReader.readNext();
//            log.info("1 Message received with ID {}", msg.getMessageId());
//
//            if (earliestReader.hasReachedEndOfTopic()) {
//                log.info("Done reading from earliest");
//                earliestReader.close();
//            }
//        } while (!earliestReader.hasReachedEndOfTopic());


        log.info("Reading messages from latest ({})", MessageId.latest);
        do {
            Message<byte[]> msg = latestReader.readNext();
            log.info("2 Message received with ID {}", msg.getMessageId());

            if (latestReader.hasReachedEndOfTopic()) {
                log.info("Done reading from latest");
                latestReader.close();
            }
        } while (!latestReader.hasReachedEndOfTopic());

        client.close();
    }
}
