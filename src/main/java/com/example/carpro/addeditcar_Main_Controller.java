package com.example.carpro;
import com.model.*;
import javafx.animation.FadeTransition;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class addeditcar_Main_Controller implements Initializable {

    @FXML
    private VBox carlistLayout;

    @FXML
    public StackPane addeditcar_Main;

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
    private CheckBox selectAllCheckBox;

    @FXML
    private Label nothingSelectedPrompt;

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

            setPagination(carList);
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

    private void setPaginationPageCount(List<Car> carList){
        int pageCount = carList.size()/rowsPerPage;
        int remain = carList.size()%rowsPerPage;
        if(remain!=0){
            pageCount++;
        }

        pagination.setPageCount(pageCount);
    }

    private void setPagination(List<Car> carList){
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                carlistLayout.getChildren().clear();
                return createPage(pageIndex,carList);
            }

        });
        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex());
        setPaginationPageCount(carList);
    }

    private Node createPage(int pageIndex, List<Car> carList){
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex+ rowsPerPage,carList.size());

        for (int i=fromIndex; i< toIndex; i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addeditCar_item.fxml"));

            try{
                HBox hBox = fxmlLoader.load();

                //add listener to each hBox
                hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("addeditCar_update.fxml"));

                        ObservableList<Node> children = hBox.getChildren();

                        //identify selected checkbox id
                        for (Node child:children) {
                            if(child instanceof CheckBox){
                                dataFactory dataFactory = new dataFactory();
                                database db = dataFactory.getDB("car");
                                List<Car> cars = new ArrayList<>(db.searchData(((CheckBox) child).getText()));

                                try {
                                    StackPane stackPane = fxmlLoader.load();
                                    addeditCar_Update_Controller auc = fxmlLoader.getController();
                                    auc.setData(cars.get(0));
                                    addeditcar_Main.getChildren().clear();
                                    addeditcar_Main.getChildren().add(stackPane);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
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

            }
            setPagination(carlist);
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

            }
            setPagination(carlist);
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
        Scene.switchScene("addeditCar_info.fxml",addeditcar_Main);
    }

    private void handleSearchResult(){
        List<Car> cars = new ArrayList<>();

        //find the cars in the car list
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        cars = db.searchData(searchBar.getText());

        carlistLayout.getChildren().clear();
        createPage(pagination.getCurrentPageIndex(),cars);

        setPaginationPageCount(cars);
    }

    @FXML
    public void searchBar (ActionEvent event) throws Exception{
        /*set search bar visible*/
        if(searchBar.isVisible()&&(searchBar.getText()==null || searchBar.getText().trim().isEmpty())){
            searchBar.setVisible(false);
            modelCmb.setVisible(true);
            modelLbl.setVisible(true);
            Scene.switchScene("addeditCar_Main.fxml",addeditcar_Main);
        }else if(!searchBar.isVisible()){
            searchBar.setVisible(true);
            modelCmb.setVisible(false);
            modelLbl.setVisible(false);
        }else{
            handleSearchResult();
        }
    }

    @FXML
    private void selectAll(ActionEvent event) throws Exception{
        ObservableList<Node> children = carlistLayout.getChildren();
        for (Node child:children) {
            if(child instanceof HBox){
                for(Node checkbox:((HBox)child).getChildren()){
                    if(checkbox instanceof CheckBox){
                        if(selectAllCheckBox.isSelected()){
                            ((CheckBox) checkbox).setSelected(true);
                        }else {
                            ((CheckBox) checkbox).setSelected(false);
                        }

                    }
                }

            }
        }
    }

    @FXML
    private void deleteRow(ActionEvent event) throws Exception{
        ObservableList<Node> children = carlistLayout.getChildren();
        ArrayList<String> arrCarId = new ArrayList<>();

        //identify selected checkbox id
        for (Node child:children) {
            if(child instanceof HBox){
                for(Node checkbox:((HBox)child).getChildren()){
                    if(checkbox instanceof CheckBox){
                        if(((CheckBox) checkbox).isSelected()){
                            arrCarId.add(((CheckBox) checkbox).getText());
                        }

                    }
                }

            }
        }
        if(arrCarId.size() != 0){
            if(validateDelete(arrCarId)){
                deleteAlert(arrCarId);
                Scene.switchScene("addeditCar_Main.fxml",addeditcar_Main);
            }else{
                deletePrompt("Car currently unavailable");
            }
        }else{
            deletePrompt("Nothing been selected");
        }
    }

    //if car currently been booked,can't delete
    public boolean validateDelete(ArrayList<String> arrCarId){
        for(int i=0;i< carList.size();i++){
            for(int j=0;j<arrCarId.size();j++){
                if(carList.get(i).getStatus().equals("Available")){
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteCarinFile(ArrayList<String> arrCarId){
        Car car = new Car();
        for(int i=0;i< carList.size();i++){
            for(int j=0;j<arrCarId.size();j++){
                if(carList.get(i).getId().equals(arrCarId.get(j))){
                    car.setId(carList.get(i).getId());
                    car.setBrand(carList.get(i).getBrand());
                    car.setModel(carList.get(i).getModel());
                    car.setNumPlate(carList.get(i).getNumPlate());
                    car.setPrice(carList.get(i).getPrice());
                    car.setSeat(carList.get(i).getSeat());
                    car.setFuel(carList.get(i).getFuel());
                    car.setStatus(carList.get(i).getStatus());
                    car.setAddress(carList.get(i).getAddress());
                    car.setPostCode(carList.get(i).getPostCode());
                    car.setState(carList.get(i).getState());
                    car.setImgsrc(carList.get(i).getImgsrc());

                    dataFactory dataFactory = new dataFactory();
                    database db = dataFactory.getDB("car");
                    db.deleteData(car.toString().substring(0,car.toString().length()-1));
                }
            }
        }
    }

    public void deleteAlert(ArrayList<String> arrCarId){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Do you really want to delete these records? " +
                "This process cannot be undone.");

        if(alert.showAndWait().get() == ButtonType.OK){
            deleteCarinFile(arrCarId);
        }
    }

    public void deletePrompt(String promptText){
        nothingSelectedPrompt.setText(promptText);
        nothingSelectedPrompt.setOpacity(1);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), nothingSelectedPrompt);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
    }

}
