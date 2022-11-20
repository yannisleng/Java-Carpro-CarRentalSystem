package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CusBookingController implements Initializable {
    private User customer = ExploreController.getCustomer();

    private Car car = ExploreController.getSelectedCar();

    @FXML
    private Button btnBook;

    @FXML
    private Button btnCancel;

    @FXML
    private DatePicker dpDropOff;

    @FXML
    private DatePicker dpPickUp;

    @FXML
    private ImageView imgCar;

    @FXML
    private ImageView imgFuel;

    @FXML
    private ImageView imgLocation;

    @FXML
    private ImageView imgPerson;

    @FXML
    private Label lblCarDesc;

    @FXML
    private Label lblCusLocation;

    @FXML
    private Label lblCusState;

    @FXML
    private Label lblDropOff;

    @FXML
    private Label lblFor1Hrs;

    @FXML
    private Label lblFuel;

    @FXML
    private Label lblFuelPercent;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblPerson;

    @FXML
    private Label lblPickUp;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblSeats;

    @FXML
    private Label lblState;

    @FXML
    private Pane paneLocation;

    @FXML
    private StackPane spBooking;

    @FXML
    private StackPane tpDropOff;

    @FXML
    private StackPane tpPickUp;

    @FXML
    private VBox vboxFuel;

    @FXML
    private VBox vboxLocation;

    @FXML
    private VBox vboxPerson;

    @FXML
    void book(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("timePicker.fxml"));
            StackPane timePicker = fxmlLoader.load();
            tpPickUp.getChildren().add(timePicker);

            FXMLLoader fxmlLoader2 = new FXMLLoader();
            fxmlLoader2 = new FXMLLoader();
            fxmlLoader2.setLocation(getClass().getResource("timePicker.fxml"));
            StackPane timePicker2 = fxmlLoader2.load();
            tpDropOff.getChildren().add(timePicker2);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setCar(User customer, Car car) {
        lblState.setText(car.getAddress());
        lblCarDesc.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate());
        lblPrice.setText("RM" + car.getPrice() + "0");
        lblPerson.setText(car.getSeat() + " " + "Persons");
        lblFuelPercent.setText(car.getFuel() + "%");
        lblCusState.setText(car.getState());

        Booking booking = new Booking(customer.getUsername(), car.getId());

        btnBook.setOnAction(event -> {
            booking.setStartDate(dpPickUp.getValue().toString());
            booking.setStartTime("10:00AM");
            booking.setEndDate(dpPickUp.getValue().toString());
            booking.setEndTime("13:00PM");
            dataFactory dataFactory = new dataFactory();
            database db = dataFactory.getDB("booking");
            db.addData(booking);
        });
    }

}
