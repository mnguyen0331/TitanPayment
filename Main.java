/*
 * Author: Phu Nguyen
 * Date: 10/31/2022
 * Project: Titan Payment System
 * Course: CPSC335-07 22473
 */

import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Starts here
        Scanner scanner = new Scanner(System.in);
        Helper.printDash(60);
        System.out.println("Welcome to Titan Payment System!\n");
        displayAccountService();
        int userSelection = Helper.getIntFromUser(scanner);
        while (true) {
            if (userSelection == 1)
                new User(scanner);

            else if (userSelection == 2) {
                System.out.print("Enter username: ");
                String userName = scanner.nextLine().trim();
                HashMap<String, User> users = User.users;
                if (users.containsKey(userName)) {
                    System.out.print("Enter password: ");
                    String inputPassword = scanner.nextLine().trim();
                    if (users.get(userName).getPassword().equals(inputPassword)) {
                        System.out.println("\nLog In Successfully!");
                        System.out.println("Current Account: " + userName + "\n");
                        User currentUser = users.get(userName);
                        displayMenu();
                        userSelection = Helper.getIntFromUser(scanner);
                        Helper.clearScreen();
                        while (userSelection != 9) {
                            switch (userSelection) {

                                case 1:
                                    currentUser.queryAccountInfo(scanner);
                                    break;

                                case 2:
                                    currentUser.uploadPurchases(scanner);
                                    break;

                                case 3:
                                    Helper.wait(1000, "System Processing ..................................");
                                    currentUser.getMinimumAndMaximumTransaction();
                                    break;

                                case 4:
                                    currentUser.getAmountDue(scanner);
                                    break;

                                case 5:
                                    currentUser.getTotalAmountPaid(scanner);
                                    break;

                                case 6:
                                    currentUser.payCard(scanner);
                                    break;

                                case 7:
                                    Helper.wait(1000, "System Processing ..................................");
                                    currentUser.getPaymentHistory();
                                    break;

                                case 8:
                                    currentUser.displayPurchasesMade(scanner);
                                    break;

                                default:
                                    System.out.println("Invalid option. Please try again!\n");
                            }
                            Helper.wait(500, "Back to Menu .......................................");
                            displayMenu();
                            userSelection = Helper.getIntFromUser(scanner);
                            Helper.clearScreen();
                        }

                    } else
                        System.out.println("Incorrect Password. Please try again!");

                } else
                    System.out.println("Account \"" + userName + "\" does not exist.");
            }

            else if (userSelection == 3) {
                System.out.println("\nThanks for using Titan Payment. See you again.\n");
                break;
            }

            else
                System.out.println("Invalid option. Please try again!");

            Helper.wait(500, "Back to Home .......................................");
            displayAccountService();
            userSelection = Helper.getIntFromUser(scanner);
            Helper.clearScreen();
        }
        scanner.close();
    }

    public static void displayAccountService() {
        System.out.println("1. Create new account");
        System.out.println("2. Log In");
        System.out.println("3. Exit\n");
    }

    public static void displayMenu() {
        System.out.println("Menu selection:\n");
        System.out.printf("%-30s", "1. Query account information");
        System.out.printf("%-30s", "2. Upload purchases");
        System.out.println("3. Get minimum and maximum transaction");
        System.out.printf("%-30s", "4. Get total due");
        System.out.printf("%-30s", "5. Get total payment");
        System.out.println("6. Pay card");
        System.out.printf("%-30s", "7. Get payment history");
        System.out.printf("%-30s", "8. Display purchases");
        System.out.println("9. Exit\n");
    }

}
