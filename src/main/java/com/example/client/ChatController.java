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
    String prevStr = "";
    String resStr;
    @FXML
    public void sendMessage(){
        String message = text.getText();
        text.setText("");

        // Отправка сообщения
        String response = client.createPostRequest("{ " + client.getLogin() + " } : " + message);
        resStr = response;
        String showMessage = response + "\n";
        area.appendText(showMessage);

    }

    public void updateArea(){
        String[] waitStr = new String[1];
        new Thread(() -> {
            try{
                while(true){
                    Thread.sleep(1000);
                    if (((waitStr[0] = client.waitResponse()) != null) && (!prevStr.equals(waitStr[0])) && (!waitStr[0].equals(resStr))){ // TODO появление текста без кнопки
                        area.appendText(waitStr[0] + "\n");
                        prevStr = waitStr[0];
                    }
                }

            }catch (InterruptedException e){

            }

        }).start();


    }

    public void setClient(Client client){ // Передача клиента
        this.client = client;
    }



}
