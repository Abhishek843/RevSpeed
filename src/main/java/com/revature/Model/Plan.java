package com.revature.Model;


public class Plan {
    private int planId;
    private int serviceId;
    private String planName;
    private double price;

    // Default constructor
    public Plan() {
    }

    // Parameterized constructor
    public Plan(int planId, int serviceId, String planName, double price) {
        this.planId = planId;
        this.serviceId = serviceId;
        this.planName = planName;
        this.price = price;
    }

    // Getter and Setter methods

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
