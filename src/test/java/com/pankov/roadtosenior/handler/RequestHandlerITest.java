package com.pankov.roadtosenior.handler;

import com.pankov.roadtosenior.handler.RequestHandler;
import com.pankov.roadtosenior.model.Content;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerITest {

    BufferedReader reader;
    BufferedWriter writer;
    ByteArrayOutputStream byteArrayOutputStream;
    Content content;
    File file;

    @BeforeEach
    public void before() {

        byteArrayOutputStream = new ByteArrayOutputStream();

        writer = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream));

        content = new Content();
        content.setPathToWebApp("src/test");
    }

    @Test
    public void testHandlerWithPositiveResponse() throws Exception {
        createTestFile();

        String request = "GET test.txt HTTP/1.1\r\n\r\n";
        reader = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(request.getBytes())));

        content.setPathToWebApp("src/test");

        RequestHandler requestHandler = new RequestHandler(content, reader, writer);
        requestHandler.handle();

        file.delete();

        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "\r\nTest message";
        assertEquals(expectedResponse, byteArrayOutputStream.toString());
    }

    @Test
    public void testHandlerWithNotFoundResponse() throws IOException {

        String request = "GET not_exist.txt HTTP/1.1\r\n\r\n";
        reader = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(request.getBytes())));

        RequestHandler requestHandler = new RequestHandler(content, reader, writer);
        requestHandler.handle();

        String expectedResponse = "HTTP/1.1 404 NOT_FOUND\r\n" +
                "\r\n404 NOT_FOUND";
        assertEquals(expectedResponse, byteArrayOutputStream.toString());
    }

    private void createTestFile() throws Exception {
        file = new File("src/test/test.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write("Test message".getBytes());
        fileOutputStream.close();
    }
}