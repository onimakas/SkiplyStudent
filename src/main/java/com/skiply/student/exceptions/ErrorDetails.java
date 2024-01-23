package com.skiply.student.exceptions;
import lombok.Getter;
import java.time.Instant;

@Getter
public class ErrorDetails {

    private final Instant timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(String message, String details) {
        super();
        this.timestamp = Instant.now();
        this.message = message;
        this.details = details;
    }

}