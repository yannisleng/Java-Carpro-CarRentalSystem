package com.model;

import java.util.List;

public class PaymentDB extends database <Payment>{
    private String file = "payment.txt";

    @Override
    public List<Payment> searchData(String input) {
        return null;
    }

    @Override
    public List<Payment> getAllData() {
        return null;
    }

    @Override
    public void updateData(Payment payment) {

    }

    @Override
    public void addData(Payment payment) {

    }

    @Override
    public void deleteData(String fileName) {

    }
}
