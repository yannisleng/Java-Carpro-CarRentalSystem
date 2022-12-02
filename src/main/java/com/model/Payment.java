package com.model;

import java.time.LocalDate;

public class Payment {
    private String id;
    private LocalDate date = LocalDate.now();
    private String method;
    private float total;

    public Payment() {
    }

    public Payment(String id, LocalDate date, String method, float total) {
        this.id = id;
        this.date = date;
        this.method = method;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return id+"`"+date+"`"+method+"`"+total+"\n";
    }
}
