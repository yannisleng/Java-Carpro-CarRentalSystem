package com.example.carpro;

import com.model.Car;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExploreController implements Initializable {
    @FXML
    private HBox cardLayout;

    @FXML
    private GridPane gridRecommendCar;

    private List<Car> recentlyAdded;
    private List<Car> recommended;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        recommended = new ArrayList<>(cars());

        int column = 0;
        int row = 1;

        try{
            for(Car value: recentlyAdded){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("recentlyAddCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                RecentlyAddCardController recentlyAddCardController = fxmlLoader.getController();
                recentlyAddCardController.setData(value);
                cardLayout.getChildren().add(cardBox);
            }

            for(Car car: recommended){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("recommendedCar.fxml"));
                VBox carBox = fxmlLoader.load();
                RecommendedCarController recommendedCarController = fxmlLoader.getController();
                recommendedCarController.setData(car);

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
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private List<Car> recentlyAdded(){
        List<Car> ls = new ArrayList<>();
        Car car = new Car();
        car.setModel("Toyota Vios");
        car.setState("Kuala Lumpur");
        ls.add(car);
        ls.add(car);
        ls.add(car);
        return ls;
    }

    private List<Car> cars(){
        List<Car> ls = new ArrayList<>();
        Car car = new Car();
        car.setModel("Toyota Vios");
        car.setState("Kuala Lumpur");
        ls.add(car);
        ls.add(car);
        ls.add(car);
        ls.add(car);
        ls.add(car);
        ls.add(car);
        ls.add(car);
        ls.add(car);
        ls.add(car);
        return ls;
    }
}
