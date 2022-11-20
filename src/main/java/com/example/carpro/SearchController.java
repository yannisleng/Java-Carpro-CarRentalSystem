package com.example.carpro;

import com.model.Car;
import com.model.dataFactory;
import com.model.database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

    @FXML
    private StackPane spExploreC2;

    @FXML
    private ScrollPane spSearch;

    private List<Car> recommended;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        recommended = new ArrayList<>(cars());

        int column = 0;
        int row = 1;

        try{
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

                gridSearch.add(carBox, column++, row);

                if(column % 3 != 0){
                    GridPane.setMargin(carBox, new Insets(-25,50, 35,0));
                }else{
                    GridPane.setMargin(carBox, new Insets(-25,0, 35,0));
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private List<Car> cars(){
        List<Car> carlist = new ArrayList<>();

        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> recommandCars = new ArrayList<>(db.getAllData());

        for(Car car: recommandCars){
            carlist.add(car);
        }
        return carlist;
    }
}
