package com.model;

import java.time.LocalDate;

public class Booking {
    private String id;
    private String customerId;
    private String carId;
    private LocalDate startDate;
    private String startTime;
    private LocalDate endDate;
    private String endTime;
    private String status;
    private String paymentId; //update when it is paid

    public Booking() {
    }

    public Booking(String customerId, String carId) {
        this.id = "00001";
        this.customerId = customerId;
        this.carId = carId;
        this.status = "Pending";
    }

    /*public Booking(String id, String customer, String car, String startDate, String startTime, String endDate, String endTime) {
        this.id = id;
        this.customerId = customer;
        this.carId = car;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.status = "Pending";
        this.paymentId = null;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public String toString() {
        return id+"`"+ customerId +"`"+ carId +"`"+startDate+"`"+startTime+"`"+endDate+"`"+endTime+"`"+status+"`"+ paymentId +"\n";
    }
}
