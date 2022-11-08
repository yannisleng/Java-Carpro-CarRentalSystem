package com.example.carpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class addeditCar_Info_Controller {

    @FXML
    private StackPane addeditcar_info;

    @FXML
    private void switchtoBefore (ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addeditCar_Main.fxml"));

        try{
            StackPane stackPane = fxmlLoader.load();
            addeditcar_info.getChildren().clear();
            addeditcar_info.getChildren().add(stackPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
