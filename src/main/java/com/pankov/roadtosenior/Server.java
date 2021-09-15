package com.pankov.roadtosenior;

import com.pankov.roadtosenior.exception.CustomServerException;
import com.pankov.roadtosenior.handler.RequestHandler;
import com.pankov.roadtosenior.model.Content;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final static int DEFAULT_PORT = 3000;
    private int port;
    private Content contentReader = new Content();

    public Server() {
        this.port = DEFAULT_PORT;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setWebPathApp(String webPathApp) {
        contentReader.setPathToWebApp(webPathApp);
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    RequestHandler requestHandler = new RequestHandler(contentReader, reader, writer);
                    requestHandler.handle();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (IOException exception) {
            throw new CustomServerException(exception);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
