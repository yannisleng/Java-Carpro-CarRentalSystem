package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentDB extends database <Payment>{

    @Override
    public List<Payment> searchData(String input) {
        return null;
    }

    @Override
    public List<Payment> getAllData() {
        List<Payment> ls= new ArrayList<Payment>();
        ArrayList<String> data = new ArrayList<String>();

        data = readFile(paymentPath);
        for (int i=0;i< data.size();i++){
            Payment payment = new Payment();
            String[] arr = data.get(i).split( "`",4);
            payment.setId(arr[0]);
            payment.setDate(LocalDate.parse(arr[1]));
            payment.setMethod(arr[2]);
            payment.setTotal(Float.parseFloat(arr[3]));
            ls.add(payment);
        }
        return ls;
    }

    @Override
    public void updateData(Payment payment) {

    }

    @Override
    public void addData(Payment payment) {
        String data = payment.toString();
        try{
            FileWriter file = new FileWriter(paymentPath, true);
            file.write(data);
            file.close();
        }catch (Exception e){
            System.out.println("Database error");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteData(String fileName) {

    }
}
