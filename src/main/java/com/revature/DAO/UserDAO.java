package com.revature.DAO;

import java.util.*;

import com.revature.Model.User; // Import the User class from the Model package

import java.sql.SQLException;
import com.revature.Model.Plan;
import com.revature.Model.UserBroadbandSubscription;

public interface UserDAO {
    void createUser(User user) throws SQLException;
    public boolean updatePassword(String newPassword,String email) throws SQLException;
    boolean subscribeToPlan(int userId, int planId, java.sql.Date subscriptionStartDate, java.sql.Date subscriptionEndDate) throws SQLException;
    boolean loginUser(String email, String password) throws SQLException;
    boolean changePassword(String email, String newPassword) throws SQLException;
    boolean requestForgottenPassword(String email) throws SQLException;
    void updateName(String email, String newName) throws SQLException;
    void updatePhoneNumber(String email, String newPhoneNumber) throws SQLException;
    void updateAddress(String email, String newAddress) throws SQLException;
    List<Plan> getPlans(String plan_type) throws SQLException;
    List<UserBroadbandSubscription> getUserBroadbandSubscription(int userId) throws SQLException;

    Plan getPlanById(int planId) throws SQLException;

    int getServiceIdForPlan(int planId) throws SQLException;

    int getUserIdByEmail(String email) throws SQLException;
    boolean unsubscribeFromPlan(int subscriptionId) throws SQLException;
}
