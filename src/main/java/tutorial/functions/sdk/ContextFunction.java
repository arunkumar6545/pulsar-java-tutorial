package tutorial.functions.sdk;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;

public class ContextFunction implements Function<String, Void> {
    private void displayFunctionInfo(Context context) {
        Logger LOG = context.getLogger();
        LOG.info("Function info:\n==============");
        LOG.info("Function name:      {}", context.getFunctionName());
        LOG.info("Function tenant:    {}", context.getTenant());
        LOG.info("Function namespace: {}", context.getNamespace());
        LOG.info("Function ID:        {}", context.getFunctionId());
        LOG.info("Function version:   {}", context.getFunctionVersion());
        LOG.info("Instance ID:        {}", context.getInstanceId());
        LOG.info("Message ID:         {}", new String(context.getMessageId()));
        LOG.info("Output topic:       {}", context.getOutputTopic());
        context.getUserConfigMap().forEach((k, v) -> LOG.info("User config: { key: {}, value: {} }", k, v));
        LOG.info("==============\n\n");
    }

    @Override
    public Void process(String input, Context context) {
        displayFunctionInfo(context);

        return null;
    }
}
