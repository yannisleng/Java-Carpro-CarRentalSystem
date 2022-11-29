package com.example.carpro;

import com.model.Brand;
import com.model.Model;
import com.model.dataFactory;
import com.model.database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddEditBrandController implements Initializable {
    @FXML
    private VBox BrandListLayout;

    @FXML
    private ScrollPane BrandScrollPane;

    @FXML
    private TextField BrandText;

    @FXML
    private Label addBrandHint;

    @FXML
    private TextField searchBrandBar;

    @FXML
    private Button closeBtn;

    public Button getCloseBtn(){return closeBtn;}

    dataFactory dataFactory = new dataFactory();
    database brandDb = dataFactory.getDB("brand");
    List<Brand> brands = new ArrayList<>(brandDb.getAllData());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BrandScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        searchBrandBar.setVisible(false);
        displayBrand(brands);
    }

    private void refreshPane(){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("brand");
        displayBrand(db.getAllData());
    }

    private void deleteBrand(String Id,String name){
        if(validateDeleteBrand(Id)){
            Brand brand = new Brand();

            brand.setId(Id);
            brand.setBrandName(name);

            brandDb.deleteData(brand.toString().substring(0,brand.toString().length()-1));

        }else{
            deleteAlert("There exist models in this brand range, hence this record cannot be removed.");
        }
    }

    private Boolean validateDeleteBrand(String brand){
        //if there's model under the brand, then brand can't be deleted
        database db = dataFactory.getDB("model");
        List<Model> models = new ArrayList<>(db.getAllData());

        for (Model model:models){
            if (brand.equals(model.getId().substring(0,3))){
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

    private void displayBrand(List<Brand> brandList){
        BrandListLayout.getChildren().clear();
        for(int i=0;i<brandList.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addBrandModelItem.fxml"));
            try{
                HBox hBox = fxmlLoader.load();
                AddModelItemController amc = fxmlLoader.getController();
                amc.getDeleteBtn().setOnAction(event -> {
                    deleteBrand(amc.getId(),amc.getName());
                    refreshPane();
                });
                amc.setData(brandList.get(i));
                BrandListLayout.getChildren().add(hBox);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void saveBrand(ActionEvent event) throws Exception{
        addBrandHint.setVisible(false);
        boolean validate = true;

        if(!BrandText.getText().isEmpty() && BrandText.getText() != null){
            for(int i=0;i<brands.size();i++){
                if(brands.get(i).getBrandName().equals(BrandText.getText())){
                    validate = false;
                    addBrandHint.setVisible(true);
                }
            }
        }else{
            BrandText.setPromptText("Please enter brand name");
            validate = false;
        }

        if(validate){
            Brand brand = new Brand();

            //generate brand id
            String brandLastID = brands.get(brands.size()-1).getId();
            int intNewID = Integer.parseInt(brandLastID)+1;
            String newID = String.format("%03d",intNewID);

            brand.setId(newID);
            brand.setBrandName(BrandText.getText());

            brandDb.addData(brand);
            refreshPane();
        }

    }

    @FXML
    public void searchBrandBar (ActionEvent event) throws Exception{
        //set search bar visible
        if(searchBrandBar.isVisible()&&(searchBrandBar.getText()==null || searchBrandBar.getText().trim().isEmpty())){
            searchBrandBar.setVisible(false);
            displayBrand(brands);
        }else if(!searchBrandBar.isVisible()){
            searchBrandBar.setVisible(true);
        }else{
            List<Brand> searchBrand = brandDb.searchData(searchBrandBar.getText());
            displayBrand(searchBrand);
        }
    }
}
