package com.pankov.roadtosenior.exception;

import com.pankov.roadtosenior.model.HttpStatus;

public class BadRequestException extends CustomServerException {

    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    public BadRequestException() {
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
