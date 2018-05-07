package tutorial.custom.schemastorage;

import org.apache.pulsar.broker.PulsarService;
import org.apache.pulsar.broker.service.schema.SchemaStorage;
import org.apache.pulsar.broker.service.schema.SchemaStorageFactory;

import javax.validation.constraints.NotNull;

public class FileSystemSchemaStorageFactory implements SchemaStorageFactory {
    @Override
    @NotNull
    public SchemaStorage create(PulsarService pulsarService) throws Exception {
        return new FileSystemSchemaStorage();
    }
}
