package com.model;

public class dataFactory {

    public database getDB(String dataType){
        if (dataType == "car"){
            return new CarDB();
        }else if(dataType == "user"){
            return new UserDB();
        }else if(dataType == "model"){
            return new ModelDB();
        }else if(dataType == "brand"){
            return new BrandDB();
        }
        return null;
    }
}
