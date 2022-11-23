package com.example.carpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TimePickerController implements Initializable {

    @FXML
    private VBox vboxHours;

    @FXML
    private VBox vboxMinutes;

    @FXML
    private VBox vboxTimePicker;

    @FXML
    private Label lblTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("timerPicker.fxml"));

        for(int i = 0; i <= 23; i++){
            addLabel(i, vboxHours);
        }

        for(int i = 0; i <= 59; i++){
            addLabel(i, vboxMinutes);
        }
    }

    private void addLabel(int i, VBox vbox){
        Label time = new Label();
        time.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-weight: bold; " +
                "-fx-pref-width: 45px; -fx-pref-height: 26px; -fx-alignment: center; -fx-font-size: 10;");
        time.setText(Integer.toString(i));
        if (time.getText().length() == 1){
            time.setText("0"+time.getText());
        }
        time.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            String constTime;
            if(vbox == vboxHours){
                constTime= lblTime.getText().substring(2,5);
                lblTime.setText(time.getText() + constTime + dayTime(Integer.parseInt(time.getText())));
            }else if(vbox == vboxMinutes){
                constTime= lblTime.getText().substring(0,3);
                lblTime.setText(constTime + time.getText() + dayTime(Integer.parseInt(lblTime.getText().substring(0,2))));
            }
        });

        time.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            time.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; " +
                    "-fx-pref-width: 45px; -fx-pref-height: 26px; -fx-alignment: center; -fx-font-size: 10;");
        });

        time.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            time.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-weight: bold; " +
                    "-fx-pref-width: 45px; -fx-pref-height: 26px; -fx-alignment: center; -fx-font-size: 10;");
        });

        vbox.getChildren().add(time);
    }

    private String dayTime(int i){
        String daytime;
        if(i < 12){
            daytime = " AM";
        }else{
            daytime = " PM";
        }
        return daytime;
    }

    @FXML
    private void showDropDown(MouseEvent event) {
        lblTime.setStyle(lblTime.getStyle() + "-fx-text-fill: #000000;");
        vboxTimePicker.setVisible(true);
        vboxTimePicker.setStyle("-fx-background-color: #000000;");
    }

    @FXML
    void hideDropDown(ActionEvent event) {
        vboxTimePicker.setVisible(false);
    }

    public String getTime(){
        return lblTime.getText();
    }
}
