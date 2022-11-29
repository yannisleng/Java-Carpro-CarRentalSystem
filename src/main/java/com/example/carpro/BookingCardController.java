package com.example.carpro;

import com.model.Booking;
import com.model.Car;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingCardController implements Initializable {
    @FXML
    private Label bookingFrom;

    @FXML
    private Label bookingTo;

    @FXML
    private ImageView carImg;

    @FXML
    private Label location;

    @FXML
    private Label name;

    @FXML
    private Label orderId;

    @FXML
    private Label plateNum;

    private String imgPath = "file:src/main/resources/com/example/carpro/img/car/";

    public Label getOrderId(){return  orderId;}

    public void setData(Booking booking, Car car){
        bookingFrom.setText(booking.getStartDate()+" "+booking.getStartTime());
        bookingTo.setText(booking.getEndDate()+" "+booking.getEndTime());
        carImg.setImage(new Image(imgPath+car.getImgsrc()));
        location.setText(car.getState());
        name.setText(car.getBrand()+" - "+car.getModel());
        orderId.setText("#"+booking.getId());
        plateNum.setText(car.getNumPlate());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
