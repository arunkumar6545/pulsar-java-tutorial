package tutorial.functions.sdk;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

import java.util.Arrays;
import java.util.List;

public class WordCountFunction implements Function<String, Void> {
    @Override
    public Void process(String input, Context context) {
        List<String> words = Arrays.asList(input.split(" "));

        words.forEach(word -> context.incrCounter(word, 1));

        return null;
    }
}
