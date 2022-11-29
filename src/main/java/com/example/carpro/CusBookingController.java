package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CusBookingController implements Initializable {
    private User customer = ExploreController.getCustomer();

    private FXMLLoader timePickerLoader;
    private TimePickerController timePickerController;

    private FXMLLoader timePickerLoader2;
    private TimePickerController timePickerController2;

    private dataFactory dataFactory = new dataFactory();
    private database db = dataFactory.getDB("booking");
    private List<Booking> bookingList = new ArrayList<>(db.getAllData());

    @FXML
    private Button btnBook;

    @FXML
    private DatePicker dpDropOff;

    @FXML
    private DatePicker dpPickUp;

    @FXML
    private ImageView imgCar;

    @FXML
    private Label lblADO;

    @FXML
    private Label lblAPU;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblCarDesc;

    @FXML
    private Label lblCusState;

    @FXML
    private Label lblFuelPercent;

    @FXML
    private Label lblPerson;

    @FXML
    private Label lblPrice;

    @FXML
    private StackPane spBooking;

    @FXML
    private StackPane tpDropOff;

    @FXML
    private StackPane tpPickUp;

    @FXML
    private Tooltip ttAddress;

    @FXML
    void cancel(ActionEvent event) {
        Scene.switchScene("explore.fxml", spBooking);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            timePickerLoader = new FXMLLoader();
            timePickerLoader.setLocation(getClass().getResource("timePicker.fxml"));
            StackPane timePicker = timePickerLoader.load();
            tpPickUp.getChildren().add(timePicker);

            timePickerLoader2 = new FXMLLoader();
            timePickerLoader2 = new FXMLLoader();
            timePickerLoader2.setLocation(getClass().getResource("timePicker.fxml"));
            StackPane timePicker2 = timePickerLoader2.load();
            tpDropOff.getChildren().add(timePicker2);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setCar(User customer, Car car) {
        timePickerController = timePickerLoader.getController();
        timePickerController2 = timePickerLoader2.getController();

        lblAddress.setText(car.getAddress());
        ttAddress.setText(car.getAddress());
        imgCar.setImage(new Image("file:src/main/resources/com/example/carpro/img/car/" + car.getImgsrc()));
        lblCarDesc.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate());
        lblPrice.setText("RM" + car.getPrice() + "0");
        lblPerson.setText(car.getSeat() + " " + "Persons");
        lblFuelPercent.setText(car.getFuel() + "%");
        lblCusState.setText(car.getState());

        Booking booking = new Booking(customer.getUsername(), car.getId());

        btnBook.setOnAction(event -> {
            booking.setId(generateId());
            booking.setStartDate(dpPickUp.getValue());
            booking.setStartTime(timePickerController.getTime());
            booking.setEndDate(dpDropOff.getValue());
            booking.setEndTime(timePickerController2.getTime());

            if(hisVal()){
                if(dpPickUp.getValue() != null && !timePickerController.getTime().equals("00:00 PM") &&
                        dpDropOff.getValue() != null && !timePickerController2.getTime().equals("00:00 PM")){
                    if(timeVal(booking.getStartDate(), booking.getEndDate(), booking.getStartTime(), booking.getEndTime())){
                        lblAPU.setVisible(false);
                        lblADO.setVisible(false);

                        db.addData(booking);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Time error");
                        alert.setHeaderText("Pick-up time must be scheduled before the drop-off time.");
                        alert.setContentText("Please try again.");
                        alert.show();
                    }
                }else{
                    lblAPU.setVisible(true);
                    lblADO.setVisible(true);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Booking failed");
                alert.setHeaderText("You're having a car on the road/ pending payment.");
                alert.setContentText("Book a new car once order completed.");
                alert.show();
            }
        });
    }

    private boolean hisVal(){
        bookingList = new ArrayList<>(db.getAllData());
        for(Booking booking: bookingList){
            if(booking.getCustomerId().equals(customer.getUsername())){
                if(booking.getStatus().equals("Pending")){
                    return false;
                }else if(booking.getStatus().equals("Approved") && booking.getPaymentId().equals("null")){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean timeVal(LocalDate puDate, LocalDate doDate, String puTime, String doTime){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm aa");

        if(puDate.isAfter(doDate)){
            return false;
        }else if(puDate.isEqual(doDate)){
            try {
                if(df.parse(puTime).compareTo(df.parse(doTime)) >= 0){
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private String generateId(){
        String bookingId;
        if(!bookingList.isEmpty()){
            Booking lastBooking = bookingList.get(bookingList.size()-1);
            bookingId= lastBooking.getId().substring(1,6);

            bookingId = "O" + String.format("%05d", (Integer.parseInt(bookingId) + 1));
        }else{
            bookingId = "O00001";
        }
        return bookingId;
    }
}
