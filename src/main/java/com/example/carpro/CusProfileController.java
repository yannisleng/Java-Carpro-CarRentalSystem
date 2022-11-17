package com.example.carpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CusProfileController implements Initializable {

    @FXML
    private Button btnUpdate;

    @FXML
    private ImageView imgProfile;

    @FXML
    private StackPane spCusProfile;

    private FileChooser fileChooser;
    private File file;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("cusProfile.fxml"));
    }

    //update profile image
    public void updateProfileImg(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        fileChooser = new FileChooser();
        fileChooser.setTitle("New Profile Picture");

        //set default path to img/
        File userDirectory = new File("src/main/resources/com/example/carpro/img/");
        fileChooser.setInitialDirectory(userDirectory);

        this.file = fileChooser.showOpenDialog(stage);

        if(file!=null){
            Image image = new Image("file:"+file.getPath());
            imgProfile.setImage(image);
        }
    }
}
