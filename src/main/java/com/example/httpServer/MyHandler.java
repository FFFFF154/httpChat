package com.example.httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyHandler implements HttpHandler {
    ConcurrentHashMap<String, Integer> map;

    private String broadcast;

    @Override
    public void handle(HttpExchange t) throws IOException {

        if ("GET".equals(t.getRequestMethod())) { // Вход
            String uri = t.getRequestURI().toString();
            if (uri.substring(uri.lastIndexOf("/") + 1).equals("broadcast")) { // Рассылка сообщений всем
                System.out.println(map);
                System.out.println(t);
                OutputStream outputStream = t.getResponseBody();
                System.out.println("ee");
                t.sendResponseHeaders(200, broadcast.length());
                outputStream.write(broadcast.getBytes());
                outputStream.close();
            } else {
                String login = uri.substring(uri.lastIndexOf('/') + 1, uri.indexOf(':'));
                String password = uri.substring(uri.indexOf(':') + 1);
                File file = new File("C:\\Users\\dns\\Documents\\java\\httpChat\\" + login + ".txt");
                if (!file.exists()){
                    file.createNewFile();
                }
                OutputStream os = t.getResponseBody();
                String response = "This is the response";
                if (checkLogin(login, password)) {
                    t.sendResponseHeaders(200, response.length());
                    map.put(login, login.hashCode());
                } else {
                    t.sendResponseHeaders(404, response.length());
                }
                os.write(response.getBytes());
                os.close();
            }

        } else if ("POST".equals(t.getRequestMethod())) { // Отправка сообщений
            sendMessage(t);
        }
    }

    private boolean checkLogin(String log, String password) { // проверка наличия логина и сравнивание пароля
        try (BufferedReader fileReader = new BufferedReader(new FileReader("C:\\Users\\dns\\Documents\\java\\httpChat\\persons.txt"))) { //
            String str;
            while ((str = fileReader.readLine()) != null) {
                String subStr = str.substring(0, str.indexOf(":"));
                if (log.equals(subStr)) {
                    String strPassword = str.substring(str.indexOf(":") + 1);
                    if (password.equals(strPassword)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void setHashMap(ConcurrentHashMap<String, Integer> map) {
        this.map = map;
    }

    private void sendAll(String req) { //TODO Нужно отправлять список логинов
        HttpClient serverClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        System.out.println(map);
        for (Map.Entry<String, Integer> entry
                : map.entrySet()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + entry.getValue() % 1000 + "/"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .headers("Content-Type", "text/plain;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(req + map))
                    .build();
            try {
                HttpResponse<String> response = serverClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
//                System.err.println("POPPOPPOPO");
//                System.err.println(map);
                // Удаление из map челика
                map.remove(entry.getKey());
            }
        }
    }

    private void sendMessage(HttpExchange e) {
        try {
            BufferedReader os2 = new BufferedReader(new InputStreamReader(e.getRequestBody()));
            String request = os2.readLine();
            sendAll(request);
            OutputStream os3 = e.getResponseBody();
            e.sendResponseHeaders(200, request.length());
            os3.write(request.getBytes());
            os3.close();

        } catch (IOException ex) {

        }

    }
}
