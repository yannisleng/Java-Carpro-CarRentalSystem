package com.example.carpro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NoResultController{

    @FXML
    private Label lblNotMatch;

    @FXML
    private Label lblResult;

    public void setText(String searchTxt){
        lblResult.setText("0 result of ‘" + searchTxt + "’");
        lblNotMatch.setText("Your search ‘" + searchTxt + "’ did not match any cars");
    }

}
