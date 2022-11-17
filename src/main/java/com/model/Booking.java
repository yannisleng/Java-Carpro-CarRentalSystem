package com.model;

import java.util.Date;

public class Booking {
    private String id;
    //private User customer;
    private Car car;
    private Date startDate;
    private Date startTime;
    private Date endDate;
    private Date endTime;
    private String status;
    private Payment payment = null; //update when it is paid

    public Booking() {
    }

    /*public Booking(String id, User customer, Car car, Date startDate, Date startTime, Date endDate, Date endTime, String status, Payment payment) {
        this.id = id;
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.status = status;
        this.payment = payment;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }*/

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /*@Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", customer=" + customer +
                ", car=" + car +
                ", startDate=" + startDate +
                ", startTime=" + startTime +
                ", endDate=" + endDate +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                ", payment=" + payment +
                '}';
    }*/
}
