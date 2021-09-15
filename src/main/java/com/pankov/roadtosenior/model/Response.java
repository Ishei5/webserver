package com.pankov.roadtosenior.model;

import com.pankov.roadtosenior.model.HttpStatus;

import java.io.Reader;

public class Response {

    private HttpStatus status;
    private Reader reader;

    public Response(HttpStatus status, Reader reader) {
        this.status = status;
        this.reader = reader;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
