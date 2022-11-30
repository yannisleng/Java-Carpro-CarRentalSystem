package com.example.carpro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Scene {
    public static void switchScene(String fxml, StackPane stackPane){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation( Scene.class.getResource(fxml));

        try {
            StackPane newStakePane = fxmlLoader.load();
            stackPane.getChildren().clear();
            stackPane.getChildren().add(newStakePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
