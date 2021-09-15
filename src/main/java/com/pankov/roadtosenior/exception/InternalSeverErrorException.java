package com.pankov.roadtosenior.exception;

import com.pankov.roadtosenior.model.HttpStatus;

public class InternalSeverErrorException extends RuntimeException {

    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalSeverErrorException() {
    }

    public InternalSeverErrorException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
