module com.example.carpro {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.carpro to javafx.fxml;
    exports com.example.carpro;
}