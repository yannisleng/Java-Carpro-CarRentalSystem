package com.model;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class BrandDB extends database <Brand>{

    private String fileName = "brand.txt";

    public List<Brand> getAllData(){
        List<Brand> brandList= new ArrayList<Brand>();
        ArrayList<String> branddata = new ArrayList<String>();

        ModelDB modeldb = new ModelDB();
        List<Model> allModels = new ArrayList<>(modeldb.getAllData());

        branddata = readFile(fileName);

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
            FileWriter file = new FileWriter(path+fileName, true);
            file.write(data);
            file.close();
            System.out.println("Done");
        }catch (Exception e){
            System.out.println("Database error");
            e.printStackTrace();
        }
    };

    public void updateData(Object object){};
    public void deleteData(String fileName){};
}
