package com.pankov.roadtosenior.exception;

import com.pankov.roadtosenior.model.HttpStatus;

public class NotAllowedMethodException extends CustomServerException {

    private final HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

    public NotAllowedMethodException() {
    }

    public NotAllowedMethodException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
