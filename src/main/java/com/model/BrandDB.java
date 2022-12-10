package com.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDB extends database <Brand>{

    public List<Brand> getAllData(){
        List<Brand> brandList= new ArrayList<Brand>();
        ArrayList<String> branddata = new ArrayList<String>();

        ModelDB modeldb = new ModelDB();
        List<Model> allModels = new ArrayList<>(modeldb.getAllData());

        branddata = readFile(brandPath);

        for (int i=0;i< branddata.size();i++){
            String[] arr = branddata.get(i).split( "`",3);
            List<Model> modelList = new ArrayList<Model>();
            for (int j=0;j< allModels.size();j++){
                if(allModels.get(j).getId().substring(0,3).equals(arr[0])){
                    modelList.add(allModels.get(j));
                }
            }
            Brand brand = new Brand(arr[0],arr[1],modelList);
            brandList.add(brand);
        }
        return brandList;
    };

    public void addData(Brand brand){
        String data = brand.toString();
        System.out.println(data);
        try{
            FileWriter file = new FileWriter(brandPath, true);
            file.write(data);
            file.close();
            System.out.println("Done");
        }catch (Exception e){
            System.out.println("Database error");
            e.printStackTrace();
        }
    };

    public List<Brand> searchData(String input){
        List<Brand> brandList = new ArrayList<>();
        List<Brand> newList = new ArrayList<>();
        brandList = getAllData();
        for(int i=0;i< brandList.size();i++){
            if(brandList.get(i).getId().equals(input)){
                newList.add(brandList.get(i));
            }
        }
        return newList;
    };

    public void updateData(Brand brand){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("car");
        List<Car> cars = new ArrayList<>(db.getAllData());
        String stringToReplace = null;
        for(Car car: cars){
            if(brand.getId().equals(car.getId().substring(0,3))){
                stringToReplace = car.getBrand();
            }
        }

        if(stringToReplace!=null){
            updateFile(carPath,stringToReplace,brand.getBrandName());
        }

        List<Brand> brands = new ArrayList<>(getAllData());
        for(int i = 0; i < brands.size(); i++){
            if(brand.getId().equals(brands.get(i).getId())){
                brands.set(i,brand);
                try {
                    FileWriter file = new FileWriter(brandPath);
                    for(Brand item: brands){
                        file.write(String.valueOf(item));
                    }
                    file.close();
                    System.out.println("brand updated");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    };
    public void deleteData(String removeData){
        deleteFile(brandPath,removeData);
    };
}
