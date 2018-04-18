package tutorial.functions.javanative;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class WordCountFunction implements Function<String, Map<String, Integer>> {
    private Map<String, Integer> counts;

    public WordCountFunction() {
        this.counts = new HashMap<>();
    }

    @Override
    public Map<String, Integer> apply(String input) {
        List<String> words = Arrays.asList(input.split(" "));

        words.forEach(word -> {
            int count = (null == counts.get(word)) ? 1 : counts.get(word) + 1;
            counts.put(word, count);
        });

        return counts;
    }
}
