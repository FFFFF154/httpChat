package com.example.client;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientServer {
    private Client client;
    private ChatController chatController;
    public ClientServer(Client client, ChatController chatController){
        this.client = client;
        this.chatController = chatController;
    }
    public void createServer(){
        int port = client.getLogin().hashCode() % 1000;
        try {
            HttpServer clientServer = HttpServer.create(new InetSocketAddress(port), 0);
            clientServer.createContext("/", new ClientHandler(client, chatController));
            clientServer.setExecutor(null);
            clientServer.start();
        } catch (IOException e) {
        }

    }
}
