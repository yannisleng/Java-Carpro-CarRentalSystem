package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddModelItemController implements Initializable {

    @FXML
    private Label Id;

    @FXML
    private TextField name;

    @FXML
    private ImageView editImg;

    @FXML
    private HBox hBox;

    public void setData(Model model){
        Id.setText(model.getId());
        name.setText(model.getModelName());
    }

    public void setData(Brand brand){
        Id.setText(brand.getId());
        name.setText(brand.getBrandName());
    }

    @FXML
    private void editname(ActionEvent event){
        if(!name.isEditable()){
            name.setEditable(true);
            name.setStyle("-fx-border-color: #4E5969;-fx-border-width: 0px 0px 1px 0px;-fx-background-color: transparent, white, transparent, white;");
            editImg.setImage(new Image("file:src/main/resources/com/example/carpro/img/check.png"));

        }else if(name.isEditable()){
            if(!name.getText().isEmpty()){
                name.setEditable(false);
                name.setStyle("-fx-background-color: transparent;");
                editImg.setImage(new Image("file:src/main/resources/com/example/carpro/img/pen.png"));

                //brand
                if(Id.getText().length()==3){
                    Brand brand = new Brand();
                    brand.setId(Id.getText());
                    brand.setBrandName(name.getText());

                    dataFactory dataFactory = new dataFactory();
                    database db = dataFactory.getDB("brand");
                    db.updateData(brand);

                }//model
                else if(Id.getText().length()==6){
                    Model model = new Model();
                    model.setId(Id.getText());
                    model.setModelName(name.getText());

                    dataFactory dataFactory = new dataFactory();
                    database db = dataFactory.getDB("model");
                    db.updateData(model);
                }
            }else{
               name.setPromptText("Please enter name");
            }

        }

    }

    @FXML
    private void deleteItem(ActionEvent event){
        String id = Id.getText();
        if(Id.getText().length()==3){
            if(validateDeleteBrand(id)){
                Brand brand = new Brand();

                brand.setId(Id.getText());
                brand.setBrandName(name.getText());

                dataFactory dataFactory = new dataFactory();
                database db = dataFactory.getDB("brand");
                db.deleteData(brand.toString().substring(0,brand.toString().length()-1));

            }else{
                deleteAlert("There exist models in this brand range, hence this record cannot be removed.");
            }
        }else if(Id.getText().length()==6){
            if(validateDeleteModel(id)){
                Model model = new Model();

                model.setId(Id.getText());
                model.setModelName(name.getText());

                dataFactory dataFactory = new dataFactory();
                database db = dataFactory.getDB("model");
                db.deleteData(model.toString().substring(0,model.toString().length()-1));
            }else{
                deleteAlert("There exist vehicles in this model range, hence this record cannot be removed.");
            }
        }
    }

    private Boolean validateDeleteBrand(String brand){
        //if there's model under the brand, then brand can't be deleted
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("model");
        List<Model> models = new ArrayList<>(db.getAllData());

        for (Model model:models){
            if (brand.equals(model.getId().substring(0,3))){
                return false;
            }
        }
        return true;
    }

    private Boolean validateDeleteModel(String model){
        //if there's car under the model, then model can't be deleted
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> cars = new ArrayList<>(db.getAllData());

        for (Car car:cars){
            if (model.equals(car.getId().substring(0,6))){
                return false;
            }
        }
        return true;
    }

    private void deleteAlert(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Refuse");
        alert.setContentText(content);
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
