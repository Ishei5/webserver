package com.pankov.roadtosenior.util;

import com.pankov.roadtosenior.model.HttpStatus;
import com.pankov.roadtosenior.model.Response;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

public class ResponseWriter {

    private final static String CRLF = "\r\n";

    public static void writeResponse(Writer writer, Response response) throws IOException {
        writer.write(createStartLine(response.getStatus()));
        writer.write(CRLF);
        transferContent(response.getReader(), writer);
        writer.flush();
    }

    public static void writeErrorResponse(Writer writer, HttpStatus status) throws IOException {
        writer.write(createStartLine(status));
        writer.write(CRLF);
        writer.write(status.toString());
        writer.flush();
    }

    static String createStartLine(HttpStatus status) {
        return "HTTP/1.1 " + status.toString() + CRLF;
    }

    static String createHeaderFiled(String key, List<String> values) {
        return key + ": " + String.join(", ", values) + CRLF;
    }

    static void transferContent(Reader reader, Writer writer) throws IOException {
        char[] chars = new char[2048];
        int length;
        while ((length = reader.read(chars)) != -1) {
            writer.write(new String(chars, 0, length));
        }
    }
}
