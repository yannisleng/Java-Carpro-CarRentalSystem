package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddEditModelController implements Initializable {

    @FXML
    private VBox ModelListLayout;

    @FXML
    private ScrollPane ModelScrollPane;

    @FXML
    private TextField ModelText;

    @FXML
    private ComboBox<String> addModelBrandCmb;

    @FXML
    private Label addModelHint;

    @FXML
    private TextField searchModelBar;

    @FXML
    private Label selectBrandHint;

    @FXML
    private Button closeBtn;

    public Button getCloseBtn(){return closeBtn;}

    dataFactory dataFactory = new dataFactory();

    database modelDb = dataFactory.getDB("model");
    database brandDb = dataFactory.getDB("brand");

    List<Model> models = new ArrayList<>(modelDb.getAllData());
    List<Brand> brands = new ArrayList<>(brandDb.getAllData());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModelScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        searchModelBar.setVisible(false);

        for(Brand brand:brands){
            addModelBrandCmb.getItems().addAll(brand.getBrandName());
        }
        displayModel(models);
    }

    private void refreshPane(){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("model");
        displayModel(db.getAllData());
    }

    private void deleteModel(String Id,String name){
        if(validateDeleteModel(Id)){
            Model model = new Model();

            model.setId(Id);
            model.setModelName(name);

            modelDb.deleteData(model.toString().substring(0,model.toString().length()-1));
        }else{
            deleteAlert("There exist vehicles in this model range, hence this record cannot be removed.");
        }
    }

    private Boolean validateDeleteModel(String modelId){
        //if there's car under the model, then model can't be deleted
        database db = dataFactory.getDB("car");
        List<Car> cars = new ArrayList<>(db.getAllData());

        for (Car car:cars){
            if (modelId.equals(car.getId().substring(0,6))){
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

    private void displayModel(List<Model> modelList){
        ModelListLayout.getChildren().clear();
        for(int i=0;i<modelList.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addBrandModelItem.fxml"));
            try{
                HBox hBox = fxmlLoader.load();
                AddModelItemController amc = fxmlLoader.getController();
                amc.getDeleteBtn().setOnAction(event -> {
                    deleteModel(amc.getId(),amc.getName());
                    refreshPane();
                });
                amc.setData(modelList.get(i));
                ModelListLayout.getChildren().add(hBox);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private Boolean saveModelValidation(){
        if(!ModelText.getText().isEmpty() && ModelText.getText() != null && addModelBrandCmb.getValue() !=null){
            for(int i=0;i<models.size();i++){
                if(models.get(i).getModelName().equals(ModelText.getText())){
                    addModelHint.setVisible(true);
                    return false;
                }
            }
        }else if(addModelBrandCmb.getValue() == null){
            selectBrandHint.setVisible(true);
            return false;
        }else{
            addModelHint.setVisible(true);
            selectBrandHint.setVisible(true);
            ModelText.setPromptText("Please enter model name");
            return false;
        }
        return true;
    }

    private void generateIdUpdateData(ArrayList<String> arr){
        Model model = new Model();
        int intId = Integer.parseInt(arr.get(arr.size()-1))+1;
        String id = String.format("%06d",intId);
        model.setId(id);
        model.setModelName(ModelText.getText());

        modelDb.addData(model);
        refreshPane();
    }

    @FXML
    private void saveModel(ActionEvent event) throws Exception{
        addModelHint.setVisible(false);
        selectBrandHint.setVisible(false);

        Boolean validate = saveModelValidation();

        ArrayList <String> arr = new ArrayList<>();

        dataFactory dataFactory = new dataFactory();
        database modelDb = dataFactory.getDB("model");
        List<Model> models = new ArrayList<>(modelDb.getAllData());

        if(validate){
            for(Brand brand:brands){
                if(brand.getBrandName().equals(addModelBrandCmb.getValue())){
                    String brandId = brand.getId();

                    for(Model model : models){
                        if(model.getId().substring(0,3).equals(brandId)){
                            arr.add(model.getId());
                        }
                    }
                    if(arr.size()==0){
                        arr.add(brandId+"000");
                    }
                }
            }

            generateIdUpdateData(arr);
        }

    }

    @FXML
    public void searchModelBar (ActionEvent event) throws Exception{
        /*set search bar visible*/
        if(searchModelBar.isVisible()&&(searchModelBar.getText()==null || searchModelBar.getText().trim().isEmpty())){
            searchModelBar.setVisible(false);
            displayModel(models);
        }else if(!searchModelBar.isVisible()){
            searchModelBar.setVisible(true);
        }else{
            List<Model> searchmodel= modelDb.searchData(searchModelBar.getText());
            displayModel(searchmodel);
        }
    }
}
