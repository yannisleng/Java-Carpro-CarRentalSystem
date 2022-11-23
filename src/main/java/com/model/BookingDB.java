package com.model;

import java.io.FileWriter;
import java.util.List;

public class BookingDB extends database <Booking>{
    private final String fileName = "booking.txt";

    @Override
    public List<Booking> searchData(String input) {
        return null;
    }

    @Override
    public List<Booking> getAllData() {
        return null;
    }

    @Override
    public void updateData(Object object) {

    }

    @Override
    public void addData(Booking booking) {
        String data = booking.toString();
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
    }

    @Override
    public void deleteData(String fileName) {

    }
}
