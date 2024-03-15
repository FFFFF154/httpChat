package com.example.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.*;

public class ClientHandler implements HttpHandler {


    private Client client;
    private ChatController chatController;

    public ClientHandler(Client client, ChatController chatController) {
        this.client = client;
        this.chatController = chatController;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if ("POST".equals(t.getRequestMethod())) {
            BufferedReader bf = new BufferedReader(new InputStreamReader(t.getRequestBody()));
            String request = bf.readLine();
            String line = request.substring(0, request.indexOf("{")) + "\n";
            chatController.getArea().appendText(line);
            try(FileWriter fileWriter = new FileWriter("C:\\Users\\dns\\Documents\\java\\httpChat\\" + client.getLogin() + ".txt",true)){
                fileWriter.write(line);
            }
            chatController.getLogins().clear();
            String persons = request.substring(request.indexOf("{") + 1, request.lastIndexOf("}"));
            persons = persons.replaceAll(" ", "");
            persons = persons.replaceAll(",", "\n");
            chatController.getLogins().appendText(persons);
            OutputStream os = t.getResponseBody();
            String response = "200";
            t.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());
            os.close();
            //persons = persons.substring(persons.indexOf("{"));
            //client.setPersons(strMap);
        }
    }
}
