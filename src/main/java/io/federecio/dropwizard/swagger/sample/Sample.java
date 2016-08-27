package io.federecio.dropwizard.swagger.sample;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sample {

    private final String message = "hello";

    @JsonProperty
    public String getMessage() {
        return message;
    }
}
