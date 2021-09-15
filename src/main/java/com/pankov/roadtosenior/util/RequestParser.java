package com.pankov.roadtosenior.util;

import com.pankov.roadtosenior.exception.InternalSeverErrorException;
import com.pankov.roadtosenior.model.HttpMethod;
import com.pankov.roadtosenior.model.Request;
import com.pankov.roadtosenior.exception.BadRequestException;
import com.pankov.roadtosenior.exception.NotAllowedMethodException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.*;

public class RequestParser {

    public static Request parseRequest(BufferedReader reader) throws IOException {
        Request request = new Request();
        try {
            injectUriAndMethod(reader, request);
            injectHeaders(reader, request);
        } catch (IllegalArgumentException e) {
            throw new NotAllowedMethodException(e);
        } catch (IOException e) {
            throw new InternalSeverErrorException(e);
        } catch (Exception e) {
            throw new BadRequestException(e);
        }

        return request;
    }

    static void injectUriAndMethod(BufferedReader reader, Request request) throws Exception {
        String[] startLine = reader.readLine().split(" ");
        request.setHttpMethod(HttpMethod.getHttpMethodByName(startLine[0]));
        String uri = "/".equals(startLine[1]) ? "index.html" : startLine[1];
        request.setUri(uri);
    }

    static void injectHeaders(BufferedReader reader, Request request) throws IOException {
        String headerLine;

        Map<String, List<String>> headers = new HashMap<>();
        while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
            String[] headerLineArray = headerLine.split(": ");
            checkHeaderField(headerLineArray);
            headers.put(headerLineArray[0], List.of(headerLineArray[1].split(", ")));
        }

        request.setHeaders(headers);
    }

    private static void checkHeaderField(String[] headerLineArray) {
        if (headerLineArray.length != 2 || Arrays.stream(headerLineArray).anyMatch(String::isEmpty)) {
            throw new BadRequestException();
        }
    }
}
