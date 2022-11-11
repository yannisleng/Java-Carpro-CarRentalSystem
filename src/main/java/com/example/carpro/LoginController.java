package com.example.carpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginController implements Initializable {

    @FXML
    private StackPane spLogin;

    @FXML
    private StackPane spLogin2;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("loginPage.fxml"));
    }

    //login
    @FXML
    void login(ActionEvent event) {
        int count = 0;
        try {
            Scanner userFile = new Scanner(new File("src/main/resources/com/example/carpro/database/user.txt"));
            while (userFile.hasNextLine()){
                String userDetail = userFile.nextLine();
                String[] userDetails = userDetail.split(",");

                if(txtUsername.getText().equals(userDetails[2]) && txtPassword.getText().equals(userDetails[3])){
                    count++;
                    if(userDetails[1].equals("admin")){
                        switchScene("main.fxml", spLogin);
                    }else if(userDetails[1].equals("customer")){
                        switchScene("cusMain.fxml", spLogin);
                    }
                }
            }
            userFile.close();

            if(count==0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login failed");
                alert.setHeaderText("Incorrect username or password.");
                alert.setContentText("Please try again.");
                alert.show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //switch scene
    private void switchScene(String fxml, StackPane stackPane){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxml));

        try {
            StackPane newStakePane = fxmlLoader.load();
            stackPane.getChildren().clear();
            stackPane.getChildren().add(newStakePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void forgotPassword(MouseEvent event) {
        switchScene("forgotPassword.fxml", spLogin2);
    }

    //click button to show hidden password
    @FXML
    private void showPassword(MouseEvent event) {
        if (!txtPassword.getText().isEmpty()){
            String password = txtPassword.getText();
            txtPassword.clear();
            txtPassword.setPromptText(password);
        }
    }

    //click button to hide password & show prompt text "Password"
    @FXML
    private void hidePassword(MouseEvent event) {
        if(!txtPassword.getPromptText().equals("Password")){
            String password = txtPassword.getPromptText();
            txtPassword.setText(password);
            txtPassword.setPromptText("Password");
        }
    }
}
