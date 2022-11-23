package com.example.carpro;
import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addeditcar_Main_Controller implements Initializable {

    @FXML
    private VBox carlistLayout;

    @FXML
    private StackPane addeditcar_Main;

    @FXML
    private ComboBox<String> brandCmb;

    @FXML
    private ComboBox<String> modelCmb;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchBar;

    @FXML
    private Label modelLbl;

    @FXML
    private CheckBox allCheckBox;

    private final static int rowsPerPage = 8;

    protected List<Car> carList = new ArrayList<>(readcar());
    protected List <Model> modelList = new ArrayList<>(readModel());
    protected List <Brand> brandList = new ArrayList<>(readBrand());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.pagination != null){

            searchBar.setVisible(false);
            //add all item to combo box
            for(int i=0;i<brandList.size();i++){
                brandCmb.getItems().addAll(brandList.get(i).getBrandName());
            }

            for (int j=0;j<modelList.size();j++){
                modelCmb.getItems().addAll(modelList.get(j).getModelName());
            }

            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer pageIndex) {
                    carlistLayout.getChildren().clear();
                    return createPage(pageIndex,carList);
                }
            });

            setPaginationPageCount();
        }
    }

    protected List<Car> readcar(){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> carList = new ArrayList<>(db.getAllData());
        return carList;
    }

    protected List<Brand> readBrand(){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("brand");
        List<Brand> brandList = new ArrayList<>(db.getAllData());
        return brandList;
    }

    protected List<Model> readModel(){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("model");
        List<Model> modelList = new ArrayList<>(db.getAllData());
        return modelList;
    }

    private void setPaginationPageCount(){
        int pageCount = carList.size()/rowsPerPage;
        int remain = carList.size()%rowsPerPage;
        if(remain!=0){
            pageCount++;
        }

        pagination.setPageCount(pageCount);
    }

    private Node createPage(int pageIndex, List<Car> carList){
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex+ rowsPerPage,carList.size());

        for (int i=fromIndex; i< toIndex; i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addeditCar_item.fxml"));

            try{
                HBox hBox = fxmlLoader.load();
                addeditCar_item_Controller aic = fxmlLoader.getController();
                aic.setData(carList.get(i));
                carlistLayout.getChildren().add(hBox);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return carlistLayout;
    }

    private void setBrandCmb(){
        List<Model> models = new ArrayList<>();
        if(brandCmb.getValue() != null & !brandCmb.getValue().toString().isEmpty()){
            modelCmb.setDisable(false);

            //find the brand name in the brand list
            for(int i=0;i<brandList.size();i++){
                if(brandList.get(i).getBrandName().equals(brandCmb.getValue())){
                    models = brandList.get(i).getModels();
                }
            }
            //empty the combobox and add in the corresponding model into the empty combobox
            modelCmb.getItems().clear();

            //update model combo box and car list
            List<Car> carlist = new ArrayList<>();
            for (int j=0;j<models.size();j++) {
                modelCmb.getItems().addAll(models.get(j).getModelName());

                carlist.addAll(models.get(j).getCars());
                carlistLayout.getChildren().clear();
                createPage(pagination.getCurrentPageIndex(),carlist);
            }

            setPaginationPageCount();
        }
    }

    private void setModelCmb(){
        List<Car> cars = new ArrayList<>();
        if(modelCmb.getValue() != null){
            //find the model name in the model list
            for(int i=0;i<modelList.size();i++){
                if(modelList.get(i).getModelName().equals(modelCmb.getValue())){
                    cars = modelList.get(i).getCars();
                }
            }

            List<Car> carlist = new ArrayList<>();
            for (int j=0;j<cars.size();j++) {
                carlist.add(cars.get(j));
                carlistLayout.getChildren().clear();
                createPage(pagination.getCurrentPageIndex(),carlist);
            }
            setPaginationPageCount();
        }
    }

    @FXML
    private void handleComboBoxSelection(ActionEvent event) throws Exception{
        if(event.getSource() == brandCmb){
            setBrandCmb();
        }else if(event.getSource() == modelCmb){
            setModelCmb();
        }
    }

    @FXML
    public void switchtoCarInfo (ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addeditCar_info.fxml"));

        try{
            StackPane stackPane = fxmlLoader.load();
            addeditcar_Main.getChildren().clear();
            addeditcar_Main.getChildren().add(stackPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void handleSearchResult(){
        List<Car> cars = new ArrayList<>();

        //find the cars in the car list
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        cars = db.searchData(searchBar.getText());

        carlistLayout.getChildren().clear();
        createPage(pagination.getCurrentPageIndex(),cars);

        setPaginationPageCount();
    }

    @FXML
    public void searchBar (ActionEvent event) throws Exception{
        /*set search bar visible*/
        if(searchBar.isVisible()&&(searchBar.getText()==null || searchBar.getText().trim().isEmpty())){
            searchBar.setVisible(false);
            modelCmb.setVisible(true);
            modelLbl.setVisible(true);
            refreshThisScene();
        }else if(!searchBar.isVisible()){
            searchBar.setVisible(true);
            modelCmb.setVisible(false);
            modelLbl.setVisible(false);
        }else{
            handleSearchResult();
        }
    }

    private void refreshThisScene(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addeditCar_Main.fxml"));

        try{
            StackPane stackPane = fxmlLoader.load();
            addeditcar_Main.getChildren().clear();
            addeditcar_Main.getChildren().add(stackPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void tickAllCheckBox(){

    }

}
