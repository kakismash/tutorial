package com.kaki.tuto.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotFoundException extends RuntimeException {

    private String source = "";
    private String message = "";

    private final int status = 404;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public NotFoundException(String message, String source) {
        super(message);
        this.message = message;
        this.source = source;
    }
}
