package com.revature.Model;

import java.sql.Date;

public class UserBroadbandSubscription {
    private int userBroadbandSubscriptionId;
    private int userId;
    private int serviceId;
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;

    public UserBroadbandSubscription() {
        // Default constructor
    }

    public UserBroadbandSubscription(int userBroadbandSubscriptionId, int userId, int serviceId, Date subscriptionStartDate, Date subscriptionEndDate) {
        this.userBroadbandSubscriptionId = userBroadbandSubscriptionId;
        this.userId = userId;
        this.serviceId = serviceId;
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionEndDate = subscriptionEndDate;
    }

    // Getters and Setters

    public int getUserBroadbandSubscriptionId() {
        return userBroadbandSubscriptionId;
    }

    public void setUserBroadbandSubscriptionId(int userBroadbandSubscriptionId) {
        this.userBroadbandSubscriptionId = userBroadbandSubscriptionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public Date getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(Date subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(Date subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    @Override
    public String toString() {
        return "UserBroadbandSubscription{" +
                "userBroadbandSubscriptionId=" + userBroadbandSubscriptionId +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                ", subscriptionStartDate=" + subscriptionStartDate +
                ", subscriptionEndDate=" + subscriptionEndDate +
                '}';
    }
}
