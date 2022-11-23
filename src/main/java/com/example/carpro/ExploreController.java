package com.example.carpro;

import com.model.Car;
import com.model.User;
import com.model.dataFactory;
import com.model.database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class ExploreController implements Initializable {

    private static User customer = LoginController.loginUser;

    public static User getCustomer() {
        return customer;
    }

    private static Car selectedCar;

    public static Car getSelectedCar() {
        return selectedCar;
    }

    private List<Car> recentlyAdded;
    private List<Car> recommended;

    @FXML
    private Button btnSearch;

    @FXML
    private HBox cardLayout;

    @FXML
    private DatePicker dpDropOff;

    @FXML
    private DatePicker dpPickUp;

    @FXML
    private GridPane gridRecommendCar;

    @FXML
    private ImageView imgLocation;

    @FXML
    private Label lblDropOff;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblPickUp;

    @FXML
    private Label lblRecentlyAdd;

    @FXML
    private Label lblRecommend;

    @FXML
    private Pane pExplore;

    @FXML
    private ScrollPane scrollPaneRecentlyAdd;

    @FXML
    public StackPane spExplore;

    @FXML
    private StackPane spExploreC1;

    @FXML
    private Separator spr1;

    @FXML
    private Separator spr2;

    @FXML
    private StackPane tpDropOff;

    @FXML
    private StackPane tpPickUp;

    @FXML
    private TextField txtLocation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        recommended = new ArrayList<>(cars());

        int column = 0;
        int row = 1;

        try{
            for(Car car: recentlyAdded){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("recentlyAddCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                RecentlyAddCardController recentlyAddCardController = fxmlLoader.getController();
                recentlyAddCardController.setData(car);
                recentlyAddCardController.getBtnView().setOnAction(event -> {
                    /*Scene.switchScene("cusBooking.fxml", spExplore);*/

                    FXMLLoader fxmlLoader2 = new FXMLLoader();
                    fxmlLoader2.setLocation( Scene.class.getResource("cusBooking.fxml"));

                    try {
                        StackPane newStakePane = fxmlLoader2.load();
                        spExplore.getChildren().clear();
                        spExplore.getChildren().add(newStakePane);
                        CusBookingController cusBookingController = fxmlLoader2.getController();
                        cusBookingController.setCar(customer, car);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                cardLayout.getChildren().add(cardBox);
            }

            for(Car car: recommended){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("recommendedCar.fxml"));
                VBox carBox = fxmlLoader.load();
                RecommendedCarController recommendedCarController = fxmlLoader.getController();
                recommendedCarController.setData(car);
                recommendedCarController.getBtnView().setOnAction(event -> {
                    Scene.switchScene("cusBooking.fxml", spExplore);
                    selectedCar = car;
                });

                if(column == 3){
                    column = 0;
                    ++row;
                }

                gridRecommendCar.add(carBox, column++, row);

                if(column % 3 != 0){
                    GridPane.setMargin(carBox, new Insets(-25,53, 55,0));
                }else{
                    GridPane.setMargin(carBox, new Insets(-25,0, 55,0));
                }
            }

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("timePicker.fxml"));
            StackPane timePicker = fxmlLoader.load();
            tpPickUp.getChildren().add(timePicker);

            FXMLLoader fxmlLoader2 = new FXMLLoader();
            fxmlLoader2 = new FXMLLoader();
            fxmlLoader2.setLocation(getClass().getResource("timePicker.fxml"));
            StackPane timePicker2 = fxmlLoader2.load();
            tpDropOff.getChildren().add(timePicker2);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private List<Car> recentlyAdded(){
        List<Car> carlist = new ArrayList<>();

        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> recentCars = new ArrayList<>(db.getAllData());

        for(int i = recentCars.size()-10; i < recentCars.size(); i++){
            Car car = recentCars.get(i);
            if(car.getStatus().equals("Available")){
                carlist.add(car);
            }
        }
        return carlist;
    }

    private List<Car> cars(){
        List<Car> carlist = new ArrayList<>();

        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> recommandCars = new ArrayList<>(db.getAllData());

        for(Car car: recommandCars){
            if(car.getState().equals(customer.getState()) && car.getStatus().equals("Available")){
                carlist.add(car);
            }
        }
        return carlist;
    }

    @FXML
    private void search(ActionEvent event) {
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> recommandCars = new ArrayList<>(db.getAllData());

        int count = 0;



        for(Car car: recommandCars){
            if(car.getState().equals(txtLocation.getText()) && car.getStatus().equals("Available")){
                count++;
            }
        }

        if(count != 0){
            Scene.switchScene("searchResult.fxml", spExploreC1);
        }
    }
}
