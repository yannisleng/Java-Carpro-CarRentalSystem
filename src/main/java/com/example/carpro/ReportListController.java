package com.example.carpro;

import com.model.Booking;
import com.model.Payment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportListController implements Initializable {
    @FXML
    private Label carID;

    @FXML
    private Label customer;

    @FXML
    private Label date;

    @FXML
    private Label paymentID;

    @FXML
    private Label paymentType;

    @FXML
    private Label total;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public  void setData(Payment payment, Booking booking){
        paymentID.setText(payment.getId());
        date.setText(payment.getDate().toString());
        customer.setText(booking.getCustomerId());
        carID.setText(booking.getCarId());
        paymentType.setText(payment.getMethod());
        total.setText(String.format("RM "+String.format("%.02f",payment.getTotal())));
    }
}
