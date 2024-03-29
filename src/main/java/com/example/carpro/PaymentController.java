package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentController {

    @FXML
    private Button btnCC;

    @FXML
    private Button btnEWallet;

    @FXML
    private Button btnTransfer;

    @FXML
    private ImageView imgCar;

    @FXML
    private Label lblBookingId;

    @FXML
    private Label lblCarDesc;

    @FXML
    private Label lblEndTime;

    @FXML
    private Label lblHour;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblSeats;

    @FXML
    private Label lblStartTime;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private StackPane spPayment;

    @FXML
    private Tooltip ttPayment;

    private com.model.dataFactory dataFactory = new dataFactory();
    private database dbCar = dataFactory.getDB("car");
    private List<Car> cars = new ArrayList<>(dbCar.getAllData());

    private database dbBooking = dataFactory.getDB("booking");
    private List<Booking> bookings = new ArrayList<>(dbBooking.getAllData());

    private database dbPayment = dataFactory.getDB("payment");
    private List<Payment> payments = new ArrayList<>(dbPayment.getAllData());

    private Booking bookingToPay = new Booking();
    private Payment payment = new Payment();

    private float total = 0;

    @FXML
    private void pay(ActionEvent event) throws IOException {
        if(payment.getMethod() != null){
            payment.setId(generateId());
            payment.setTotal(total);
            dbPayment.addData(payment);
            bookingToPay.setPaymentId(payment.getId());
            for(int j = 0; j < bookings.size(); j++){
                Booking booking1 = bookings.get(j);
                if(booking1.getId().equals(bookingToPay.getId())){
                    bookings.set(j, bookingToPay);
                    updateDb("src/main/resources/com/example/carpro/database/booking.txt", bookings);
                }
            }
            ReceiptController receipt = CusController.instance.receipt();
            receipt.setData(bookingToPay);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Payment error");
            alert.setHeaderText("Please select a payment method.");
            alert.setContentText("Please try again.");
            alert.show();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Scene.switchScene("cusHistory.fxml", spPayment);
    }

    @FXML
    private void setPymtMethod(ActionEvent event) {
        if(event.getSource()==btnCC || event.getSource()==btnCC){
            setBold(btnCC);
            setOri(btnEWallet);
            setOri(btnTransfer);
            payment.setMethod("Credit Card");
        }else if(event.getSource()==btnEWallet || event.getSource()==btnEWallet){
            setBold(btnEWallet);
            setOri(btnCC);
            setOri(btnTransfer);
            payment.setMethod("E-wallet");
        }else if(event.getSource()==btnTransfer || event.getSource()==btnTransfer){
            setBold(btnTransfer);
            setOri(btnCC);
            setOri(btnEWallet);
            payment.setMethod("Transfer");
        }
    }

    public void setData(Booking booking){
        bookingToPay = booking;
        for(Car car: cars){
            if(booking.getCarId().equals(car.getId())){
                Image image = new Image("file:src/main/resources/com/example/carpro/img/car/" + car.getImgsrc());
                imgCar.setImage(image);
                lblBookingId.setText("Payment for #" + booking.getId());
                lblCarDesc.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate());
                ttPayment.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate());
                lblSeats.setText(" - " + car.getSeat() + " seats");
                lblStartTime.setText(booking.getStartDate() + ", " + booking.getStartTime());
                lblEndTime.setText(booking.getEndDate() + ", " + booking.getEndTime());
                lblLocation.setText(car.getState());
                lblPrice.setText("RM" + String.format("%.02f", car.getPrice()) + "/ hours");
                lblHour.setText("Total Hour: " + toHours(booking));
                total = car.getPrice()*toHours(booking);
                lblTotalPrice.setText("RM" + (String.format("%.02f", total)));
            }
        }
    }

    private void setBold(Button button){
        button.setStyle(button.getStyle() + "-fx-border-width: 3px;");
    }

    private void setOri(Button button){
        button.setStyle(button.getStyle() + "-fx-border-width: 1px;");
    }

    public static int toHours(Booking booking){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long diff = 0;
        try {
            Date d1 = sdf.parse(booking.getEndDate() + " " + booking.getEndTime());
            Date d2 = sdf.parse(booking.getStartDate() + " " + booking.getStartTime());
            diff = ((d1.getTime()-d2.getTime())/(1000 * 60 * 60));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) diff;
    }

    public String generateId(){
        String paymentId;
        if(!payments.isEmpty()){
            Payment payment = payments.get(payments.size()-1);
            paymentId= payment.getId().substring(1,6);

            paymentId = "P" + String.format("%05d", (Integer.parseInt(paymentId) + 1));
        }else{
            paymentId = "P00001";
        }
        return paymentId;
    }

    public static void updateDb(String fileStr, List<Booking> list){
        try {
            FileWriter file = new FileWriter(fileStr);
            for(Booking booking: list){
                file.write(String.valueOf(booking));
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
