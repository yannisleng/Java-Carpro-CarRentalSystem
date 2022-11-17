package com.model;

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

    public void updateData(Object object){};
    public void addData(Brand brand){};
    public void deleteData(String fileName){};
}
