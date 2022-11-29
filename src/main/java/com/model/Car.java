package com.model;

import javafx.scene.control.CheckBox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Car {

    private String id;
    private String brand;
    private String model;
    private String numPlate;
    private float price;
    private int seat;
    private int fuel;
    private String status;
    private String address;
    private String postCode;
    private String state;
    private String imgsrc;

    public Car(){
        fuel = 100;
        status = "Available";
        imgsrc = "default-image.png";
    }

    public Car(String id, String brand, String model, String numPlate, float price, int seat, int fuel, String status,
               String address, String postCode, String state, String imgsrc) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.numPlate = numPlate;
        this.price = price;
        this.seat = seat;
        this.fuel = fuel;
        this.status = status;
        this.address = address;
        this.postCode = postCode;
        this.state = state;
        this.imgsrc = imgsrc;
    }

    public Car(String id, String brand, String model, String numPlate, float price, int seat, String address, String postCode, String state){
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.numPlate = numPlate;
        this.price = price;
        this.seat = seat;
        this.address = address;
        this.postCode = postCode;
        this.state = state;
        fuel = 100;
        status = "Available";
        imgsrc = "default-image.png";
    }

    @Override
    public String toString() {
        return id+"`"+brand+"`"+model+"`"+numPlate+"`"+String.format("%.02f",price)+"`"+seat+"`"+fuel+"`"+status+"`"+address+"`"+postCode+"`"+state+"`"+imgsrc+"\n";
    }

    public String getBrand() {return brand;}

    public void setBrand(String brand) {this.brand = brand;}

    public String getModel() {return model;}

    public void setModel(String model) {this.model = model;}

    public int getFuel() {return fuel;}

    public void setFuel(int fuel) {this.fuel = fuel;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getPostCode() {return postCode;}

    public void setPostCode(String postCode) {this.postCode = postCode;}

    public String getState() {return state;}

    public void setState(String state) { this.state = state;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumPlate() {
        return numPlate;
    }

    public void setNumPlate(String numPlate) {
        this.numPlate = numPlate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {this.price = price;}

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getImgsrc() {return imgsrc;}

    public void setImgsrc(String imgsrc) {this.imgsrc = imgsrc;}

    //method
    public boolean priceValidation(String input){
        try{
            float price = Float.parseFloat(input);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public boolean plateNumValidation(String input){
        Matcher matcher = Pattern.compile("[a-zA-Z]{1,3} [0-9]{1,4}").matcher(input);
        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }

    public boolean addressValidation(String input){
        Matcher matcher = Pattern.compile("[A-Za-z0-9'\\.\\-\\s\\,]").matcher(input);
        if (matcher.find()){
            return true;
        }else{
            return false;
        }
    }

    public boolean postCodeValidation(String input){
        Matcher matcher = Pattern.compile("\\d{5}").matcher(input);
        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }

}
