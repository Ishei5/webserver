package com.pankov.roadtosenior.model;

import java.util.List;
import java.util.Map;

public class Request {

    private String uri;
    private Map<String, List<String>> headers;
    private HttpMethod httpMethod;

    public Request() {
    }

    public Request(String uri, Map<String, List<String>> headers, HttpMethod httpMethod) {
        this.uri = uri;
        this.headers = headers;
        this.httpMethod = httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }
}
