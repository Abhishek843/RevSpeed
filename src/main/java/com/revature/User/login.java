//package com.revature.User;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import org.mindrot.jbcrypt.BCrypt;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Scanner;
//
//public class login {
//    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static Dotenv dotenv = Dotenv.configure().load();
//
//    public static void main(String[] args) {
//        try {
//            Class.forName(JDBC_DRIVER);
//            Connection connection = DriverManager.getConnection(
//                    dotenv.get("DB_URL"),
//                    dotenv.get("DB_USER"),
//                    dotenv.get("DB_PASSWORD")
//            );
//
//            Scanner scanner = new Scanner(System.in);
//
//            while (true) {
//                System.out.println("\nCRUD Operations:");
//                System.out.println("1. Register");
//                System.out.println("2. Login");
//                System.out.println("3. Exit");
//                System.out.print("Enter your choice: ");
//
//                int choice = scanner.nextInt();
//
//                switch (choice) {
//                    case 1:
//                        createUser(connection, scanner);
//                        break;
//                    case 2:
//                        login(connection, scanner);
//                        break;
//                    case 3:
//                        System.out.println("Exiting...");
//                        connection.close();
//                        System.exit(0);
//                        break;
//                    default:
//                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
//                }
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void createUser(Connection connection, Scanner scanner) throws SQLException {
//        System.out.print("Enter name: ");
//        String name = scanner.next();
//        System.out.print("Enter phone: ");
//        String phone_number = scanner.next();
//        System.out.print("Enter address: ");
//        String address = scanner.next();
//        System.out.print("Enter email: ");
//        String email_id = scanner.next();
//        System.out.print("Enter password: ");
//        String password = scanner.next();
//
//        // Hash the password using BCrypt
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//
//        String sql = "INSERT INTO users (name, phone_number, address, email_id, password) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, phone_number);
//            preparedStatement.setString(3, address);
//            preparedStatement.setString(4, email_id);
//            preparedStatement.setString(5, hashedPassword);
//            int rowsAffected = preparedStatement.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("User created successfully.");
//            } else {
//                System.out.println("Failed to create user.");
//            }
//        }
//    }
//
//    private static void login(Connection connection, Scanner scanner) {
//        try {
//            System.out.print("Enter email: ");
//            String email_id = scanner.next();
//            System.out.print("Enter password: ");
//            String password = scanner.next();
//
//            String sql = "SELECT * FROM users WHERE email_id = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setString(1, email_id);
//
//                // Execute the query
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    // Check if the result set has any rows
//                    if (resultSet.next()) {
//                        // Verify the hashed password using BCrypt
//                        if (BCrypt.checkpw(password, resultSet.getString("password"))) {
//                            System.out.println("Login successful!");
//                        } else {
//                            System.out.println("Invalid password. Please try again.");
//                        }
//                    } else {
//                        System.out.println("Invalid email. Please try again.");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
