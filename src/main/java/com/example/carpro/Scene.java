package com.example.carpro;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;

public class Scene {
    public static void switchScene(String fxml, StackPane stackPane){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation( Scene.class.getResource(fxml));

        try {
            StackPane newStakePane = fxmlLoader.load();
            stackPane.getChildren().clear();
            stackPane.getChildren().add(newStakePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getController(String fxml, StackPane stackPane){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation( Scene.class.getResource(fxml));

        try {
            StackPane newStakePane = fxmlLoader.load();
            stackPane.getChildren().clear();
            stackPane.getChildren().add(newStakePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getController();
    }

    public static void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            //setStyle("-fx-background-color: #c2c2c2;");
                        }else if (item.isAfter(maxDate)) {
                            setDisable(true);
                            //setStyle("-fx-background-color: #c2c2c2;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }
}
