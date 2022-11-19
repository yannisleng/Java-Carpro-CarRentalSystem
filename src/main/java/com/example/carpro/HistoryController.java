package com.example.carpro;

import com.model.Car;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML
    private GridPane gridHistory;

    private List<Car> history;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        history = new ArrayList<>(cars());

        int column = 0;
        int row = 1;

        try{
            for(Car car: history){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("historyCard.fxml"));
                VBox historyBox = fxmlLoader.load();
                HistoryCardController historyCardController = fxmlLoader.getController();
                historyCardController.setData(car);

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
