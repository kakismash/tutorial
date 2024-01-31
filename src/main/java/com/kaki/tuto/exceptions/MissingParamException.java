package com.kaki.tuto.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MissingParamException extends RuntimeException {

    private String source = "";
    private String message = "";

    private final int status = 403;

    public MissingParamException(String message) {
        super(message);
        this.message = message;
    }

    public MissingParamException(String message, String source) {
        super(message);
        this.message = message;
        this.source = source;
    }
}
