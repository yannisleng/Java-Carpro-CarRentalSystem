package com.example.carpro;

import com.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CusController implements Initializable {

    Stage stage;

    User user;

    @FXML
    private Button bookingMenu;

    @FXML
    private Button btnExplore;

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnProfile;

    @FXML
    private Button exploreMenu;

    @FXML
    private Button profileMenu;

    @FXML
    private StackPane spCusDefault;

    @FXML
    private VBox vboxCusMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("explore.fxml"));

        try{
            StackPane stackPane = fxmlLoader.load();
            spCusDefault.getChildren().add(stackPane);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void switchScene(String fxml){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxml));

        try{
            StackPane stackPane = fxmlLoader.load();
            spCusDefault.getChildren().clear();
            spCusDefault.getChildren().add(stackPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMenuAction(ActionEvent event) throws Exception{
        if(event.getSource()==btnExplore || event.getSource()==exploreMenu){
            btnExplore.requestFocus();
            switchScene("explore.fxml");
        }else if(event.getSource()==btnHistory || event.getSource()==bookingMenu){
            btnHistory.requestFocus();
            switchScene("cusHistory.fxml");
        }else if(event.getSource()==btnProfile || event.getSource()==profileMenu){
            btnProfile.requestFocus();
            switchScene("cusProfile.fxml");
        }
    }

    @FXML
    private void setMenuVisible(ActionEvent event) throws Exception{
        if(vboxCusMenu.isVisible()){
            vboxCusMenu.setVisible(false);
        }else{
            vboxCusMenu.setVisible(true);
        }
    }

    public void exit(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Car Pro");
        alert.setHeaderText("You're about to logout.");
        alert.setContentText("Are you sure you want to exit Car Pro?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) spCusDefault.getScene().getWindow();
            stage.close();
        }
    }
}
