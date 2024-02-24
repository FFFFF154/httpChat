module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires jdk.httpserver;


    opens com.example.client to javafx.fxml;
    exports com.example.client;
}