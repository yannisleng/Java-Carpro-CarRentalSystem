package com.model;

import java.util.Objects;

public class dataFactory {

    public database getDB(String dataType){
        if (Objects.equals(dataType, "car")){
            return new CarDB();
        }else if(Objects.equals(dataType, "user")){
            return new UserDB();
        }else if(Objects.equals(dataType, "model")){
            return new ModelDB();
        }else if(Objects.equals(dataType, "brand")){
            return new BrandDB();
        }else if(Objects.equals(dataType, "booking")){
            return new BookingDB();
        }else if(Objects.equals(dataType, "payment")){
            return new PaymentDB();
        }
        return null;
    }
}
