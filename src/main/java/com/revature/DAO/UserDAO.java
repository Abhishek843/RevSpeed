package com.revature.DAO;



import com.revature.Model.User; // Import the User class from the Model package

import java.sql.SQLException;


public interface UserDAO {
    void createUser(User user) throws SQLException;
    boolean loginUser(String email, String password) throws SQLException;
    boolean changePassword(String email, String newPassword) throws SQLException;
    boolean requestForgottenPassword(String email) throws SQLException;
}
