package com.example.carpro;

import com.model.Car;
import com.model.User;
import com.model.dataFactory;
import com.model.database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private GridPane gridSearch;

    @FXML
    private Label lblSearch;

    private User customer = ExploreController.getCustomer();

    private List<Car> searchCars;

    private String searchTxt = ExploreController.getSearchTxt();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchCars = new ArrayList<>(searchCars());

        int column = 0;
        int row = 1;

        int count = 0;
        try{
            for(Car car: searchCars){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("recommendedCar.fxml"));
                VBox carBox = fxmlLoader.load();
                RecommendedCarController recommendedCarController = fxmlLoader.getController();
                recommendedCarController.setData(car);
                recommendedCarController.getBtnView().setOnAction(event -> {
                    CusBookingController cusBookingController = (CusBookingController)
                            Scene.getController("cusBooking.fxml", ExploreController.instance.getSpExploreS());
                    cusBookingController.setCar(customer, car);
                });

                count++;

                if(column == 3){
                    column = 0;
                    ++row;
                }

                gridSearch.add(carBox, column++, row);

                if(column % 3 != 0){
                    GridPane.setMargin(carBox, new Insets(-25,50, 35,0));
                }else{
                    GridPane.setMargin(carBox, new Insets(-25,0, 35,0));
                }
            }
            lblSearch.setText("About " + count + " results of ‘" + searchTxt + "’");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private List<Car> searchCars(){
        List<Car> searchCars = new ArrayList<>();

        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> cars = new ArrayList<>(db.getAllData());

        for(Car car: cars){
            if((car.getState().equalsIgnoreCase(searchTxt) && car.getStatus().equals("Available")) ||
                    (car.getAddress().contains(searchTxt) && car.getStatus().equals("Available"))){
                searchCars.add(car);
            }
        }
        return searchCars;
    }
}
