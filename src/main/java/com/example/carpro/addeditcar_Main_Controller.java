package com.example.carpro;
import com.model.database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.model.Car;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addeditcar_Main_Controller implements Initializable {

    @FXML
    private VBox carlistLayout;

    @FXML
    private Button addCar;

    @FXML
    private StackPane addeditcar_Main;

    @FXML
    private Pagination pagination;
    private final static int rowsPerPage = 8;

    List<Car> car = new ArrayList<>(readcar());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.pagination != null){
            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer pageIndex) {
                    carlistLayout.getChildren().clear();
                    return createPage(pageIndex);
                }
            });

            int pageCount = car.size()/rowsPerPage;
            int remain = car.size()%rowsPerPage;
            if(remain!=0){
                pageCount++;
            }

            pagination.setPageCount(pageCount);
        }

    }

    private List<Car> readcar(){
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

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex+ rowsPerPage,car.size());

        for (int i=fromIndex; i<= toIndex; i++){
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
        return carlistLayout;
    }

    @FXML
    private void switchtoNext (ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addeditCar_info.fxml"));

        try{
            StackPane stackPane = fxmlLoader.load();
            addeditcar_Main.getChildren().clear();
            addeditcar_Main.getChildren().add(stackPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
