package com.example.carpro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    Image icon = new Image("file:src/main/resources/com/example/carpro/img/logo.png");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setScene(scene);
        /*Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setScene(new Scene(root,900,600));*/
        stage.getIcons().add(icon);
        stage.setTitle("Car Pro");

        //Disable min, max, close btns
        stage.initStyle(StageStyle.UNDECORATED);

        //Press esc to exit the program
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent keyEvent) -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE) {
                exit(stage);
            }
        });

        stage.show();
    }

    //Exit Confirmation
    public void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Car Pro");
        alert.setHeaderText("You're about to logout.");
        alert.setContentText("Are you sure you want to exit Car Pro?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}