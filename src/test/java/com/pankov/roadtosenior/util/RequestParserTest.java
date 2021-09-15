package com.pankov.roadtosenior.util;

import com.pankov.roadtosenior.model.Request;
import com.pankov.roadtosenior.exception.BadRequestException;
import com.pankov.roadtosenior.exception.NotAllowedMethodException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RequestParserTest {

    private final static String GET_REQUEST = "GET /wiki/HTTP HTTP/1.1\n" +
            "Host: ru.wikipedia.org\r\n" +
            "Accept: text/html\r\n" +
            "Connection: close\r\n" +
            "\r\n";

    private final static String EXPECTED_URI = "/wiki/HTTP";
    private final static String EXPECTED_METHOD = "GET";
    public final static Map<String, List<String>> EXPECTED_HEADERS = new HashMap<>() {{
        put("Host", List.of("ru.wikipedia.org"));
        put("Accept", List.of("text/html"));
        put("Connection", List.of("close"));
    }};

    private Request request = new Request();


    @Test
    @DisplayName("Test read and inject URI and method")
    public void testInjectUriAndMethod() throws Exception {

        RequestParser.injectUriAndMethod(new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(GET_REQUEST.getBytes()))), request);
        assertEquals(EXPECTED_METHOD, request.getHttpMethod().name());
        assertEquals(EXPECTED_URI, request.getUri());
    }

    @Test
    @DisplayName("Test read and inject headers")
    public void testInjectHeader() throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(GET_REQUEST.getBytes())));
        reader.readLine();

        RequestParser.injectHeaders(reader, request);

        Map<String, List<String>> actualHeaders = request.getHeaders();
        assertTrue(EXPECTED_HEADERS.equals(actualHeaders));
    }

    @Test
    @DisplayName("Test parse whole request")
    public void testRequestParser() throws IOException {

        Request request = RequestParser.parseRequest(new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(GET_REQUEST.getBytes()))));

        assertEquals(EXPECTED_METHOD, request.getHttpMethod().name());
        assertEquals(EXPECTED_URI, request.getUri());
        assertEquals(EXPECTED_HEADERS, request.getHeaders());
    }

    @Test
    @DisplayName("Test parse request method should throw NotAllowedMethodException")
    public void testInjectMethodWithNotAllowedMethodException() {
        assertThrows(NotAllowedMethodException.class, () -> {
            Request request = RequestParser.parseRequest(new BufferedReader(
                    new InputStreamReader(
                            new ByteArrayInputStream("POST /wiki/HTTP HTTP/1.1\r\n".getBytes()))));
        });
    }

    @Test
    @DisplayName("Test parse request method should throw BadRequestException")
    public void testInjectMethodWithBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            Request request = RequestParser.parseRequest(new BufferedReader(
                    new InputStreamReader(
                            new ByteArrayInputStream("GET /wiki/HTTP HTTP/1.1r\nSomeHeader: \r\n".getBytes()))));
        });
    }
}
