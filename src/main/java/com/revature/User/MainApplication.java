package com.revature.User;

import com.revature.DAOImpl.UserDAOImpl;
import com.revature.Model.Plan;
import com.revature.Model.User;
import com.revature.Model.UserBroadbandSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class MainApplication {
    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);
    private static String loggedInUserEmail;
    private static boolean loggedIn = false;

    public static void main(String[] args) {
        try {
//            0gCWLU3McgHN
            UserDAOImpl userDAO = new UserDAOImpl();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                if (!loggedIn) {
                    System.out.println("\nUser Operations:");
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("3. Change Password (if logged in)");
                    System.out.println("4. Request Forgotten Password");
                    System.out.println("44. Update Password");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            registerUser(userDAO, scanner);
                            logger.info("User registered successfully.");
                            break;
                        case 2:
                            loginUser(userDAO, scanner);
                            logger.info("User logged in successfully.");
                            loggedIn = true;
                            break;
                        case 3:
                            if (loggedInUserEmail == null) {
                                System.out.println("Please log in first.");
                            } else {
                                changePassword(userDAO, scanner);
                            }
                            break;
                        case 4:
                            requestForgottenPassword(userDAO, scanner);
                            break;
                        case 44: // New case for changing the password
                            updatePassword(userDAO, scanner);
                            break;

                        case 6:
                            System.out.println("Exiting...");
                            scanner.close();
                            System.exit(0);
                            break;
                        default:
                            logger.warn("Invalid choice entered: {}", choice);
                            System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    }
                } else {
                    System.out.println("\nProfile Page Options:");
                    System.out.println("5. View Plans");
                    System.out.println("7. Update Name");
                    System.out.println("8. Update Phone Number");
                    System.out.println("9. Update Address");
                    System.out.println("10. Logout");
                    System.out.println("11. viewUserBroadbandSubscriptions");
                    System.out.println("12. DeleteSubscribedPlan");

                    System.out.print("Enter your choice: ");

                    int profileChoice = scanner.nextInt();

                    switch (profileChoice) {
                        case 5:
                            System.out.println("Write dth to see dth plans");
                            System.out.println("Write broadband to see broadband  plans");
                            System.out.println("Write internet to see internet plans");
                            viewPlans(userDAO,scanner);

                            subscribeToPlan(userDAO, scanner);
                            logger.info("User subscribed to a plan successfully.");
                            break;
                        case 7:
                            updateName(userDAO, scanner);
                            break;
                        case 8:
                            updatePhoneNumber(userDAO, scanner);
                            break;
                        case 9:
                            updateAddress(userDAO, scanner);
                            break;
                        case 10:
                            loggedInUserEmail = null;
                            loggedIn = false;
                            System.out.println("Logged out successfully.");
                            break;
                        case 11:
                            viewUserBroadbandSubscriptions(userDAO);
                            break;
                        case 12: // New case for deleting a plan
                            deleteSubscribedPlan(userDAO, scanner);
                            break;

                        default:
                            logger.warn("Invalid profile choice entered: {}", profileChoice);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred in the application.", e);
        }
    }
//    private static void subscribeToPlan(UserDAOImpl userDAO, Scanner scanner) {
//        try {
//            System.out.println("Select a plan to subscribe (enter Plan ID): ");
//            int selectedPlanId = scanner.nextInt();
//            Plan selectedPlan = userDAO.getPlanById(selectedPlanId);
//
//            if (selectedPlan != null) {
//                // Assuming you have a method to get the service ID associated with the plan
//                int serviceId = userDAO.getServiceIdForPlan(selectedPlanId);
//
//                // Assuming you have a method to get the user ID using the logged-in user's email
//                int userId = userDAO.getUserIdByEmail(loggedInUserEmail);
//
//
//                java.util.Date currentDate = new java.util.Date();
//                Date startDate = new Date(currentDate.getTime());
//
//                // Set the end date to 1 month after the current date
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(currentDate);
//                calendar.add(Calendar.MONTH, 1);
//                Date endDate = new Date(calendar.getTimeInMillis());
//
//                // Create a new broadband subscription
//                userDAO.subscribeToPlan(userId, selectedPlanId, startDate, endDate);
//
//                System.out.println("Subscription successful!");
//            } else {
//                System.out.println("Invalid Plan ID. Please try again.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private static void subscribeToPlan(UserDAOImpl userDAO, Scanner scanner) {
        try {
            System.out.println("Select a plan to subscribe (enter Plan ID): ");
            int selectedPlanId = scanner.nextInt();
            Plan selectedPlan = userDAO.getPlanById(selectedPlanId);

            if (selectedPlan != null) {
                // Assuming you have a method to get the service ID associated with the plan
                int serviceId = userDAO.getServiceIdForPlan(selectedPlanId);

                // Assuming you have a method to get the user ID using the logged-in user's email
                int userId = userDAO.getUserIdByEmail(loggedInUserEmail);

                java.util.Date currentDate = new java.util.Date();
                Date startDate = new Date(currentDate.getTime());

                // Calculate endDate based on the number of days in the selected plan
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_MONTH, selectedPlan.getDays());
                Date endDate = new Date(calendar.getTimeInMillis());

                // Create a new broadband subscription
                userDAO.subscribeToPlan(userId, selectedPlanId, startDate, endDate);

                System.out.println("Subscription successful!");
                logger.info("User subscribed to plan {} successfully.", selectedPlanId);
            } else {
                System.out.println("Invalid Plan ID. Please try again.");
                logger.warn("Invalid Plan ID entered for subscription.");
            }
        } catch (SQLException e) {
            logger.error("Error subscribing to plan.", e);
        }
    }


    private static void updatePassword(UserDAOImpl userDAO, Scanner scanner) {
        try {
            System.out.print("Enter email: ");
            String loggedInUserEmail = scanner.next();
            System.out.print("Enter current password: ");
            String currentPassword = scanner.next();
            System.out.print("Enter new password: ");
            String newPassword = scanner.next();

            if (userDAO.updatePassword(newPassword,loggedInUserEmail)) {
                System.out.println("Password changed successfully!");
            } else {
                System.out.println(loggedInUserEmail);
                System.out.println(currentPassword);
                System.out.println(newPassword);
                System.out.println("Failed to change password. Please check your current password and try again.");
            }
        } catch (SQLException e) {
            logger.error("Error in password.", e);
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
            logger.error("Error in register.", e);
        }
    }

    private static void deleteSubscribedPlan(UserDAOImpl userDAO, Scanner scanner) {
        try {
            viewUserBroadbandSubscriptions(userDAO); // Display existing subscriptions before deletion

            System.out.print("Enter Subscription ID to delete: ");
            int subscriptionId = scanner.nextInt();

            if (userDAO.unsubscribeFromPlan(subscriptionId)) {
                System.out.println("Subscription deleted successfully!");
            } else {
                System.out.println("Failed to delete subscription. Please try again.");
            }
        } catch (SQLException e) {
            logger.error("Error in deleting.", e);
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
                loggedInUserEmail = email_id;
                boolean m = true; // You may need to retrieve the user details from the database
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }
        } catch (SQLException e) {
            logger.error("Error while login.", e);
        }
    }

    private static void changePassword(UserDAOImpl userDAO, Scanner scanner) {
        try {
            System.out.print("Enter email: ");
            String loggedInUserEmail = scanner.next();
            System.out.print("Enter current password: ");
            String currentPassword = scanner.next();
            System.out.print("Enter new password: ");
            String newPassword = scanner.next();

            if (userDAO.changePassword(loggedInUserEmail, newPassword)) {
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("Failed to change password. Please try again.");
            }
        } catch (SQLException e) {
            logger.error("Error updating password.", e);
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
            logger.error("Error updating password.", e);
        }
    }
    private static void updateName(UserDAOImpl userDAO, Scanner scanner) {
        try {

            System.out.print("Enter new name: ");
            String newName = scanner.next();
            userDAO.updateName(loggedInUserEmail, newName);
        } catch (SQLException e) {
            logger.error("Error updating name.", e);
        }
    }

    private static void updatePhoneNumber(UserDAOImpl userDAO, Scanner scanner) {
        try {
            System.out.print("Enter new phone number: ");
            String newPhoneNumber = scanner.next();
            userDAO.updatePhoneNumber(loggedInUserEmail, newPhoneNumber);
        } catch (SQLException e) {
            logger.error("Error updating phone number.", e);
        }
    }

    private static void updateAddress(UserDAOImpl userDAO, Scanner scanner) {
        try {
            System.out.print("Enter new address: ");
            String newAddress = scanner.next();
            userDAO.updateAddress(loggedInUserEmail, newAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void viewPlans(UserDAOImpl userDAO,Scanner scanner) {
        try {
            System.out.print("Please Select plan type: ");
            String plan_type = scanner.next();
            List<Plan> plans = userDAO.getPlans(plan_type);

            if (plans.isEmpty()) {
                System.out.println("No plans available.");
            } else {

                System.out.println("Available Plans:");
                System.out.println(plans);

                for (Plan plan : plans) {
                    System.out.println("Plan ID: " + plan.getPlanId());
                    System.out.println("Service ID: " + plan.getServiceId());
                    System.out.println("Plan Name: " + plan.getPlanName());
                    System.out.println("Price: " + plan.getPrice());
                    System.out.println("Days: " + plan.getDays());
                    System.out.println("PlanType: " + plan.getPlanType());
                    System.out.println("PlanInfo: " + plan.getPlanInfo());
                    System.out.println("OttBenefits: " + plan.getOttBenefits());


                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewUserBroadbandSubscriptions(UserDAOImpl userDAO) {
        try {
            int userId = userDAO.getUserIdByEmail(loggedInUserEmail);
            List<UserBroadbandSubscription> subscriptions = userDAO.getUserBroadbandSubscription(userId);

            if (subscriptions.isEmpty()) {
                System.out.println("No broadband subscriptions available.");
            } else {
                System.out.println("User Broadband Subscriptions:");
                for (UserBroadbandSubscription subscription : subscriptions) {
                    System.out.println("Subscription ID: " + subscription.getUserBroadbandSubscriptionId());
                    System.out.println("User ID: " + subscription.getUserId());
                    System.out.println("Service ID: " + subscription.getServiceId());
                    System.out.println("Start Date: " + subscription.getSubscriptionStartDate());
                    System.out.println("End Date: " + subscription.getSubscriptionEndDate());

                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




//    private static void viewPlans() {
//        // Implement logic to view plans (if needed)
//        System.out.println("Viewing plans...");
//    }


}

