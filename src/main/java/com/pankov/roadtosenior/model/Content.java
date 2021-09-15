package com.pankov.roadtosenior.model;

import java.io.*;

public class Content {

    private final static String DEFAULT_WEB_APP_PATH = "src/main/resources";
    private String pathToWebApp;

    public Content() {
        this.pathToWebApp = DEFAULT_WEB_APP_PATH;
    }

    public Content(String pathToWebApp) {
        this.pathToWebApp = pathToWebApp;
    }

    public String getPathToWebApp() {
        return pathToWebApp;
    }

    public void setPathToWebApp(String pathToWebApp) {
        this.pathToWebApp = pathToWebApp;
    }

    public Reader getContentReader(String uri) throws FileNotFoundException {
        File file = new File(pathToWebApp, uri);

        return new BufferedReader(new FileReader(file));
    }
}
