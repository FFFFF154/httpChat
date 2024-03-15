package com.example.httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class RegistrationHandler implements HttpHandler { // Регистрация
    @Override
    public void handle(HttpExchange t) throws IOException {
        if ("GET".equals(t.getRequestMethod())) { // Регистрация
            String uri = t.getRequestURI().toString();
            String login = uri.substring(uri.lastIndexOf('/') + 1, uri.indexOf(':'));
            String password = uri.substring(uri.indexOf(':') + 1);

            try (FileWriter file = new FileWriter("C:\\Users\\dns\\Documents\\java\\httpChat\\persons.txt", true)) {
                OutputStream os = t.getResponseBody();
                String response = "This is the response";
                if (!checkLog(login)) {
                    String person = login + ":" + password;
                    File history = new File("C:\\Users\\dns\\Documents\\java\\httpChat\\" + login + ".txt");
                    System.out.println(history.createNewFile());
                    file.write(person);
                    file.append('\n');
                    t.sendResponseHeaders(200, response.length());
                } else {
                    t.sendResponseHeaders(404, response.length());
                }
                os.write(response.getBytes());
                os.close();
            }


        }
    }

    private boolean checkLog(String log) { // проверка наличия логина в файле
        try (BufferedReader fileReader = new BufferedReader(new FileReader("C:\\Users\\dns\\Documents\\java\\httpChat\\persons.txt"))) { //
            String str;
            while ((str = fileReader.readLine()) != null) {
                String subStr = str.substring(0, str.indexOf(":"));
                if (log.equals(subStr)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }
}
