package com.example.httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class BroadcastHandler implements HttpHandler {

    ConcurrentHashMap<String, HttpExchange> map;

    @Override
    public void handle(HttpExchange t) throws IOException {

    }
}
