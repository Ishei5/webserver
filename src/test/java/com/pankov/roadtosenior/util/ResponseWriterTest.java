package com.pankov.roadtosenior.util;

import com.pankov.roadtosenior.model.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseWriterTest {

    private ResponseWriter responseWriter = new ResponseWriter();

    @Test
    @DisplayName("Test create start line")
    public void testCreateStarLine() {
        String expectedStartLine = "HTTP/1.1 200 OK\r\n";

        assertEquals(expectedStartLine, responseWriter.createStartLine(HttpStatus.OK));
    }

    @Test
    @DisplayName("Test create header row")
    public void testCreateHeaderRow() {
        String expectedHeaderRow = "Last-Modified: Wed, 08 Sep 2021 19:15:56 GMT\r\n";
        String actualHeaderRow = responseWriter.createHeaderFiled(
                "Last-Modified", List.of("Wed", "08 Sep 2021 19:15:56 GMT"));
        assertEquals(expectedHeaderRow, actualHeaderRow);
    }

    @Test
    public void testWriteErrorResponse() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        responseWriter.writeErrorResponse(writer, HttpStatus.NOT_FOUND);

        String expectedStartLine = "HTTP/1.1 404 NOT_FOUND\r\n\r\n404 NOT_FOUND";

        assertEquals(expectedStartLine, out.toString());
    }

    @Test
    public void testTransferContent() throws IOException {
        byte[] array = new byte[50];
        for (int i = 0; i < 50; i++) {
            array[i] = (byte) (i + 65);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(array);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        responseWriter.transferContent(new BufferedReader(new InputStreamReader(inputStream)), writer);
        writer.flush();

        assertArrayEquals(array, out.toByteArray());
    }
}