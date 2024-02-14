module com.example.httpchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.httpchat to javafx.fxml;
    exports com.example.httpchat;
}