package com.model;

import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentDB extends database <Payment>{
    private final String fileName = "payment.txt";

    @Override
    public List<Payment> searchData(String input) {
        return null;
    }

    @Override
    public List<Payment> getAllData() {
        List<Payment> ls= new ArrayList<Payment>();
        ArrayList<String> data = new ArrayList<String>();

        data = readFile(fileName);
        for (int i=0;i< data.size();i++){
            Payment payment = new Payment();
            String[] arr = data.get(i).split( "`",4);
            payment.setId(arr[0]);
            payment.setStatus(arr[1]);
            payment.setMethod(arr[2]);
            payment.setTotal(Float.parseFloat(arr[3]));
            ls.add(payment);
        }
        return ls;
    }

    @Override
    public void updateData(List list) {

    }

    @Override
    public void addData(Payment payment) {
        String data = payment.toString();
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
