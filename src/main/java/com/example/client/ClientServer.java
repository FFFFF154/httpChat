package com.example.client;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientServer {
    private Client client;
    public ClientServer(Client client){
        this.client = client;
    }
    public void createServer(){
        int port = client.getLogin().hashCode() % 1000;
        try {
            HttpServer clientServer = HttpServer.create(new InetSocketAddress(port), 0);
            clientServer.createContext("/", new ClientHandler(client));
            clientServer.setExecutor(null);
            clientServer.start();
        } catch (IOException e) {
        }

    }
}
