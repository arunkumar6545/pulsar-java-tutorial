package tutorial.functions.sdk;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import tutorial.schema.Tweet;

public class TweetFilterFunction implements Function<Tweet, Tweet> {
    private static final int MAX_TWEET_LENGTH = 140;
    private String filteredWord;

    private boolean tooLong(Tweet tweet) {
        return tweet.getContent().length() > MAX_TWEET_LENGTH;
    }

    private boolean containsFilteredWord(Tweet tweet) {
        return (null == filteredWord) && tweet.getContent().contains(filteredWord);
    }

    @Override
    public Tweet process(Tweet tweet, Context context) {
        context.getUserConfigValue("filtered-word").ifPresent(value -> {
            this.filteredWord = value;
        });

        return (tooLong(tweet) || containsFilteredWord(tweet)) ? null : tweet;
    }
}
