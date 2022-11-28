package com.example.carpro;

import com.model.Booking;
import com.model.Car;
import com.model.dataFactory;
import com.model.database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        initial();
    }

    private void loadBooking(List<Booking> bookings)throws IOException{
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> cars = new ArrayList<>(db.getAllData());

        bookingContainer.getChildren().clear();

        int column = 0;
        int row = 1;

        for (Booking booking : bookings){
            for(Car car:cars){
                if(booking.getCarId().equals(car.getId())){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("bookingCard.fxml"));

                    VBox bookingCard = fxmlLoader.load();
                    BookingCardController bcc = fxmlLoader.getController();
                    bcc.setData(booking,car);

                    if(column == 2){
                        column = 0;
                        ++row;
                    }
                    bookingContainer.add(bookingCard,column++,row);
                    GridPane.setMargin(bookingCard, new Insets(0,10,10,0));
                }
            }

        }
    }

    private List<Booking> getBookingList(String status){
        List<Booking> bookingList = new ArrayList<>();

        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("booking");
        List<Booking> bookings = new ArrayList<>(db.getAllData());

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

        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("booking");
        List<Booking> bookings = new ArrayList<>(db.getAllData());

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
