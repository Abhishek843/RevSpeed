package com.revature.User;

import com.revature.Model.User;
import com.revature.DAOImpl.UserDAOImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class MainApplication {
    private static User loggedInUser; // Added declaration

    public static void main(String[] args) {
        try {
            UserDAOImpl userDAO = new UserDAOImpl();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nUser Operations:");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Change Password (if logged in)");
                System.out.println("4. Request Forgotten Password");
                System.out.println("5. View Plans (if logged in)");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        registerUser(userDAO, scanner);
                        break;
                    case 2:
                        loginUser(userDAO, scanner);
                        break;
                    case 3:
                        if (loggedInUser == null) {
                            changePassword(userDAO, scanner);
                        } else {
                            System.out.println("Please log in first.");
                        }
                        break;
                    case 4:
                        requestForgottenPassword(userDAO, scanner);
                        break;
                    case 5:
                        if (loggedInUser != null) {
                            viewPlans();
                        } else {
                            System.out.println("Please log in first.");
                        }
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerUser(UserDAOImpl userDAO, Scanner scanner) {
        try {
            System.out.print("Enter name: ");
            String name = scanner.next();
            System.out.print("Enter phone: ");
            String phone_number = scanner.next();
            System.out.print("Enter address: ");
            String address = scanner.next();
            System.out.print("Enter email: ");
            String email_id = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();

            userDAO.createUser(new User(name, phone_number, address, email_id, password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loginUser(UserDAOImpl userDAO, Scanner scanner) {
        try {
            System.out.print("Enter email: ");
            String email_id = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();

            if (userDAO.loginUser(email_id, password)) {
                System.out.println("Login successful!");
                boolean m = true; // You may need to retrieve the user details from the database
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void changePassword(UserDAOImpl userDAO, Scanner scanner) {
        try {
            System.out.print("Enter current password: ");
            String currentPassword = scanner.next();
            System.out.print("Enter new password: ");
            String newPassword = scanner.next();

            if (userDAO.changePassword(loggedInUser.getEmail(), newPassword)) {
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("Failed to change password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void requestForgottenPassword(UserDAOImpl userDAO, Scanner scanner) {
        try {
            System.out.print("Enter email: ");
            String email = scanner.next();

            if (userDAO.requestForgottenPassword(email)) {
                System.out.println("New password requested successfully!");
            } else {
                System.out.println("Failed to request a new password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewPlans() {
        // Implement logic to view plans (if needed)
        System.out.println("Viewing plans...");
    }
}
