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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    private database dbCar = dataFactory.getDB("car");
    private List<Car> cars = new ArrayList<>(dbCar.getAllData());

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
    private Tooltip ttAddress, ttCarDesc;


    @FXML
    void cancel(ActionEvent event) {
        Scene.switchScene("explore.fxml", spBooking);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scene.restrictDatePicker(dpPickUp, LocalDate.now(), LocalDate.now().plusMonths(6));
        Scene.restrictDatePicker(dpDropOff, LocalDate.now(), LocalDate.now().plusMonths(6));

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
        ttCarDesc.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate());
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
                        if(periodVal()){
                            lblAPU.setVisible(false);
                            lblADO.setVisible(false);

                            db.addData(booking);

                            car.setStatus("Unavailable");
                            for(int i = 0; i < cars.size(); i++){
                                Car txtCar = cars.get(i);
                                if(txtCar.getId().equals(car.getId())){
                                    cars.set(i, car);
                                    try {
                                        FileWriter file = new FileWriter("src/main/resources/com/example/carpro/database/car.txt");
                                        for(Car car2: cars){
                                            file.write(String.valueOf(car2));
                                        }
                                        file.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            ConfirmationController confirmation = CusController.instance.confirmation();
                            confirmation.setData(booking);
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Time error");
                            alert.setHeaderText("Please select period more than 1 hour and within 1 week.");
                            alert.setContentText("Please try again.");
                            alert.show();
                        }
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

    private boolean periodVal(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long diff = 0;
        try {
            Date d1 = sdf.parse(dpDropOff.getValue() + " " + timePickerController2.getTime());
            Date d2 = sdf.parse(dpPickUp.getValue() + " " + timePickerController.getTime());
            diff = ((d1.getTime()-d2.getTime())/(1000 * 60 * 60));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(diff < 1 || diff > 168){
            return false;
        }else{
            return true;
        }
    }
}
