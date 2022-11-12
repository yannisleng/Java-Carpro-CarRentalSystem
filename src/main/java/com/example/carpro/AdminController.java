package com.example.carpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

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
    private Button addUserMenu;

    @FXML
    private Button bookingMenu;

    @FXML
    private Button editMenu;


    @FXML
    private Button reportMenu;

    @FXML
    private StackPane switchScene;

    @FXML
    private VBox menu;

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

    public void switchscene(String fxml){
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
        if (event.getSource()==report || event.getSource()==reportMenu){
            report.requestFocus();reportMenu.requestFocus();
            switchscene("adminReport.fxml");
        }else if(event.getSource()==edit || event.getSource()==editMenu){
            edit.requestFocus();editMenu.requestFocus();
            switchscene("addeditCar_Main.fxml");
        } else if (event.getSource()==booking || event.getSource()==bookingMenu) {
            booking.requestFocus();bookingMenu.requestFocus();
            switchscene("customerBooking.fxml");
        } else if (event.getSource()==addUser || event.getSource()==addUserMenu) {
            addUser.requestFocus();addUserMenu.requestFocus();
            switchscene("registerUser.fxml");
        }
    }

    @FXML
    private void setMenuVisible(ActionEvent event) throws Exception{
        if(menu.isVisible()){
            menu.setVisible(false);
        }else{
            menu.setVisible(true);
        }
    }
}
