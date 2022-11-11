package com.model;

import java.util.ArrayList;
import java.util.List;

public class ModelDB extends database{

    private final String fileName = "model.txt";

    public List<Model> getAllData(){
        List<Model> modelList= new ArrayList<Model>();
        List<Car> carList = new ArrayList<Car>();
        ArrayList<String> modeldata = new ArrayList<String>();

        CarDB cardb = new CarDB();
        List<Car> allCars = new ArrayList<>(cardb.getAllData());

        modeldata = readFile(fileName);

        for (int i=0;i< modeldata.size();i++){
            String[] arr = modeldata.get(i).split( "`",3);

            for (int j=0;j< allCars.size();j++){
                if(allCars.get(j).getId().substring(0,6).equals(arr[0])){
                    carList.add(allCars.get(j));
                }
            }

            Model model = new Model(arr[0],arr[1],arr[2],carList);
            modelList.add(model);
            carList.clear();
        }
        return modelList;
    };

    public void updateData(Object object){};
    public void addData(){};
    public void deleteData(String fileName){};
}
