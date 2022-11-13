package com.example.carpro;

import com.model.Car;
import com.model.Model;
import com.model.dataFactory;
import com.model.database;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @FXML
    private Label brandAlert;

    @FXML
    private Label modelAlert;

    @FXML
    private Label plateNumAlert;

    @FXML
    private Label seatsAlert;

    @FXML
    private Label priceAlert;

    @FXML
    private Label addressAlert;

    @FXML
    private Label postCodeAlert;

    @FXML
    private Label stateAlert;

    @FXML
    private Label priceHint;

    @FXML
    private Label plateNumHint;

    @FXML
    private Label addressHint;

    @FXML
    private Label postCodeHint;

    @FXML
    private ImageView carPicimgView;

    @FXML
    private Label pathLbl;

    @FXML
    private Label successMsg;

    private Image defaulImg = new Image("file:src/main/resources/com/example/carpro/img/car/default-image.png",212,212,true,true);


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

        ObservableList<String> state = FXCollections.observableArrayList("Johor","Kedah","Kelantan","Kuala Lumpur","Melaka","Negeri Sembilan","Pahang","Penang","Perak","Perlis","Sabah","Sarawak","Selangor","Terengganu");
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
    private void uploadPhoto(ActionEvent event) throws Exception{
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a car picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(addeditcar_info.getScene().getWindow());
        if (file!=null){
            carPicimgView.setImage(new Image("file:"+file.getAbsolutePath(),212,212,true,true));
            pathLbl.setText(file.getAbsolutePath());
        }
    }

    private boolean validation() {
        priceHint.setVisible(false);
        plateNumHint.setVisible(false);
        addressHint.setVisible(false);
        postCodeHint.setVisible(false);
        brandAlert.setVisible(false);
        modelAlert.setVisible(false);
        seatsAlert.setVisible(false);
        priceAlert.setVisible(false);
        plateNumAlert.setVisible(false);
        addressAlert.setVisible(false);
        postCodeAlert.setVisible(false);
        stateAlert.setVisible(false);

        Car car = new Car();

        boolean priceVal = car.priceValidation(priceText.getText());
        boolean plateNumVal = car.plateNumValidation(plateNumText.getText());
        boolean addressVal = car.addressValidation(addressText.getText());
        boolean postCodeVal = car.postCodeValidation(postCodeText.getText());

        if (brandCmb.getValue() != null && modelCmb.getValue() != null && seatsCmb.getValue() != null && stateCmb.getValue() != null && priceVal && plateNumVal && addressVal && postCodeVal) {
            return true;
        }else if(brandCmb.getValue() == null){
            brandAlert.setVisible(true);
            return false;
        }else if(modelCmb.getValue()==null){
            modelAlert.setVisible(true);
            return false;
        }else if(seatsCmb.getValue()==null){
            seatsAlert.setVisible(true);
            return false;
        }else if(priceText.getText() == null || !priceVal){
            priceAlert.setVisible(true);
            priceHint.setVisible(true);
            return false;
        }else if(plateNumText.getText() == null || !plateNumVal){
            plateNumAlert.setVisible(true);
            plateNumHint.setVisible(true);
            return false;
        }else if(addressText.getText()==null || !addressVal){
            addressAlert.setVisible(true);
            addressHint.setVisible(true);
            return false;
        }else if(postCodeText.getText()==null || !postCodeVal){
            postCodeAlert.setVisible(true);
            postCodeHint.setVisible(true);
            return false;
        }else if(stateCmb.getValue()==null){
            stateAlert.setVisible(true);
            return false;
        }else{
            return false;
        }

    }

    private void successAction(){
        //prompt updated success message
        successMsg.setOpacity(1);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), successMsg);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();

        //initial the scene
        priceText.clear();
        plateNumText.clear();
        addressText.clear();
        postCodeText.clear();
        carPicimgView.setImage(defaulImg);
        pathLbl.setText("src/main/resources/com/example/carpro/img/car/default-image.png");
    }

    @FXML
    private void saveData(ActionEvent event) throws Exception{
        Car car = new Car();
        ArrayList <String> arr = new ArrayList<>();

        boolean validate = validation();
        if(validate){
            for(int i=0; i< carList.size();i++){

                if(carList.get(i).getBrand().equals(brandCmb.getValue()) & carList.get(i).getModel().equals(modelCmb.getValue())){
                    arr.add(carList.get(i).getId());
                }
            }
            int intId = Integer.parseInt(arr.get(arr.size()-1))+1;
            String id = String.format("%010d",intId);

            String oriPath = pathLbl.getText();
            Path from = Paths.get(oriPath);
            String fileExtension = oriPath.substring(oriPath.lastIndexOf("."), pathLbl.getText().length());
            Path to = Paths.get("src/main/resources/com/example/carpro/img/car/"+id+fileExtension);
            Files.copy(from,to);

            car.setId(id);
            car.setBrand(brandCmb.getValue());
            car.setModel(modelCmb.getValue());
            car.setSeat(Integer.parseInt(seatsCmb.getValue()));
            car.setPrice(Float.parseFloat(priceText.getText()));
            car.setNumPlate(plateNumText.getText().toUpperCase());
            car.setAddress(addressText.getText());
            car.setPostCode(postCodeText.getText());
            car.setState(stateCmb.getValue());
            car.setImgsrc(id+fileExtension);

            dataFactory dataFactory = new dataFactory();
            database db = dataFactory.getDB("car");
            db.addData(car);

            successAction();
        }
    }

}
