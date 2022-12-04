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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExploreController implements Initializable {

    private static User customer = LoginController.loginUser;

    public static User getCustomer() {
        return customer;
    }

    private List<Car> recentlyAdded;
    private List<Car> recommended;
    private List<Car> searchCars;

    private FXMLLoader recommendLoader;

    private FXMLLoader timePickerLoader;
    private TimePickerController timePickerController;

    private FXMLLoader timePickerLoader2;
    private TimePickerController timePickerController2;

    @FXML
    private HBox cardLayout;

    @FXML
    private DatePicker dpDropOff;

    @FXML
    private DatePicker dpPickUp;

    @FXML
    private GridPane gridRecommendCar;

    @FXML
    private Label lblRecommend;

    @FXML
    private Label lblSearchLocation;

    @FXML
    private Label lblSelectDO;

    @FXML
    private Label lblSelectPU;

    @FXML
    private StackPane spExplore,spExploreC1;

    @FXML
    private StackPane tpDropOff;

    @FXML
    private StackPane tpPickUp;

    @FXML
    private TextField txtLocation;

    public static String searchTxt;

    public static ExploreController instance ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scene.restrictDatePicker(dpPickUp, LocalDate.now(), LocalDate.now().plusMonths(6));
        Scene.restrictDatePicker(dpDropOff, LocalDate.now(), LocalDate.now().plusMonths(6));

        recentlyAdded = new ArrayList<>(recentlyAdded());
        recommended = new ArrayList<>(cars());

        try{
            for(Car car: recentlyAdded){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("recentlyAddCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                RecentlyAddCardController recentlyAddCardController = fxmlLoader.getController();
                recentlyAddCardController.setData(car);
                recentlyAddCardController.getBtnView().setOnAction(event -> {
                    FXMLLoader cusBookingLoader = new FXMLLoader();
                    cusBookingLoader.setLocation( Scene.class.getResource("cusBooking.fxml"));
                    try {
                        StackPane newStakePane = cusBookingLoader.load();
                        spExplore.getChildren().clear();
                        spExplore.getChildren().add(newStakePane);
                        CusBookingController cusBookingController = cusBookingLoader.getController();
                        cusBookingController.setCar(customer, car);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                cardLayout.getChildren().add(cardBox);
            }

            loadHorizontalCars(recommended);

            timePickerLoader = new FXMLLoader();
            timePickerLoader.setLocation(getClass().getResource("timePicker.fxml"));
            StackPane timePicker = timePickerLoader.load();
            tpPickUp.getChildren().add(timePicker);

            timePickerLoader2 = new FXMLLoader();
            timePickerLoader2.setLocation(getClass().getResource("timePicker.fxml"));
            StackPane timePicker2 = timePickerLoader2.load();
            tpDropOff.getChildren().add(timePicker2);

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
            if(car.getState().equalsIgnoreCase(searchTxt) && car.getStatus().equals("Available")){
                searchCars.add(car);
            }
        }
        return searchCars;
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
    private void search(ActionEvent event) throws IOException {
        timePickerController = timePickerLoader.getController();
        timePickerController2 = timePickerLoader2.getController();

        if(!txtLocation.getText().equals("") && dpPickUp.getValue() != null && !timePickerController.getTime().equals("00:00 PM") &&
                dpDropOff.getValue() != null && !timePickerController2.getTime().equals("00:00 PM")){
            if(carVal(txtLocation.getText())){
                if(timeVal(dpPickUp.getValue(), dpDropOff.getValue(), timePickerController.getTime(),
                        timePickerController2.getTime())){
                    searchTxt = txtLocation.getText();
                    lblRecommend.setText("Results of ‘" + searchTxt + "’");
                    loadHorizontalCars(searchCars());
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Time error");
                    alert.setHeaderText("Pick-up time must be scheduled before the drop-off time.");
                    alert.setContentText("Please try again.");
                    alert.show();
                }
            }else{
                noResultScene();
            }
        }else{
            lblSearchLocation.setVisible(true);
            lblSelectPU.setVisible(true);
            lblSelectDO.setVisible(true);
        }
    }

    private void noResultScene(){
        try{
            FXMLLoader noResultLoader = new FXMLLoader();
            noResultLoader.setLocation(getClass().getResource("noResult.fxml"));
            StackPane spNoResult = noResultLoader.load();
            NoResultController noResultController = noResultLoader.getController();
            noResultController.setText(txtLocation.getText());
            spExploreC1.getChildren().add(spNoResult);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private boolean carVal(String text){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> recommandCars = new ArrayList<>(db.getAllData());
        for(Car car: recommandCars){
            if(car.getState().equalsIgnoreCase(text) && car.getStatus().equals("Available")){
                return true;
            }
        }
        return false;
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

    private void loadHorizontalCars(List<Car> list) throws IOException {
        gridRecommendCar.getChildren().clear();

        int column = 0;
        int row = 1;

        for(Car car: list){
            recommendLoader = new FXMLLoader();
            recommendLoader.setLocation(getClass().getResource("recommendedCar.fxml"));
            VBox carBox = recommendLoader.load();
            RecommendedCarController recommendedCarController = recommendLoader.getController();
            recommendedCarController.setData(car);
            recommendedCarController.getBtnView().setOnAction(event -> {
                FXMLLoader cusBookingLoader = new FXMLLoader();
                cusBookingLoader.setLocation( Scene.class.getResource("cusBooking.fxml"));
                try {
                    StackPane newStakePane = cusBookingLoader.load();
                    spExplore.getChildren().clear();
                    spExplore.getChildren().add(newStakePane);
                    CusBookingController cusBookingController = cusBookingLoader.getController();
                    cusBookingController.setCar(customer, car);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
    }
}
