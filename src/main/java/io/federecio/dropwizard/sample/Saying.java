package io.federecio.dropwizard.sample;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Saying {

    private final String message;

    public Saying(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }
}
