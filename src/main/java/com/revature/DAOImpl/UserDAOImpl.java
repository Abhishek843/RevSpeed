package com.revature.DAOImpl;

import com.revature.DAO.UserDAO;
import com.revature.Model.User;
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
}
