package com.model;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ModelDB extends database <Model>{

    private final String fileName = "model.txt";

    public List<Model> getAllData(){
        List<Model> modelList= new ArrayList<Model>();
        ArrayList<String> modeldata = new ArrayList<String>();

        CarDB cardb = new CarDB();
        List<Car> allCars = new ArrayList<>(cardb.getAllData());

        modeldata = readFile(fileName);

        for (int i=0;i< modeldata.size();i++){
            String[] arr = modeldata.get(i).split( "`",3);
            List<Car> carList = new ArrayList<Car>();
            for (int j=0;j< allCars.size();j++){

                if(allCars.get(j).getId().substring(0,6).equals(arr[0])){
                    carList.add(allCars.get(j));
                }
            }
            Model model = new Model(arr[0],arr[1],carList);
            modelList.add(model);
        }
        return modelList;
    };
    public void addData(Model model){
        String data = model.toString();
        System.out.println(data);
        try{
            FileWriter file = new FileWriter(path+fileName, true);
            file.write(data);
            file.close();
            System.out.println("Done");
        }catch (Exception e){
            System.out.println("Database error");
            e.printStackTrace();
        }
    };

    public List<Model> searchData(String input){
        List<Model> modelList = new ArrayList<>();
        List<Model> newList = new ArrayList<>();
        modelList = getAllData();
        for(int i=0;i< modelList.size();i++){
            if(modelList.get(i).getId().equals(input)){
                newList.add(modelList.get(i));
            }
        }
        return newList;
    }

    public void updateData(List list){};
    public void deleteData(String fileName){};
}
