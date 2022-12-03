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

    @FXML
    private Button bookingMenu;

    @FXML
    private Button btnExplore;

    public Button getBtnHistory() {
        return btnHistory;
    }

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnProfile;

    @FXML
    private Button exploreMenu;

    @FXML
    private Button profileMenu;

    public StackPane getSpCusDefault() {
        return spCusDefault;
    }

    @FXML
    private StackPane spCusDefault;

    @FXML
    private StackPane spCusMain;

    @FXML
    public static StackPane spCus;

    @FXML
    private VBox vboxCusMenu;
    public static CusController instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        spCus = spCusMain;

        Scene.switchScene("explore.fxml", spCusDefault);
    }

    public static Receipt receipt(){
        Receipt receipt = (Receipt) Scene.getController("receipt.fxml", spCus);
        return receipt;
    }

    @FXML
    private void handleMenuAction(ActionEvent event) throws Exception{
        if(event.getSource()==btnExplore || event.getSource()==exploreMenu){
            btnExplore.requestFocus();
            Scene.switchScene("explore.fxml", spCusDefault);
        }else if(event.getSource()==btnHistory || event.getSource()==bookingMenu){
            btnHistory.requestFocus();
            Scene.switchScene("cusHistory.fxml", spCusDefault);
        }else if(event.getSource()==btnProfile || event.getSource()==profileMenu){
            btnProfile.requestFocus();
            Scene.switchScene("cusProfile.fxml", spCusDefault);
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
