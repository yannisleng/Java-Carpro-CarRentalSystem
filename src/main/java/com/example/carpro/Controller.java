package com.example.carpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button booking;

    @FXML
    private Button edit;

    @FXML
    private Button logout;

    @FXML
    private Button report;

    @FXML
    private Button addUser;

    @FXML
    private StackPane switchScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("adminReport.fxml"));

        try{
            StackPane stackPane = fxmlLoader.load();
            switchScene.getChildren().add(stackPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void switchscene(String fxml){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxml));

        try{
            StackPane stackPane = fxmlLoader.load();
            switchScene.getChildren().clear();
            switchScene.getChildren().add(stackPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMenuAction(ActionEvent event) throws Exception{
        if (event.getSource()==report){
            switchscene("adminReport.fxml");
        }else if(event.getSource()==edit){
            switchscene("addeditCar_Main.fxml");
        } else if (event.getSource()==booking) {
            switchscene("customerBooking.fxml");
        } else if (event.getSource()==addUser) {
            switchscene("registerUser.fxml");
        }
    }
}
