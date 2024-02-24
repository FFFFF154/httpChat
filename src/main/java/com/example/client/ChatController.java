package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatController {
    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    private Client client;
    @FXML
    private TextField text;
    @FXML
    private TextArea area;
    @FXML
    private TextArea logins;
    @FXML
    private Button button;
    @FXML
    public void sendMessage(){
        String message = text.getText();
        text.setText("");
        String waitStr;
        if ((waitStr = waitResponse()) != null){
            area.appendText(waitStr);
        }
        // Отправка сообщения
        String response = client.createPostRequest("{ " + client.getLogin() + " } : " + message);
        String showMessage = response + "\n";
        area.appendText(showMessage);

    }

    public void setClient(Client client){ // Передача клиента
        this.client = client;
    }

    private String waitResponse(){
        String response ;
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/"))
                .version(HttpClient.Version.HTTP_1_1)
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString("-0-"))
                .build();
        CompletableFuture<HttpResponse<String>> resp = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try{
            response = resp.get().body();
        } catch (Exception e){
            response = null;
        }
        return response;
    }

}
