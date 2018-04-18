package tutorial.functions.sdk;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoutingFunction implements Function<String, Void> {
    private static final Set<String> fruits = Stream.of("apple", "orange", "banana").collect(Collectors.toSet());
    private static final Set<String> veggies = Stream.of("celery", "carrot", "onion").collect(Collectors.toSet());

    @Override
    public Void process(String input, Context context) {
        if (fruits.contains(input)) {
            context.publish("fruits", input);
            context.incrCounter("fruits", 1);
            return null;
        }

        if (veggies.contains(input)) {
            context.publish("veggies", input);
            context.incrCounter("veggies", 1);
            return null;
        }

        context.getLogger().warn("The item {} was neither a fruit nor a vegetable", input);

        return null;
    }
}
