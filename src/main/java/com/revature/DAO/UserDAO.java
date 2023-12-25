package com.revature.DAO;

import java.util.*;

import com.revature.Model.User; // Import the User class from the Model package

import java.sql.SQLException;
import com.revature.Model.Plan;

public interface UserDAO {
    void createUser(User user) throws SQLException;
    boolean loginUser(String email, String password) throws SQLException;
    boolean changePassword(String email, String newPassword) throws SQLException;
    boolean requestForgottenPassword(String email) throws SQLException;
    void updateName(String email, String newName) throws SQLException;
    void updatePhoneNumber(String email, String newPhoneNumber) throws SQLException;
    void updateAddress(String email, String newAddress) throws SQLException;
    List<Plan> getPlans() throws SQLException;
}
