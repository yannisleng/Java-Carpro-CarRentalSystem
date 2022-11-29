package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CarBookingInfoController implements Initializable {

    @FXML
    private Label bookingFromLbl;

    @FXML
    private Label bookingToLbl;

    @FXML
    private Label brandModelLbl;

    @FXML
    private ImageView carImg;

    @FXML
    private Label fuelLbl;

    @FXML
    private Label fullNameLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label orderIdLbl;

    @FXML
    private Label plateNumLbl;

    @FXML
    private Label priceLbl;

    @FXML
    private ImageView profileImg;

    @FXML
    private Label seatsLbl;

    @FXML
    private Label statusLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private Button exitBtn;

    @FXML
    private Button approveBtn;

    @FXML
    private Button rejectBtn;

    private String carImgPath = "file:src/main/resources/com/example/carpro/img/car/";
    private String userImgPath = "file:src/main/resources/com/example/carpro/img/profile_pic/";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Button getExitBtn(){return exitBtn;}

    public Button getApproveBtn(){return approveBtn;}

    public Button getRejectBtn(){return rejectBtn;}

    public Label getOrderIdLbl(){return orderIdLbl;}

    public void setData(Booking booking, Car car, User user){
        bookingFromLbl.setText(booking.getStartDate().toString().substring(2)+" "+booking.getStartTime());
        bookingToLbl.setText(booking.getEndDate().toString().substring(2)+" "+booking.getEndTime());
        brandModelLbl.setText(car.getBrand()+" - "+car.getModel());
        carImg.setImage(new Image(carImgPath+car.getImgsrc()));
        fuelLbl.setText(car.getFuel()+" %");
        fullNameLbl.setText(user.getFirstName()+" "+user.getLastName());
        locationLbl.setText(car.getAddress()+"\n"+car.getPostCode()+" "+car.getState());
        orderIdLbl.setText("#"+booking.getId());
        plateNumLbl.setText(car.getNumPlate());
        priceLbl.setText("RM "+String.format("%.02f",car.getPrice())+"/hour");
        profileImg.setImage(new Image(userImgPath+user.getProfilePic()));
        seatsLbl.setText(String.valueOf(car.getSeat()));
        statusLbl.setText(booking.getStatus());
        usernameLbl.setText(user.getUsername());
        statusCustomize(booking.getStatus());
    }

    private void statusCustomize(String status){
        if(status.equals("Pending")){
            statusLbl.setStyle("-fx-text-fill: #F24E1E");
            approveBtn.setVisible(true);rejectBtn.setVisible(true);
        }else if(status.equals("Approved")){
            statusLbl.setStyle("-fx-text-fill: #4D67EE");
            approveBtn.setVisible(false);rejectBtn.setVisible(false);
        }else if(status.equals("Completed")){
            statusLbl.setStyle("-fx-text-fill: #00C620");
            approveBtn.setVisible(false);rejectBtn.setVisible(false);
        }else if(status.equals("Rejected")){
            statusLbl.setStyle("-fx-text-fill: #FD0D0D");
            approveBtn.setVisible(false);rejectBtn.setVisible(false);
        }
    }
}
