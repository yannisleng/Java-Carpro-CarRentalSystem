package com.example.carpro;

import com.model.Car;
import com.model.Model;
import com.model.dataFactory;
import com.model.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addeditCar_Info_Controller extends addeditcar_Main_Controller implements Initializable{

    @FXML
    private StackPane addeditcar_info;

    @FXML
    private ComboBox<String> brandCmb;

    @FXML
    private ComboBox<String> modelCmb;

    @FXML
    private ComboBox<String> seatsCmb;

    @FXML
    private ComboBox<String> stateCmb;

    @FXML
    private TextField addressText;

    @FXML
    private TextField plateNumText;

    @FXML
    private TextField postCodeText;

    @FXML
    private TextField priceText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //add all item to combo box
        for(int i=0;i<brandList.size();i++){
            brandCmb.getItems().addAll(brandList.get(i).getBrandName());
        }

        for (int j=0;j<modelList.size();j++){
            modelCmb.getItems().addAll(modelList.get(j).getModelName());
        }

        ObservableList<String> seats = FXCollections.observableArrayList("5","7","8");
        seatsCmb.setItems(seats);

        ObservableList<String> state = FXCollections.observableArrayList("Johor","Kedah","Kelantan","Malacca","Negeri Sembilan","Pahang","Penang","Perak","Perlis","Sabah","Sarawak","Selangor","Terengganu");
        stateCmb.setItems(state);
    }

    @FXML
    private void switchtoBefore (ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addeditCar_Main.fxml"));

        try{
            StackPane stackPane = fxmlLoader.load();
            addeditcar_info.getChildren().clear();
            addeditcar_info.getChildren().add(stackPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void brandComboBoxSelection(ActionEvent event) throws Exception{
        List<Model> models = new ArrayList<>();
        if(brandCmb.getValue() != null & !brandCmb.getValue().toString().isEmpty()){
            modelCmb.setDisable(false);

            //find the brand name in the brand list
            for(int i=0;i<brandList.size();i++){
                if(brandList.get(i).getBrandName().equals(brandCmb.getValue())){
                    models = brandList.get(i).getModels();
                }
            }
            //empty the combobox and add in the corresponding model into the empty combobox
            modelCmb.getItems().clear();

            //update model combo box
            for (int j=0;j<models.size();j++) {
                modelCmb.getItems().addAll(models.get(j).getModelName());
            }
        }
    }

    @FXML
    private void saveData(ActionEvent event) throws Exception{
        Car car = new Car();
        ArrayList <String> arr = new ArrayList<>();

        for(int i=0; i< modelList.size();i++){
            if(carList.get(i).getBrand().equals(brandCmb.getValue()) & carList.get(i).getModel().equals(modelCmb.getValue())){
                arr.add(carList.get(i).getId());
            }
        }

        int id = Integer.parseInt(arr.get(arr.size()-1))+1;

        car.setId(String.format("%010d",id));
        car.setBrand(brandCmb.getValue());
        car.setModel(modelCmb.getValue());
        car.setSeat(Integer.parseInt(seatsCmb.getValue()));
        car.setPrice(Float.parseFloat(priceText.getText()));
        car.setNumPlate(plateNumText.getText());
        car.setAddress(addressText.getText());
        car.setPostCode(postCodeText.getText());
        car.setState(stateCmb.getValue());

        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        db.addData(car);
    }

}
