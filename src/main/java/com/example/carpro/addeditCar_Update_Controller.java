package com.example.carpro;

import com.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addeditCar_Update_Controller extends addeditCar_Info_Controller implements Initializable {

    @FXML
    private TextField addressText;

    @FXML
    private ComboBox<String> brandCmb;

    @FXML
    private ImageView carPicimgView;

    @FXML
    private ComboBox<String> modelCmb;

    @FXML
    private Label pathLbl;

    @FXML
    private TextField plateNumText;

    @FXML
    private TextField postCodeText;

    @FXML
    private TextField priceText;

    @FXML
    private ComboBox<String> seatsCmb;

    @FXML
    private ComboBox<String> stateCmb;

    @FXML
    private StackPane addeditcar_update;

    private String imgPath = "file:src/main/resources/com/example/carpro/img/car/";
    private String ImgPath = "src/main/resources/com/example/carpro/img/car/";

    private Car oriCar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i=0;i<brandList.size();i++){
            brandCmb.getItems().addAll(brandList.get(i).getBrandName());
        }

        ObservableList<String> seats = FXCollections.observableArrayList("5","7","8");
        seatsCmb.setItems(seats);

        ObservableList<String> state = FXCollections.observableArrayList("Johor","Kedah","Kelantan","Kuala Lumpur","Melaka","Negeri Sembilan","Pahang","Penang","Perak","Perlis","Sabah","Sarawak","Selangor","Terengganu");
        stateCmb.setItems(state);
    }

    public void setData(Car car){
        oriCar = car;
        brandCmb.getSelectionModel().select(car.getBrand());
        modelCmb.getSelectionModel().select(car.getModel());
        seatsCmb.getSelectionModel().select(String.valueOf(car.getSeat()));
        priceText.setText(String.format("%.02f",car.getPrice()));
        plateNumText.setText(car.getNumPlate());
        addressText.setText(car.getAddress());
        postCodeText.setText(car.getPostCode());
        stateCmb.getSelectionModel().select(car.getState());
        carPicimgView.setImage(new Image(imgPath+car.getImgsrc()));
        pathLbl.setText(ImgPath+car.getImgsrc());
        setModelCmb(car);
    }

    private void setModelCmb(Car car){
        List<Model> models = new ArrayList<>();

        //find the brand name in the brand list
        for (Brand brand : brandList){
            if(car.getBrand().equals(brand.getBrandName())){
                models = brand.getModels();
            }
        }

        //update model combo box
        for (Model model:models) {
            modelCmb.getItems().addAll(model.getModelName());
        }
    }

    @FXML
    private void brandCmbSelected(ActionEvent event)throws Exception{
        brandComboBoxSelect();
    }

    @FXML
    private void switchtoMain(ActionEvent event)throws Exception{
        Scene.switchScene("addeditCar_Main.fxml",addeditcar_update);
    }

    @FXML
    private void setUploadPhoBtn(ActionEvent event)throws Exception{
        uploadPic(addeditcar_update);
    }

    @FXML
    private void updateData(ActionEvent event) throws Exception{
        Car car = new Car();
        if(validation()){
            //copy file to local storage
            String oriPath = pathLbl.getText();
            Path from = Paths.get(oriPath);
            String fileExtension = oriPath.substring(oriPath.lastIndexOf("."), pathLbl.getText().length());
            Path to = Paths.get(ImgPath+oriCar.getId()+fileExtension);
            Files.copy(from,to, StandardCopyOption.REPLACE_EXISTING);

            //set car data
            car.setId(oriCar.getId());
            car.setBrand(brandCmb.getValue());
            car.setModel(modelCmb.getValue());
            car.setSeat(Integer.parseInt(seatsCmb.getValue()));
            car.setPrice(Float.parseFloat(priceText.getText()));
            car.setNumPlate(plateNumText.getText().toUpperCase());
            car.setAddress(addressText.getText());
            car.setPostCode(postCodeText.getText());
            car.setState(stateCmb.getValue());
            car.setImgsrc(oriCar.getId()+fileExtension);

            dataFactory dataFactory = new dataFactory();
            database db = dataFactory.getDB("car");
            db.updateData(car);

            successAction();
        }
    }
}
