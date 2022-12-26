package com.example.carpro;

import com.model.User;
import com.model.dataFactory;
import com.model.database;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterUserController implements Initializable {
    @FXML
    private Label addressAlert;

    @FXML
    private Label addressHint;

    @FXML
    private TextField addressText;

    @FXML
    private Label datePickAlert;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label emailAlert;

    @FXML
    private Label emailHint;

    @FXML
    private TextField emailText;

    @FXML
    private Label firstNameAlert;

    @FXML
    private TextField firstNameText;

    @FXML
    private Label genderAlert;

    @FXML
    private ComboBox<String> genderCmb;

    @FXML
    private Label lastNameAlert;

    @FXML
    private TextField lastNameText;

    @FXML
    private Label pathLbl;

    @FXML
    private Label phoneNumAlert;

    @FXML
    private Label phoneNumHint;

    @FXML
    private TextField phoneNumText;

    @FXML
    private Label postCodeAlert;

    @FXML
    private Label postCodeHint;

    @FXML
    private TextField postCodeText;

    @FXML
    private Label stateAlert;

    @FXML
    private ComboBox<String> stateCmb;

    @FXML
    private Label successMsg;

    @FXML
    private ImageView userPicImgView;

    @FXML
    private ComboBox<String> userRoleCmb;

    @FXML
    private Label usernameAlert;

    @FXML
    private Label usernameHint;

    @FXML
    private Label userRoleHint;

    @FXML
    private TextField usernameText;

    private Image defaultImg = new Image("file:src/main/resources/com/example/carpro/img/profile_pic/default-image.png",212,212,true,true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> gender = FXCollections.observableArrayList("Male","Female","Rather not say");
        genderCmb.setItems(gender);

        ObservableList<String> userRole = FXCollections.observableArrayList("Admin","Customer");
        userRoleCmb.setItems(userRole);

        ObservableList<String> state = FXCollections.observableArrayList("Johor","Kedah","Kelantan","Kuala Lumpur","Melaka","Negeri Sembilan","Pahang","Penang","Perak","Perlis","Sabah","Sarawak","Selangor","Terengganu");
        stateCmb.setItems(state);

        datePicker.setValue(LocalDate.now().minusYears(18));
        Scene.restrictDatePicker(datePicker,LocalDate.now().minusYears(200),LocalDate.now().minusYears(18));
    }

    @FXML
    private void uploadPhoto(ActionEvent event) throws Exception{
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a profile picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(userRoleCmb.getScene().getWindow());
        if (file!=null){
            userPicImgView.setImage(new Image("file:"+file.getAbsolutePath(),212,212,true,true));
            pathLbl.setText(file.getAbsolutePath());
        }
    }

    private boolean validation(){
        usernameAlert.setVisible(false);
        usernameHint.setVisible(false);
        genderAlert.setVisible(false);
        firstNameAlert.setVisible(false);
        lastNameAlert.setVisible(false);
        phoneNumAlert.setVisible(false);
        phoneNumHint.setVisible(false);
        datePickAlert.setVisible(false);
        emailAlert.setVisible(false);
        emailHint.setVisible(false);
        addressAlert.setVisible(false);
        addressHint.setVisible(false);
        postCodeAlert.setVisible(false);
        postCodeHint.setVisible(false);
        stateAlert.setVisible(false);
        userRoleHint.setVisible(false);

        User user = new User();

        boolean usernameVal = user.usernameValidation(usernameText.getText());
        boolean phoneNumVal = user.commonValidation(phoneNumText.getText(),"phoneNum");
        boolean emailVal = user.commonValidation(emailText.getText(),"email");
        boolean addressVal = user.commonValidation(addressText.getText(),"address");
        boolean postCodeVal = user.commonValidation(postCodeText.getText(),"postCode");

        if(usernameVal && genderCmb.getValue()!=null && firstNameText.getText() != null && lastNameText.getText()!=null
        && phoneNumVal && datePicker.getValue() !=null && emailVal && addressVal && postCodeVal && stateCmb.getValue()!=null
        && userRoleCmb.getValue()!=null){
            return true;
        }else if(usernameText.getText() == null || usernameText.getText().isEmpty()|| !usernameVal){
            usernameAlert.setVisible(true);
            usernameHint.setVisible(true);
            return false;
        }else if(genderCmb.getValue()==null){
            genderAlert.setVisible(true);
            return false;
        }else if(firstNameText.getText()==null || firstNameText.getText().isEmpty()){
            firstNameAlert.setVisible(true);
            return false;
        }else if(lastNameText.getText()==null || lastNameText.getText().isEmpty()){
            lastNameAlert.setVisible(true);
            return false;
        }else if(!phoneNumVal){
            phoneNumAlert.setVisible(true);
            phoneNumHint.setVisible(true);
            return false;
        }else if(datePicker.getValue() == null){
            datePickAlert.setVisible(true);
            return false;
        }else if(!emailVal){
            emailAlert.setVisible(true);
            emailHint.setVisible(true);
            return false;
        }else if(!addressVal){
            addressAlert.setVisible(true);
            addressHint.setVisible(true);
            return false;
        }else if(!postCodeVal){
            postCodeAlert.setVisible(true);
            postCodeHint.setVisible(true);
            return false;
        }else if(stateCmb.getValue()==null){
            stateAlert.setVisible(true);
            return false;
        }else if(userRoleCmb.getValue()==null){
            userRoleHint.setVisible(true);
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
        usernameText.clear();
        firstNameText.clear();
        lastNameText.clear();
        phoneNumText.clear();
        emailText.clear();
        addressText.clear();
        postCodeText.clear();
        userPicImgView.setImage(defaultImg);
        pathLbl.setText("src/main/resources/com/example/carpro/img/profile_pic/default-image.png");
    }

    @FXML
    private void saveData(ActionEvent event) throws Exception{
        User user = new User();

        boolean validate = validation();
        if(validate) {

            //copy file to local storage
            String oriPath = pathLbl.getText();
            Path from = Paths.get(oriPath);
            String fileExtension = oriPath.substring(oriPath.lastIndexOf("."), pathLbl.getText().length());
            Path to = Paths.get("src/main/resources/com/example/carpro/img/profile_pic/" + usernameText.getText() + fileExtension);
            Files.copy(from, to);

            //generate password (date of birth)
            String pw = datePicker.getValue().toString().replaceAll("[-]", "").substring(2, 8);

            //set user data
            user.setUsername(usernameText.getText());
            user.setGender(genderCmb.getValue());
            user.setFirstName(firstNameText.getText());
            user.setLastName(lastNameText.getText());
            user.setPhoneNum(phoneNumText.getText());
            user.setDateOfBirth(datePicker.getValue().toString());
            user.setEmail(emailText.getText());
            user.setAddress(addressText.getText());
            user.setPostCode(postCodeText.getText());
            user.setState(stateCmb.getValue());
            user.setPassword(pw);
            user.setRole(userRoleCmb.getValue());
            user.setProfilePic(usernameText.getText() + fileExtension);

            dataFactory dataFactory = new dataFactory();
            database db = dataFactory.getDB("user");
            db.addData(user);

            successAction();
        }
    }
}
