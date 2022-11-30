package com.example.carpro;

import com.model.Booking;
import com.model.Car;
import com.model.dataFactory;
import com.model.database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

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

    public Button getBtnReturn() {
        return btnReturn;
    }

    public void setData(Booking booking){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> cars = new ArrayList<>(db.getAllData());

        Car bookedCar = new Car();

        for(Car car: cars){
            if(booking.getCarId().equals(car.getId())){
                bookedCar = car;
            }
        }

        lblBrandModelCarPlate.setText(bookedCar.getBrand() + " " + bookedCar.getModel() + " " + bookedCar.getNumPlate());
        lblAddress.setText(bookedCar.getAddress() + ", " + bookedCar.getPostCode() + ", " + bookedCar.getState());
        lblStartDate.setText(String.valueOf(booking.getStartDate()));
        lblStartTime.setText(booking.getStartTime());
        lblEndDate.setText(String.valueOf(booking.getEndDate()));
        lblEndTime.setText(booking.getEndTime());
    }
}
