package com.model;

public class Car {

    public Car(){}

    @Override
    public String toString() {

        return this.id+this.brand+this.model+this.location;
    }

    private String id;
    private String model;
    private String brand;
    private String numPlate;
    private float price;
    private String location;
    private int seat;
    private String imgSrc;

    private database database;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public void setPrice(float price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

   /* public void readcar(){
        ArrayList<String> carList= new ArrayList<>();

        carList = database.readFile("car.txt");
        int i;
        for (i=0;i< carList.size();i++){
            //carList.get(i);
            String[] arr = carList.get(i).split("\n",6);
            this.setId(arr[0]);
            this.setBrand(arr[1]);
            this.setModel(arr[2]);
            this.setNumPlate(arr[3]);
            this.setPrice(Float.parseFloat(arr[4]));
            this.setLocation(arr[5]);
        }
    }*/
}
