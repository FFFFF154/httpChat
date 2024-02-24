package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientController {

    static Client client = new Client();
    @FXML
    private TextField login;
    @FXML
    private PasswordField myPassword;
    @FXML
    private Label message;
    @FXML
    private Button button1;

    @FXML
    public void buttonOfAuthorization() {

        String log = login.getText();
        client.setLogin(log);
        String pas = myPassword.getText();
        if(client.createRequestLog(log, pas) == 200){
            Stage stage = (Stage) button1.getScene().getWindow();
            stage.close();
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("chat-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage1 = new Stage();
                stage1.setScene(scene);
                stage1.setTitle("Tornados-Chat");
                ChatController chatController = fxmlLoader.getController();
                chatController.setClient(client);
                stage1.show();
            } catch (IOException e){

            }

        } else {
            myPassword.setText("");
            message.setText("Wrong password");
        }

    }

    public void buttonOfRegistration(){
        String log = login.getText();
        client.setLogin(log);
        String pas = myPassword.getText();
        if(client.createRequestReg(log, pas) == 200) {
            message.setText("success!");
        } else {
            myPassword.setText("");
            message.setText("The login is already taken");
        }
        //Stage stage = (Stage) button1.getScene().getWindow();
        //stage.close();
    }
}