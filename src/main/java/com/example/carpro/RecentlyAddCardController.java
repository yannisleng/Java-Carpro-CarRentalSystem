package com.example.carpro;

import com.model.Car;
import com.model.User;
import com.model.dataFactory;
import com.model.database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RecentlyAddCardController implements Initializable {

    @FXML
    private Button btnView;

    public Button getBtnView() {
        return btnView;
    }

    @FXML
    private ImageView imgRecentlyAddCar;

    @FXML
    private Label lblBrandModel;

    @FXML
    private Label lblLocation;

    @FXML
    private Tooltip ttCarDesc;


    public void setData(Car car){
        Image image = new Image("file:src/main/resources/com/example/carpro/img/car/" + car.getImgsrc());
        imgRecentlyAddCar.setImage(image);
        lblBrandModel.setText(car.getBrand() + " " + car.getModel());
        ttCarDesc.setText(car.getBrand() + " " + car.getModel());
        lblLocation.setText(car.getState());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
