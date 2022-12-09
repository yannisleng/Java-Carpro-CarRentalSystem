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

    private FXMLLoader recommendLoader;

    private TimePickerController timePickerController;
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
    private Label lblSearchLocation;

    @FXML
    private Label lblSelectDO;

    @FXML
    private Label lblSelectPU;

    @FXML
    private StackPane spExplore, spExploreS, spExploreC1;

    public StackPane getSpExploreS() {
        return spExploreS;
    }

    @FXML
    private StackPane tpDropOff;

    @FXML
    private StackPane tpPickUp;

    @FXML
    private TextField txtLocation;

    private static String searchTxt;

    public static String getSearchTxt() {
        return searchTxt;
    }

    public static ExploreController instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        spExploreS = spExplore;

        Scene.restrictDatePicker(dpPickUp, LocalDate.now(), LocalDate.now().plusMonths(6));
        Scene.restrictDatePicker(dpDropOff, LocalDate.now(), LocalDate.now().plusMonths(6));

        recentlyAdded = new ArrayList<>(recentlyAddedCars());
        recommended = new ArrayList<>(recommendedCars());

        try{
            loadHorizontalCars(recommended);
            loadVerticalCar(recentlyAdded);

            timePickerController = (TimePickerController) Scene.getController("timePicker.fxml", tpPickUp);
            timePickerController2 = (TimePickerController) Scene.getController("timePicker.fxml", tpDropOff);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private List<Car> searchedCars(){
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

    private List<Car> recentlyAddedCars(){
        List<Car> carlist = new ArrayList<>();

        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> recentCars = new ArrayList<>(db.getAllData());

        for(int i = recentCars.size()-15; i < recentCars.size(); i++){
            Car car = recentCars.get(i);
            if(car.getStatus().equals("Available")){
                carlist.add(car);
            }
        }
        return carlist;
    }

    private List<Car> recommendedCars(){
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
        if(!txtLocation.getText().equals("")){
            lblSelectPU.setVisible(false);
            lblSearchLocation.setVisible(false);
            if(dpPickUp.getValue() != null && !timePickerController.getTime().equals("00:00 PM")){
                lblSelectPU.setVisible(false);
                if(dpDropOff.getValue() != null && !timePickerController2.getTime().equals("00:00 PM")){
                    lblSelectDO.setVisible(false);
                    if(timeVal(dpPickUp.getValue(), dpDropOff.getValue(), timePickerController.getTime(),
                            timePickerController2.getTime())){
                        if(carVal(txtLocation.getText())){
                            searchTxt = txtLocation.getText();
                            Scene.switchScene("searchResult.fxml", spExploreC1);
                        }else{
                            noResultScene();
                        }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Time error");
                        alert.setHeaderText("Pick-up time must be scheduled before the drop-off time.");
                        alert.setContentText("Please try again.");
                        alert.show();
                    }
                }else{
                    lblSelectDO.setVisible(true);
                }
            }else{
                lblSelectPU.setVisible(true);
            }
        }else{
            lblSearchLocation.setVisible(true);
        }
    }

    private void noResultScene(){
        NoResultController noResultController = (NoResultController) Scene.getController("noResult.fxml", spExploreC1);
        noResultController.setText(txtLocation.getText());
    }

    private boolean carVal(String text){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> cars = new ArrayList<>(db.getAllData());
        for(Car car: cars){
            if((car.getState().equalsIgnoreCase(text) && car.getStatus().equals("Available")) ||
                    (car.getAddress().contains(text) && car.getStatus().equals("Available"))){
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

    private void loadVerticalCar(List<Car> list) throws IOException{
        try{
            for(Car car: list){
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
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
