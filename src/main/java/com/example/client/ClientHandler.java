package com.example.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ClientHandler implements HttpHandler {

    private Client client;

    public ClientHandler(Client client) {
        this.client = client;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if ("POST".equals(t.getRequestMethod())) {
            BufferedReader bf = new BufferedReader(new InputStreamReader(t.getRequestBody()));
            String request = bf.readLine();
            String strMap = request.substring(request.indexOf("=*") + 2);
            OutputStream os = t.getResponseBody();
            String response = "200";
            t.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());
            os.close();
            client.setMessage(request);
            client.setPersons(strMap);
        }
    }
}
