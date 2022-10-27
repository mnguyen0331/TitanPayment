import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Starts here
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------------");
        System.out.println("Welcome to Titan Payment System!");
        displayAccountService();
        int userSelection = getIntFromUser(scanner);
        while (true) {
            if (userSelection == 1)
                new User(scanner);

            else if (userSelection == 2) {
                System.out.print("Enter username: ");
                String userName = scanner.next().trim();
                HashMap<String, User> users = User.users;
                if (users.containsKey(userName)) {
                    System.out.println();
                    System.out.println("Current Account: " + userName);
                    User currentUser = users.get(userName);
                    displayMenu();
                    userSelection = getIntFromUser(scanner);
                    while (userSelection != 9) {
                        switch (userSelection) {

                            case 1:
                                currentUser.queryAccountInfo(scanner);
                                break;

                            case 2:
                                currentUser.uploadPurchases(scanner);
                                break;

                            case 3:
                                currentUser.getMinimumAndMaximumTransaction();
                                wait(4000);
                                break;

                            case 4:
                                currentUser.getAmountDue();
                                break;

                            case 5:
                                currentUser.getTotalAmountPaid();
                                break;

                            case 6:
                                currentUser.payCard(scanner);
                                break;

                            case 7:
                                // getPaymentHistory();
                                break;

                            case 8:
                                currentUser.displayPurchasesMade();
                                wait(4000);
                                break;

                            default:
                                System.out.println("Invalid option. Please try again!\n");
                        }
                        displayMenu();
                        System.out.print("Pick an acion from the menu: ");
                        userSelection = scanner.nextInt();
                    }

                } else
                    System.out.println("Username does not exist.");
            }

            else if (userSelection == 3) {
                System.out.println("Thanks for using Titan Payment. See you again.\n");
                break;
            }

            else
                System.out.println("Invalid option. Please try again!");

            displayAccountService();
            userSelection = getIntFromUser(scanner);
        }
        scanner.close();
    }

    public static void displayAccountService() {
        System.out.println();
        System.out.println("1. Create new account");
        System.out.println("2. I already have an account");
        System.out.println("3. Exit\n");
    }

    public static void displayMenu() {
        System.out.println();
        System.out.println("Menu selection:\n");
        System.out.println("1. Query account information");
        System.out.println("2. Upload purchases");
        System.out.println("3. Get minimum and maximum transaction");
        System.out.println("4. Get amount due");
        System.out.println("5. Get total amount paid");
        System.out.println("6. Pay card");
        System.out.println("7. Get payment history");
        System.out.println("8. Display all the purchases made");
        System.out.println("9. Exit\n");
    }

    public static void wait(int miliSecond) {
        try {
            Thread.sleep(miliSecond);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static int getIntFromUser(Scanner scanner) {
        int result = -1;
        System.out.print("Pick an acion from the menu: ");
        String userInput = scanner.next();
        while (true) {
            try {
                result = Integer.parseInt(userInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not an integer. Please try again\n");
                System.out.print("Pick an acion from the menu: ");
                userInput = scanner.next();
            }
        }
        return result;
    }
}
