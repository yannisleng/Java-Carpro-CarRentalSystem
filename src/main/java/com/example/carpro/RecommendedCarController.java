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

    @FXML
    private ImageView imgCar;

    @FXML
    private Label lblBrandModel;

    @FXML
    private Label lblLocation;

    @FXML
    private StackPane spRecommend;

    @FXML
    private VBox vboxRecommend;

    public void setData(Car car){
        //Image image = new Image(getClass().getResourceAsStream(car.getImgSrc()));
        //imgCar.setImage(image);
        lblBrandModel.setText(car.getModel());
        lblLocation.setText(car.getLocation());
    }
}
