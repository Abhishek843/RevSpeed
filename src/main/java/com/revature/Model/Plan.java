package com.revature.Model;


public class Plan {
    private int planId;
    private int serviceId;
    private String planName;
    private double price;

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    private String plan_type;

    public String getOtt_benefits() {
        return ott_benefits;
    }

    public void setOtt_benefits(String ott_benefits) {
        this.ott_benefits = ott_benefits;
    }

    private String ott_benefits;

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    private String planType;

    public String getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(String planInfo) {
        this.planInfo = planInfo;
    }

    private String planInfo;
    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    private int days;

    // Default constructor
    public Plan() {
    }

    // Parameterized constructor
    public Plan(int planId, int serviceId, String planName, double price,int days,String ott_benefits,String planInfo ,String plan_type) {
        this.planId = planId;
        this.serviceId = serviceId;
        this.planName = planName;
        this.price = price;
        this.days=days;
        this.plan_type=plan_type;
        this.ott_benefits=ott_benefits;
        this.planInfo=planInfo;
        this.planType=plan_type;
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
