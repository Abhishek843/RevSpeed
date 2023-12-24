package com.revature;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CrudJDBC {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/jdbcdemo";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "2798";

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(
                    "DB_URL",
                    "DB_USER",
                    "DB_PASSWORD"
            );

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nCRUD Operations:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createStudent(connection, scanner);
                        break;
                    case 2:
                        readStudents(connection);
                        break;
                    case 3:
                        updateStudent(connection, scanner);
                        break;
                    case 4:
                        deleteStudent(connection, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        connection.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter student name: ");
        String name = scanner.next();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();

        String sql = "INSERT INTO studentcrud (name, age) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student created successfully.");
            } else {
                System.out.println("Failed to create student.");
            }
        }
    }

    private static void readStudents(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM studentcrud");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + " " +
                    resultSet.getString("name") + " " +
                    resultSet.getInt("age"));
        }
    }

    private static void updateStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the ID of the student to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter new student name: ");
        String newName = scanner.next();
        System.out.print("Enter new student age: ");
        int newAge = scanner.nextInt();

        String sql = "UPDATE studentcrud SET name = ?, age = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, newAge);
            preparedStatement.setInt(3, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("Failed to update student. Student not found with ID: " + id);
            }
        }
    }

    private static void deleteStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the ID of the student to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM studentcrud WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Failed to delete student. Student not found with ID: " + id);
            }
        }
    }
}
