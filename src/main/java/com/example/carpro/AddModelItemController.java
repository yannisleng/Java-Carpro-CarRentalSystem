package com.example.carpro;

import com.model.Brand;
import com.model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AddModelItemController implements Initializable {

    @FXML
    private Label Id;

    @FXML
    private Label name;

    public void setData(Model model){
        Id.setText(model.getId());
        name.setText(model.getModelName());
    }

    public void setData(Brand brand){
        Id.setText(brand.getId());
        name.setText(brand.getBrandName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
