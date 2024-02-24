package httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private static final ConcurrentHashMap<String, HttpExchange> map = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            MyHandler myHandler = new MyHandler();
            myHandler.setHashMap(map);
            RegistrationHandler rHandler = new RegistrationHandler();
            server.createContext("/registration/", rHandler);
            server.createContext("/", myHandler);
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
            server.setExecutor(threadPoolExecutor);
            server.start();
        } catch (IOException e){

        }

    }
}
