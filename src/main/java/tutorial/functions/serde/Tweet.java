package tutorial.functions.serde;

import java.util.Date;

public final class Tweet {
    private final String username;
    private final String content;
    private final Date timestamp;

    public Tweet(String username, String content) {
        this.username = username;
        this.content = content;
        this.timestamp = new Date();
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
