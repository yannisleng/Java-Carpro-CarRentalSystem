package com.example.carpro;
import com.model.database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.model.Car;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addeditcar_Main_Controller implements Initializable {

    @FXML
    private VBox carlistLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Car> car = new ArrayList<>(readcar());
        for (int i=0; i< car.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addeditCar_item.fxml"));

            try{
                HBox hBox = fxmlLoader.load();
                addeditCar_item_Controller aic = fxmlLoader.getController();
                aic.setData(car.get(i));
                carlistLayout.getChildren().add(hBox);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public List<Car> readcar(){

        database db = new database();
        ArrayList<String> carList= new ArrayList<>();
        List<Car> ls= new ArrayList<>();

        carList = db.readFile("car.txt");
        int i;

        for (i=0;i< carList.size();i++){
            String[] arr = carList.get(i).split( ",",6);
            Car car = new Car();
            car.setId(arr[0]);
            car.setBrand(arr[1]);
            car.setModel(arr[2]);
            car.setNumPlate(arr[3]);
            car.setPrice(Float.parseFloat(arr[4]));
            car.setLocation(arr[5]);
            ls.add(car);
        }
        return ls;
    }
}
