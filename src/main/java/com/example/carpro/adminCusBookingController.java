package com.example.carpro;

import com.model.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class adminCusBookingController implements Initializable {

    @FXML
    private GridPane bookingContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button endedBtn;

    @FXML
    private Button ongoingBtn;

    @FXML
    private Button pendingBtn;

    @FXML
    private TextField searchBar;

    @FXML
    private StackPane carBookingInfo;

    @FXML
    private StackPane customerBookingLayout;

    @FXML
    private ProgressBar processBar ;

    dataFactory dataFactory = new dataFactory();

    database carDb = dataFactory.getDB("car");
    database bookingDb = dataFactory.getDB("booking");
    database userDb = dataFactory.getDB("user");

    List<Car> cars = new ArrayList<>(carDb.getAllData());
    List<Booking> bookings = new ArrayList<>(bookingDb.getAllData());
    List<User> users = new ArrayList<>(userDb.getAllData());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        initial();
    }

    private void loadBooking(List<Booking> bookings)throws IOException{
        processBar.setProgress(0);
        bookingContainer.getChildren().clear();

        new Thread(() -> {
            AtomicInteger column = new AtomicInteger();
            int row = 1;

            int index = 1;

            for (Booking booking : bookings){
                for(Car car:cars){
                    if(booking.getCarId().equals(car.getId())){
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("bookingCard.fxml"));

                        VBox bookingCard = null;
                        try {
                            bookingCard = fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        BookingCardController bcc = fxmlLoader.getController();
                        bcc.setData(booking,car);

                        bookingCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                String id = bcc.getOrderId().getText().substring(1,bcc.getOrderId().getText().length());
                                BookingCardEventHandler(id);
                                carBookingInfo.setVisible(true);
                                processBar.setVisible(false);
                                carBookingInfo.toFront();
                            }
                        });

                        if(column.get() == 2){
                            column.set(0);
                            ++row;
                        }
                        int finalRow = row;
                        int finalIndex = index;
                        VBox finalBookingCard = bookingCard;
                        Platform.runLater(() -> {
                            processBar.setVisible(true);
                            bookingContainer.add(finalBookingCard, column.getAndIncrement(), finalRow);
                            GridPane.setMargin(finalBookingCard, new Insets(0,10,10,0));
                            processBar.setProgress((double) finalIndex /bookings.size());
                        });
                    }
                }
                //System.out.println("index: "+index);
                index++;
            }
        }).start();
        processBar.setVisible(false);
    }
    private void BookingCardEventHandler(String orderId){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("carBookingInfo.fxml"));

        Car thisCar = new Car();
        User thisUser = new User();

        for (Booking booking:bookings){
            if(booking.getId().equals(orderId)){

                for (Car car:cars){
                    if (booking.getCarId().equals(car.getId())){
                        thisCar = car;
                    }
                }
                for (User user:users){
                    if(booking.getCustomerId().equals(user.getUsername())){
                        thisUser = user;
                    }
                }

                try {
                    StackPane stackPane = fxmlLoader.load();
                    CarBookingInfoController cbi = fxmlLoader.getController();
                    cbi.setData(booking,thisCar,thisUser);
                    carBookingInfo.getChildren().clear();
                    carBookingInfo.getChildren().add(stackPane);

                    cbi.getExitBtn().setOnAction(event -> {
                        carBookingInfo.setVisible(false);
                    });

                    cbi.getApproveBtn().setOnAction(event -> {
                        handleAction("Approve",cbi.getOrderIdLbl().getText().substring(1));
                    });

                    cbi.getRejectBtn().setOnAction(event -> {
                        handleAction("Reject",cbi.getOrderIdLbl().getText().substring(1));
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleAction(String action, String orderId){
        Booking booking = new Booking();

        dataFactory dataFactory = new dataFactory();
        database bookingDb = dataFactory.getDB("booking");
        List<Booking> bookings = new ArrayList<>(bookingDb.getAllData());

        for(Booking book:bookings){
            if (book.getId().equals(orderId)){
                booking = book;
            }
        }

        if(action=="Approve"){
            booking.setStatus("Approved");
            bookingDb.updateData(booking);
        }else if(action=="Reject"){
            booking.setStatus("Rejected");
            bookingDb.updateData(booking);
        }

        Scene.switchScene("customerBooking.fxml",customerBookingLayout);
    }

    private List<Booking> getBookingList(String status){
        List<Booking> bookingList = new ArrayList<>();

        for(Booking booking:bookings){
            if(booking.getStatus().equals(status)){
                bookingList.add(booking);
            }
        }

        return bookingList;
    }

    private void initial(){
        List<Booking> pendingList = new ArrayList<>(getBookingList("Pending"));
        pendingBtn.setStyle("-fx-background-color:#000000");ongoingBtn.setStyle("-fx-background-color:#d9d9d9");endedBtn.setStyle("-fx-background-color:#d9d9d9");
        try{
            loadBooking(pendingList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void changeStatus(ActionEvent event){
        if (event.getSource()==pendingBtn){
            initial();
        }else if(event.getSource()==ongoingBtn){
            pendingBtn.setStyle("-fx-background-color:#d9d9d9");ongoingBtn.setStyle("-fx-background-color:#000000");endedBtn.setStyle("-fx-background-color:#d9d9d9");
            List<Booking> ongoingList = new ArrayList<>(getBookingList("Approved"));
            try{
                loadBooking(ongoingList);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else if(event.getSource()==endedBtn){
            pendingBtn.setStyle("-fx-background-color:#d9d9d9");ongoingBtn.setStyle("-fx-background-color:#d9d9d9");endedBtn.setStyle("-fx-background-color:#000000");
            List<Booking> completedList = new ArrayList<>(getBookingList("Completed"));
            List<Booking> rejectedList = new ArrayList<>(getBookingList("Rejected"));

            //merge two list
            List<Booking> endedList = new ArrayList<>(completedList);
            endedList.addAll(rejectedList);

            try{
                loadBooking(endedList);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private List<Booking> getSearchList(String bookingId){
        List<Booking> bookingList = new ArrayList<>();
        List<Booking> bookings = new ArrayList<>(bookingDb.getAllData());

        for(Booking booking:bookings){
            if(booking.getId().equals(bookingId)){
                bookingList.add(booking);
            }
        }

        return bookingList;
    }

    @FXML
    private void searchBooking(ActionEvent event){
        List<Booking> search = new ArrayList<>(getSearchList(searchBar.getText()));
        try{
            loadBooking(search);
        }catch (IOException e){
            e.printStackTrace();
        }

        if(searchBar.getText().isEmpty()){
            initial();
        }
    }
}
