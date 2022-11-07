package com.example.carpro;

import com.model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class RecentlyAddCardController {

    @FXML
    private HBox boxRecentlyAddCar;

    @FXML
    private Button btnView;

    @FXML
    private ImageView imgRecentlyAddCar;

    @FXML
    private Label lblBrandModel;

    @FXML
    private Label lblLocation;

    @FXML
    private StackPane spRecentlyAddCard;

    public void setData(Car car){
        /*Image image = new Image(getClass().getResourceAsStream(car.getImgSrc()));
        imgRecentlyAddCar.setImage(image);*/

        lblBrandModel.setText(car.getModel());
        lblLocation.setText(car.getLocation());
    }
}
