package com.example.carpro;

import com.model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HistoryCardController {

    @FXML
    private Button btnReturn;

    @FXML
    private ImageView imgCar;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblBrandModelCarPlate;

    @FXML
    private Label lblEndDate;

    @FXML
    private Label lblEndTime;

    @FXML
    private Label lblRoundArrow;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblStartTime;

    @FXML
    private Separator separatorBlack;

    @FXML
    private Separator separatorGrey;

    @FXML
    private StackPane spHistory;

    @FXML
    private VBox vboxHistory;

    public void setData(Car car){
        lblBrandModelCarPlate.setText(car.getModel());
        lblAddress.setText(car.getLocation());
        //lblStartDate.setText(car.get);
    }
}
