package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AddModelItemController implements Initializable {

    @FXML
    private Label Id;

    @FXML
    private TextField name;

    @FXML
    private ImageView editImg;

    @FXML
    private Button deleteBtn;

    public Button getDeleteBtn(){return deleteBtn;}

    public String getId(){return Id.getText();}

    public String getName(){return name.getText();}

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
