package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationController {

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblCar;

    @FXML
    private Label lblDropOffTime;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPickUpTime;

    @FXML
    private Label lblPrice;

    @FXML
    private StackPane spConfirmation;

    @FXML
    private Tooltip ttAddress, ttCarDesc;

    private com.model.dataFactory dataFactory = new dataFactory();
    private database dbCar = dataFactory.getDB("car");
    private database dbUser = dataFactory.getDB("user");
    private List<Car> cars = new ArrayList<>(dbCar.getAllData());
    private List<User> customers = new ArrayList<>(dbUser.getAllData());

    public void setData(Booking booking){
        for(User customer: customers){
            for(Car car: cars){
                if(booking.getCarId().equals(car.getId())){
                    if(booking.getCustomerId().equals(customer.getUsername())){
                        lblName.setText("Hey! " + customer.getFirstName() + ",");
                        lblCar.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate());
                        ttCarDesc.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate());
                        lblAddress.setText(car.getAddress());
                        ttAddress.setText(car.getAddress());
                        lblPrice.setText("RM" + car.getPrice() + "/hrs");
                        lblPickUpTime.setText(booking.getStartDate() + " " + booking.getStartTime());
                        lblDropOffTime.setText(booking.getEndDate() + " " + booking.getEndTime());
                    }
                }
            }
        }
    }

    @FXML
    private void history(ActionEvent event) {
        CusController cusController = (CusController) Scene.getController("cusMain.fxml", spConfirmation);
        Scene.switchScene("cusHistory.fxml", cusController.getSpCusDefault());
        cusController.getBtnHistory().requestFocus();
    }
}
