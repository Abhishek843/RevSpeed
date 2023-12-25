package com.revature.DAOImpl;
import java.security.SecureRandom;
import java.util.*;
import com.revature.DAO.UserDAO;
import com.revature.Model.User;
import com.revature.Model.Plan;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAOImpl implements UserDAO {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final Connection connection;
    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }
    static final String DB_URL = "jdbc:mysql://localhost:3306/224speed";
    static final String DB_USER = "root";
    static final String DB_PASSWORD= "2798";
    public UserDAOImpl() {
        try {
            Class.forName(JDBC_DRIVER);
            this.connection = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error initializing database connection.", e);
        }
    }
    @Override
    public void updateName(String email, String newName) throws SQLException {
        String sql = "UPDATE users SET name = ? WHERE email_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Name updated successfully in the database.");
            } else {
                System.out.println("Failed to update name in the database.");
            }
        }
    }

    @Override
    public void updatePhoneNumber(String email, String newPhoneNumber) throws SQLException {
        String sql = "UPDATE users SET phone_number = ? WHERE email_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newPhoneNumber);
            preparedStatement.setString(2, email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Phone number updated successfully in the database.");
            } else {
                System.out.println("Failed to update phone number in the database.");
            }
        }
    }

    @Override
    public void updateAddress(String email, String newAddress) throws SQLException {
        String sql = "UPDATE users SET address = ? WHERE email_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newAddress);
            preparedStatement.setString(2, email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Address updated successfully in the database.");
            } else {
                System.out.println("Failed to update address in the database.");
            }
        }
    }

    @Override
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, phone_number, address, email_id, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getEmail());

            String hashedPassword = BCrypt.hashpw(user.getHashedPassword(), BCrypt.gensalt());
            preparedStatement.setString(5, hashedPassword);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User created successfully.");
            } else {
                System.out.println("Failed to create user.");
            }
        }
    }

    @Override
    public boolean loginUser(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return BCrypt.checkpw(password, resultSet.getString("password"));
                } else {
                    return false;
                }
            }
        }
    }
    @Override
    public boolean changePassword(String email, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE email_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, email);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean requestForgottenPassword(String email) throws SQLException {
        // Implement logic to generate and send a new password to the user's email
        // For simplicity, let's just update the password in the database
        String newPassword = generateRandomPassword();
        System.out.println(newPassword);// You need to implement this method
        return changePassword(email, newPassword);
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Generate a password of a certain length (e.g., 12 characters)
        int length = 12;

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }


        return password.toString();
    }

    @Override
    public List<Plan> getPlans() throws SQLException {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT * FROM plans";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Plan plan = new Plan();
                    plan.setPlanId(resultSet.getInt("plan_id"));
                    plan.setServiceId(resultSet.getInt("service_id"));
                    plan.setPlanName(resultSet.getString("plan_name"));
                    plan.setPrice(resultSet.getDouble("price"));
                    plans.add(plan);
                }
            }
        }

        return plans;
    }
}
