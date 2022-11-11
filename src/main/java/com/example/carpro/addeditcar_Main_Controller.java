package com.example.carpro;
import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private StackPane addeditcar_Main;

    @FXML
    private ComboBox<String> brandCmb;

    @FXML
    private ComboBox<String> modelCmb;

    @FXML
    private Pagination pagination;

    private final static int rowsPerPage = 8;

    List<Car> car = new ArrayList<>(readcar());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.pagination != null){
            List <Model> modelList = new ArrayList<>(readModel());
            List <Brand> brandList = new ArrayList<>(readBrand());

            //add item to combo box
            for(int i=0;i<brandList.size();i++){
                brandCmb.getItems().addAll(brandList.get(i).getBrandName());
            }

            for (int j=0;j<modelList.size();j++){
                modelCmb.getItems().addAll(modelList.get(j).getModelName());
            }

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
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> carList = new ArrayList<>(db.getAllData());
        return carList;
    }

    private List<Brand> readBrand(){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("brand");
        List<Brand> brandList = new ArrayList<>(db.getAllData());
        return brandList;
    }

    private List<Model> readModel(){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("model");
        List<Model> modelList = new ArrayList<>(db.getAllData());
        return modelList;
    }

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex+ rowsPerPage,car.size());

        for (int i=fromIndex; i< toIndex; i++){
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
