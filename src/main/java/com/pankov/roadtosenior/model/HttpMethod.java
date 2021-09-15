package com.pankov.roadtosenior.model;

public enum HttpMethod {

    GET("GET");
//    POST("POST");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod getHttpMethodByName(String name) {
        for (HttpMethod value : values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }

        throw new IllegalArgumentException("There is no HTTP method " + name);
    }
}
