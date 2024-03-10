package com.example.client;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {

    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    private String login;

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin(){
        return login;
    }
    private String requestMessage;
    private String persons;

    public String createPostRequest(String message){
        // Отправка POST запроса на сервер
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/"))
                .version(HttpClient.Version.HTTP_1_1)
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(message))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (Exception e){
        }
        return null;
    }

    public int createRequestLog(String name, String password){
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/" + name + ":" + password))
                .version(HttpClient.Version.HTTP_1_1)
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .GET()
                .build();
        try{
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        } catch (Exception e){
        }
        return 404;
    }
    public int createRequestReg(String name, String password){ // GET запрос на регистрацию
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/registration/" + name + ":" + password))
                .version(HttpClient.Version.HTTP_1_1)
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .GET()
                .build();
        try{
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        } catch (Exception e){
        }
        return 404;
    }

//    public String waitResponse(){
//        HttpRequest request= HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:8080/broadcast"))
//                .version(HttpClient.Version.HTTP_1_1)
//                .GET()
//                .build();
//        CompletableFuture<HttpResponse<String>> resp = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//        try{
//            return resp.get().body();
//        } catch (Exception e){
//        }
//        return null;
//    }

    public void setMessage(String requestMessage){
        this.requestMessage = requestMessage;
    }

    public String getRequestMessage(){
        return requestMessage;
    }

    public void setPersons(String persons){
        StringBuilder stringBuilder = new StringBuilder(persons);
        while(stringBuilder.indexOf("=") != -1){
            stringBuilder.delete(stringBuilder.indexOf("="), stringBuilder.indexOf(","));
        }
        this.persons = stringBuilder.toString();

    }

    public String getPersons(){
        return persons;
    }


}
