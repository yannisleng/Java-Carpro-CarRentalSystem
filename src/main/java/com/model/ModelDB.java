package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelDB extends database <Model>{

    public List<Model> getAllData(){
        List<Model> modelList= new ArrayList<Model>();
        ArrayList<String> modeldata = new ArrayList<String>();

        CarDB cardb = new CarDB();
        List<Car> allCars = new ArrayList<>(cardb.getAllData());

        modeldata = readFile(modelPath);

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
            FileWriter file = new FileWriter(modelPath, true);
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

    public void updateData(Model model){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> cars = new ArrayList<>(db.getAllData());
        String stringToReplace = null;
        for(Car car: cars){
            if(model.getId().equals(car.getId().substring(0,6))){
                stringToReplace = car.getModel();
            }
        }

        if(stringToReplace!=null){
            updateFile(carPath,stringToReplace,model.getModelName());
        }

        List<Model> models = new ArrayList<>(getAllData());
        for(int i = 0; i < models.size(); i++){
            if(model.getId().equals(models.get(i).getId())){
                models.set(i,model);
                try {
                    FileWriter file = new FileWriter(modelPath);
                    for(Model item: models){
                        file.write(String.valueOf(item));
                    }
                    file.close();
                    System.out.println("model updated");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void deleteData(String removeData){
        deleteFile(modelPath,removeData);
    };
}
