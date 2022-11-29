package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDB extends database <Booking>{
    @Override
    public List<Booking> searchData(String input) {
        return null;
    }

    @Override
    public List<Booking> getAllData() {
        List<Booking> ls= new ArrayList<Booking>();
        ArrayList<String> data = new ArrayList<String>();

        data = readFile(bookingPath);
        for (int i=0;i< data.size();i++){
            Booking booking = new Booking();
            String[] arr = data.get(i).split( "`",9);
            booking.setId(arr[0]);
            booking.setCustomerId(arr[1]);
            booking.setCarId(arr[2]);
            booking.setStartDate(LocalDate.parse(arr[3]));
            booking.setStartTime(arr[4]);
            booking.setEndDate(LocalDate.parse(arr[5]));
            booking.setEndTime(arr[6]);
            booking.setStatus(arr[7]);
            booking.setPaymentId(arr[8]);
            ls.add(booking);
        }
        return ls;
    }

    @Override
    public void updateData(Booking booking) {
        List<Booking> bookings = new ArrayList<>(getAllData());
        for(int i = 0; i < bookings.size(); i++){
            if(booking.getId().equals(bookings.get(i).getId())){
                bookings.set(i,booking);
                try {
                    FileWriter file = new FileWriter(bookingPath);
                    for(Booking item: bookings){
                        file.write(String.valueOf(item));
                    }
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addData(Booking booking) {
        String data = booking.toString();
        System.out.println(data);
        try{
            FileWriter file = new FileWriter(bookingPath, true);
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
