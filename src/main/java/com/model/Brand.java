package com.model;

import java.util.List;

public class Brand {
    private String id;
    private String brandName;
    private List<Model> models;

    public Brand(){}

    public Brand(String id,String brandName,List<Model> models){
        this.id = id;
        this.brandName = brandName;
        this.models = models;
    }
    @Override
    public String toString(){
        return id+"`"+brandName+"\n";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<Model> getModels(){
        return models;
    }
}
