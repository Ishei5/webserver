package com.pankov.roadtosenior.handler;

import com.pankov.roadtosenior.exception.BadRequestException;
import com.pankov.roadtosenior.exception.InternalSeverErrorException;
import com.pankov.roadtosenior.model.Content;
import com.pankov.roadtosenior.model.HttpStatus;
import com.pankov.roadtosenior.model.Request;
import com.pankov.roadtosenior.model.Response;
import com.pankov.roadtosenior.util.RequestParser;
import com.pankov.roadtosenior.util.ResponseWriter;

import java.io.*;

public class RequestHandler {

    private Content content;
    private BufferedReader reader;
    private BufferedWriter writer;

    public RequestHandler(Content content, BufferedReader reader, BufferedWriter writer) {
        this.content = content;
        this.reader = reader;
        this.writer = writer;
    }

    public void handle() throws IOException {

//        Request request;
//        Reader contentReader;
        try {
            Request request = RequestParser.parseRequest(reader);
            try (Reader contentReader = content.getContentReader(request.getUri())) {
                ResponseWriter.writeResponse(writer, new Response(HttpStatus.OK,
                        contentReader));
            }
        } catch (FileNotFoundException e) {
            ResponseWriter.writeErrorResponse(writer, HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            ResponseWriter.writeErrorResponse(writer, e.getStatus());
        } catch (InternalSeverErrorException e) {
            ResponseWriter.writeErrorResponse(writer, e.getStatus());
        }
    }
}