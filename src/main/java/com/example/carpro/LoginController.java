package com.example.carpro;

import com.model.User;
import com.model.dataFactory;
import com.model.database;
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
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class LoginController implements Initializable {

    public static User loginUser;

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
    private void login(ActionEvent event) {
        int count = 0;
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("user");
        List<User> users = new ArrayList<>(db.getAllData());

        for(User user: users){
            if(txtUsername.getText().equals(user.getUsername()) && txtPassword.getText().equals(user.getPassword())){
                count++;
                loginUser = new User(user.getUsername(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(),
                        user.getGender(), user.getEmail(), user.getPhoneNum(), user.getAddress(), user.getPostCode(),
                        user.getState(), user.getRole(), user.getPassword(), user.getProfilePic());
                if(loginUser.getRole().equals("Admin")){
                    Scene.switchScene("adminMain.fxml", spLogin);
                }else if(loginUser.getRole().equals("Customer")){
                    Scene.switchScene("cusMain.fxml", spLogin);
                }
            }
        }

        if(count==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setHeaderText("Incorrect username or password.");
            alert.setContentText("Please try again.");
            alert.show();
        }
    }

    @FXML
    private void forgotPassword(MouseEvent event) {
        Scene.switchScene("forgotPassword.fxml", spLogin2);
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
