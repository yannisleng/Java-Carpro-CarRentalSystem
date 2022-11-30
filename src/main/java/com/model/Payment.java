package com.model;

public class Payment {
    private String id;
    private String status;
    private String method;
    private float total;

    public Payment() {
    }

    public Payment(String id, String status, String method, float total) {
        this.id = id;
        this.status = status;
        this.method = method;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return id+"`"+status+"`"+method+"`"+total+"\n";
    }
}
