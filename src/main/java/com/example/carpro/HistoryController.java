package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    private User customer = LoginController.loginUser;

    @FXML
    private Button btnEnded;

    @FXML
    private Button btnOngoing;

    @FXML
    private Button btnPending;

    @FXML
    private GridPane gridHistory;

    @FXML
    private StackPane spHistory;

    private dataFactory dataFactory = new dataFactory();
    private database db = dataFactory.getDB("booking");
    private List<Booking> fullBookings = new ArrayList<>(db.getAllData());

    private database dbCar = dataFactory.getDB("car");
    private List<Car> cars = new ArrayList<>(dbCar.getAllData());

    @FXML
    private void view(ActionEvent event) {
        if(event.getSource()==btnPending || event.getSource()==btnPending){
            loadBookingCard(bookingList("Pending"));
        }else if(event.getSource()==btnOngoing || event.getSource()==btnOngoing){
            loadBookingCard(bookingList("Ongoing"));
        }else if(event.getSource()==btnEnded || event.getSource()==btnEnded){
            loadBookingCard(bookingList("Ended"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Booking cusBooking: fullBookings){
           if(cusBooking.getEndDate().isBefore(LocalDate.now()) && cusBooking.getStatus().equals("Approved")){
               setReturn(cusBooking);

               cusBooking.setStatus("Completed");
               db.updateData(cusBooking);
               loadBookingCard(bookingList("Ended"));
           }
        }
        loadBookingCard(bookingList("Ongoing"));
    }

    private List<Booking> bookingList(String status){
        List<Booking> cusBooking = new ArrayList<>();

        if(status.equals("Pending")){
            setBlack(btnPending);
            setWhite(btnOngoing);
            setWhite(btnEnded);
            for(Booking booking: fullBookings){
                if(booking.getCustomerId().equals(customer.getUsername()) && booking.getStatus().equals("Pending")){
                    cusBooking.add(booking);
                }
            }
        }else if(status.equals("Ongoing")){
            setBlack(btnOngoing);
            setWhite(btnPending);
            setWhite(btnEnded);
            for(Booking booking: fullBookings){
                if(booking.getCustomerId().equals(customer.getUsername()) && booking.getStatus().equals("Approved") &&
                booking.getPaymentId().equals("null")){
                    cusBooking.add(booking);
                }
            }
        }else if(status.equals("Ended")){
            setBlack(btnEnded);
            setWhite(btnPending);
            setWhite(btnOngoing);
            for(Booking booking: fullBookings){
                if(booking.getCustomerId().equals(customer.getUsername()) && (booking.getStatus().equals("Completed") ||
                        (booking.getStatus().equals("Rejected")))){
                    cusBooking.add(booking);
                }
            }
        }
        return cusBooking;
    }

    private void loadBookingCard(List<Booking> bookings){

        int column = 0;
        int row = 1;

        gridHistory.getChildren().clear();

        if(!bookings.isEmpty()){
            try{
                for(Booking booking: bookings){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("historyCard.fxml"));
                    VBox historyBox = fxmlLoader.load();
                    HistoryCardController historyCardController = fxmlLoader.getController();
                    historyCardController.setData(booking);
                    if(booking.getStatus().equals("Completed") && booking.getPaymentId().equals("null")){
                        historyCardController.getBtnReturn().setText("Pay");
                        historyCardController.getBtnReturn().setOnAction(event -> {
                            FXMLLoader fxmlLoader2 = new FXMLLoader();
                            fxmlLoader2.setLocation( Scene.class.getResource("payment.fxml"));

                            try {
                                StackPane newStakePane = fxmlLoader2.load();
                                spHistory.getChildren().clear();
                                spHistory.getChildren().add(newStakePane);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            PaymentController paymentController = fxmlLoader2.getController();
                            paymentController.setData(booking);
                        });
                    }else if(booking.getStatus().equals("Pending")){
                        historyCardController.getBtnReturn().setText("Pending");
                    }else if(booking.getStatus().equals("Approved")){
                        historyCardController.getBtnReturn().setOnAction(event -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Return car");
                            alert.setHeaderText("You're about to return car.");
                            alert.setContentText("Are you sure you want to return the car?");

                            if(alert.showAndWait().get() == ButtonType.OK){
                                setReturn(booking);

                                booking.setStatus("Completed");
                                db.updateData(booking);
                                loadBookingCard(bookingList("Ended"));
                            }
                        });
                    }else if(booking.getStatus().equals("Rejected")){
                        historyCardController.getBtnReturn().setText("Rejected");
                    }else if(booking.getStatus().equals("Completed") && !booking.getPaymentId().equals("null")) {
                        historyCardController.getBtnReturn().setText("Paid");
                        historyCardController.getBtnReturn().setOnAction(event -> {
                            ReceiptController receipt = CusController.instance.receipt();
                            receipt.setData(booking);
                        });
                    }

                    if(column == 1){
                        column = 0;
                        ++row;
                    }

                    gridHistory.add(historyBox, column++, row);
                    GridPane.setMargin(historyBox, new Insets(-25,0, 35,0));
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            FXMLLoader noResultLoader = new FXMLLoader();
            noResultLoader.setLocation( Scene.class.getResource("noResult2.fxml"));
            try {
                VBox noResultVBox = noResultLoader.load();
                gridHistory.add(noResultVBox,0,1);
                GridPane.setMargin(noResultVBox, new Insets(-30,0, 8,0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setBlack(Button button){
        button.setStyle("-fx-background-color: #000000; -fx-background-radius: 8px; -fx-text-fill: #ffffff; " +
                "-fx-font-weight: bold; -fx-font-size: 20px;");
    }

    private void setWhite(Button button){
        button.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-color: #000000;" +
                "-fx-border-radius: 8px; -fx-text-fill: #000000; -fx-font-weight: bold; -fx-font-size: 20px;");
    }

    private void setReturn(Booking booking){
        for(int i = 0; i < cars.size(); i++){
            Car car = cars.get(i);
            if(car.getId().equals(booking.getCarId())){
                car.setStatus("Available");
                car.setFuel(car.getFuel()-generateRandomNumber(5,70));
                if(car.getFuel()<=0){
                    car.setFuel(99);
                }
                dbCar.updateData(car);
            }
        }
    }

    private int generateRandomNumber(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
