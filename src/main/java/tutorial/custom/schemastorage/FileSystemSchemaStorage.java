package tutorial.custom.schemastorage;

import com.google.common.io.Files;
import org.apache.pulsar.broker.service.schema.SchemaStorage;
import org.apache.pulsar.broker.service.schema.StoredSchema;
import org.apache.pulsar.common.schema.SchemaVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class FileSystemSchemaStorage implements SchemaStorage {
    private static final Logger LOG = LoggerFactory.getLogger(FileSystemSchemaStorage.class);

    private final File rootDirectory;

    public FileSystemSchemaStorage() {
        rootDirectory = Files.createTempDir();
    }

    @Override
    public void start() {
        LOG.info("Creating filesystem schema storage engine with a root directory of {}", rootDirectory.getAbsolutePath());
    }

    @Override
    public SchemaVersion versionFromBytes(byte[] bytes) {
        return null;
    }

    @Override
    public CompletableFuture<SchemaVersion> delete(String key) {
        return null;
    }

    @Override
    public CompletableFuture<StoredSchema> get(String key, SchemaVersion version) {
        return null;
    }

    @Override
    public CompletableFuture<SchemaVersion> put(String key, byte[] value, byte[] hash) {
        return null;
    }

    @Override
    public void close() {}
}
