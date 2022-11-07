package com.example.carpro;

import com.model.Car;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        try{
            for(int i = 0; i < recentlyAdded.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("recentlyAddCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                RecentlyAddCardController recentlyAddCardController = fxmlLoader.getController();
                recentlyAddCardController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(cardBox);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private List<Car> recentlyAdded(){
        List<Car> ls = new ArrayList<>();
        Car car = new Car();
        car.setModel("Toyota Vios");
        car.setLocation("Kuala Lumpur");
        ls.add(car);
        ls.add(car);
        ls.add(car);
        return ls;
    }
}
