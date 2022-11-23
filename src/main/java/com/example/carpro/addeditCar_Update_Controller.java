package com.example.carpro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class addeditCar_Update_Controller extends addeditcar_Main_Controller implements Initializable {
    @FXML
    private Button addBrandBtn;

    @FXML
    private Button addModelBtn;

    @FXML
    private StackPane addeditcar_info;

    @FXML
    private Label addressAlert;

    @FXML
    private Label addressHint;

    @FXML
    private TextField addressText;

    @FXML
    private Label brandAlert;

    @FXML
    private ComboBox<?> brandCmb;

    @FXML
    private ImageView carPicimgView;

    @FXML
    private Label modelAlert;

    @FXML
    private ComboBox<?> modelCmb;

    @FXML
    private Label pathLbl;

    @FXML
    private Label plateNumAlert;

    @FXML
    private Label plateNumHint;

    @FXML
    private TextField plateNumText;

    @FXML
    private Label postCodeAlert;

    @FXML
    private Label postCodeHint;

    @FXML
    private TextField postCodeText;

    @FXML
    private Label priceAlert;

    @FXML
    private Label priceHint;

    @FXML
    private TextField priceText;

    @FXML
    private Label seatsAlert;

    @FXML
    private ComboBox<?> seatsCmb;

    @FXML
    private Label stateAlert;

    @FXML
    private ComboBox<?> stateCmb;

    @FXML
    private Label successMsg;

    @FXML
    private Button uploadPhoBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
