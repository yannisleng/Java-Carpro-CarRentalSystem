package com.example.carpro;

import com.model.User;
import com.model.dataFactory;
import com.model.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CusProfileController implements Initializable {

    private User customer = LoginController.loginUser;

    public User getCustomer() {
        return customer;
    }

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdateProfile;

    @FXML
    private ComboBox<String> cmbState;

    @FXML
    private ImageView imgProfile;

    @FXML
    private Label lblName;

    @FXML
    private Label lblUsername;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDOB;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtPSW;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private TextField txtPosCode;

    private FileChooser fileChooser;
    private File file;

    private dataFactory dataFactory = new dataFactory();
    private database db = dataFactory.getDB("user");
    private List<User> customers = new ArrayList<>(db.getAllData());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> states = FXCollections.observableArrayList("Johor","Kedah","Kelantan","Kuala Lumpur",
                "Melaka","Negeri Sembilan","Pahang","Penang","Perak","Perlis","Sabah","Sarawak","Selangor","Terengganu");
        cmbState.setItems(states);

        showUserInformation();

        btnUpdateProfile.setStyle("-fx-opacity: 1.0; -fx-background-color: transparent; -fx-border-color: transparent;");
    }

    //update profile image
    public void updateProfileImg(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        fileChooser = new FileChooser();
        fileChooser.setTitle("New Profile Picture");

        //set default path to profile_pic/
        File userDirectory = new File("src/main/resources/com/example/carpro/img/profile_pic/");
        fileChooser.setInitialDirectory(userDirectory);

        this.file = fileChooser.showOpenDialog(stage);

        if(file!=null){
            Image image = new Image("file:"+file.getPath());
            imgProfile.setImage(image);
            customer.setProfilePic(file.getName());
        }
    }

    @FXML
    private void editUserInformation(ActionEvent event) {
        initializeTxt(true, Cursor.TEXT);
        initializeCmb(false, Cursor.HAND, "#c2c2c2");
        initializeBtnEdit(false, true);
        initializeBtnSave(true, false);
        btnUpdateProfile.setDisable(false);
        cmbState.setDisable(false);
    }

    @FXML
    private void saveUserInformation(ActionEvent event){
        customer.setPhoneNum(txtPhoneNo.getText());
        customer.setEmail(txtEmail.getText());
        customer.setPassword(txtPSW.getText());
        customer.setAddress(txtAddress.getText());
        customer.setPostCode(txtPosCode.getText());
        customer.setState(cmbState.getValue());

        boolean phoneVal = customer.commonValidation(customer.getPhoneNum(), "phoneNum");
        boolean emailVal = customer.commonValidation(customer.getEmail(), "email");
        boolean pswVal = customer.getPassword().length() >= 6;
        boolean addressVal = customer.commonValidation(customer.getAddress(), "address");
        boolean posCodeVale = customer.commonValidation(customer.getPostCode(), "postCode");

        if (phoneVal && pswVal && emailVal && addressVal && posCodeVale){
            for(int i = 0; i < customers.size(); i++){
                if(customer.getUsername().equals(customers.get(i).getUsername())){
                    customers.set(i,customer);
                    try {
                        FileWriter file = new FileWriter("src/main/resources/com/example/carpro/database/user.txt");
                        for(User user: customers){
                            file.write(String.valueOf(user));
                        }
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            System.out.println("Invalid input");
        }

        initializeTxt(false, Cursor.DEFAULT);
        initializeCmb(true, Cursor.DEFAULT, "transparent");
        initializeBtnEdit(true, false);
        initializeBtnSave(false, true);
        btnUpdateProfile.setDisable(true);
        cmbState.setDisable(true);
    }

    private void showUserInformation(){
        lblName.setText(customer.getFirstName() + " " + customer.getLastName());
        lblUsername.setText(customer.getUsername());
        imgProfile.setImage(new Image("file:src/main/resources/com/example/carpro/img/profile_pic/" + customer.getProfilePic()));
        txtPhoneNo.setText(customer.getPhoneNum());
        txtGender.setText(customer.getGender());
        txtEmail.setText(customer.getEmail());
        txtDOB.setText(customer.getDateOfBirth());
        txtPSW.setText(customer.getPassword());
        txtAddress.setText(customer.getAddress());
        txtPosCode.setText(customer.getPostCode());
        cmbState.setPromptText(customer.getState());
        cmbState.setValue(customer.getState());
    }

    private void initializeTxt(boolean editable, Cursor cursor){
        txtPhoneNo.setEditable(editable);
        txtPhoneNo.setCursor(cursor);
        txtEmail.setEditable(editable);
        txtEmail.setCursor(cursor);
        txtPSW.setEditable(editable);
        txtPSW.setCursor(cursor);
        txtAddress.setEditable(editable);
        txtAddress.setCursor(cursor);
        txtPosCode.setEditable(editable);
        txtPosCode.setCursor(cursor);
    }

    private void initializeCmb(boolean disable, Cursor cursor, String color){
        cmbState.setDisable(disable);
        cmbState.setCursor(cursor);
        cmbState.lookup(".arrow").setStyle("-fx-background-color: " + color);
    }

    private void initializeBtnEdit(boolean visible, boolean disable){
        btnEdit.setVisible(visible);
        btnEdit.setDisable(disable);
    }

    private void initializeBtnSave(boolean visible, boolean disable){
        btnSave.setVisible(visible);
        btnSave.setDisable(disable);
    }
}
