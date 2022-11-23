package com.example.carpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import com.model.Car;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addeditCar_item_Controller implements Initializable {

    @FXML
    private Label brand;

    @FXML
    private CheckBox id;

    @FXML
    private Label location;

    @FXML
    private Label model;

    @FXML
    private Label plate;

    @FXML
    private Label price;

    public void setData(Car car){
        id.setText(car.getId());
        brand.setText(car.getBrand());
        model.setText(car.getModel());
        plate.setText(car.getNumPlate());
        price.setText("RM "+ String.format("%.02f",car.getPrice()));
        location.setText(car.getState());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
