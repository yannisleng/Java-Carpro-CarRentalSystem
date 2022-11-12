package com.model;

import java.util.List;

public class Model {
    private String id;
    private String modelName;
    private String imgsrc;
    private List<Car> cars;

    Model(String id, String modelName,String imgsrc, List<Car> cars){
        this.id = id;
        this.modelName = modelName;
        this.imgsrc = imgsrc;
        this.cars = cars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getImgsrc() {return imgsrc;}

    public void setImgsrc(String imgsrc) {this.imgsrc = imgsrc;}

    public List<Car> getCars(){
        return cars;
    }

    public void setCars(List<Car> cars) {this.cars = cars;}

}
