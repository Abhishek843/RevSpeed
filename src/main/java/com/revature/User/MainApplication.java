package com.revature.User;




import com.revature.Model.User;

import com.revature.DAOImpl.UserDAOImpl;

import java.sql.SQLException;

import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        try {
            UserDAOImpl userDAO = new UserDAOImpl();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nUser Operations:");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
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
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
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
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
