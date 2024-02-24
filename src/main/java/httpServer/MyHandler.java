package httpServer;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class MyHandler implements HttpHandler {
    ConcurrentHashMap<String, HttpExchange> map;

    @Override
    public void handle(HttpExchange t) throws IOException {

        if ("GET".equals(t.getRequestMethod())) { // Вход
            String uri = t.getRequestURI().toString();
            String login = uri.substring(uri.lastIndexOf('/') + 1, uri.indexOf(':'));
            String password = uri.substring(uri.indexOf(':') + 1);
            OutputStream os = t.getResponseBody();
            String response = "This is the response";
            if (checkLogin(login, password)) {
                t.sendResponseHeaders(200, response.length());
                map.put(login, t);
            } else {
                t.sendResponseHeaders(404, response.length());
            }

            os.write(response.getBytes());
            os.close();
        } else if ("POST".equals(t.getRequestMethod())) { // Отправка сообщений
            System.out.println(map);
            sendMessage(t);
//            BufferedReader os2 = new BufferedReader(new InputStreamReader(t.getRequestBody()));
//            String request = os2.readLine();
//            os2.close();
//
//            OutputStream os3 = t.getResponseBody();
//            t.sendResponseHeaders(200, request.length());
//            os3.write(request.getBytes());
//            os3.close();


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

    public void setHashMap(ConcurrentHashMap<String, HttpExchange> map) {
        this.map = map;
    }

    private void sendAll(String req){
        try{
            for (HttpExchange ex
            :map.values()){
                System.out.println(ex);
                OutputStream os3 = ex.getResponseBody();
                System.out.println(1);
                ex.sendResponseHeaders(200, req.length());
                System.out.println(2);
                os3.write(req.getBytes());
                System.out.println(3);
                os3.close();
            }
        }catch (Exception e){
        }
    }
    private void sendMessage(HttpExchange e){
        try{
            BufferedReader os2 = new BufferedReader(new InputStreamReader(e.getRequestBody()));
            String request = os2.readLine();
            //if(request != null){
                sendAll(request);
                OutputStream os3 = e.getResponseBody();
                e.sendResponseHeaders(200, request.length());
                os3.write(request.getBytes());
                os3.close();
            //}

        } catch (IOException ex){

        }

    }
}
