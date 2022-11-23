package com.example.carpro;

import com.model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RecommendedCarController {
    @FXML
    private Button btnView;

    public Button getBtnView() {
        return btnView;
    }

    @FXML
    private ImageView imgCar;

    @FXML
    private Label lblBrandModel;

    @FXML
    private Label lblLocation;

    public void setData(Car car){
        Image image = new Image("file:src/main/resources/com/example/carpro/img/car/" + car.getImgsrc());
        imgCar.setImage(image);
        lblBrandModel.setText(car.getBrand() + " " + car.getModel());
        lblLocation.setText(car.getState());
    }
}
