import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Starts here
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------------");
        System.out.println("Welcome to Titan Payment System!");
        displayAccountService();
        System.out.print("Your selection: ");
        int userSelection = scanner.nextInt();
        while (true) {
            if (userSelection == 1)
                new User();

            else if (userSelection == 2) {
                System.out.print("Enter username: ");
                String userName = scanner.next().trim();
                HashMap<String, User> users = User.users;
                if (users.containsKey(userName)) {
                    System.out.println();
                    System.out.println("Current Account: " + userName);
                    User currentUser = users.get(userName);
                    displayMenu();
                    System.out.print("Pick an acion from the menu: ");
                    int menuSelection = scanner.nextInt();
                    while (menuSelection != 9) {
                        switch (menuSelection) {

                            case 1:
                                currentUser.queryAccountInfo();
                                break;

                            case 2:
                                // uploadPurchases();
                                break;

                            case 3:
                                // getMinimumTransaction();
                                break;

                            case 4:
                                // getMaximumTransaction();
                                break;

                            case 5:
                                // getAmountDue();
                                break;

                            case 6:
                                // getTotalAmountPaid();
                                break;

                            case 7:
                                // getPaymentHistory();
                                break;

                            case 8:
                                // displayPurchasesMade();
                                break;

                            default:
                                System.out.println("Invalid option. Please try again!\n");
                        }
                        System.out.print("Pick an acion from the menu: ");
                        menuSelection = scanner.nextInt();
                    }

                } else
                    System.out.println("Username does not exist.");
            }

            else if (userSelection == 3) {
                System.out.println("Thanks for using Titan Payment. See you again.\n");
                break;
            }

            else
                System.out.println("Invalid selection. Please try again!");

            displayAccountService();
            System.out.print("Your selection: ");
            userSelection = scanner.nextInt();
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
        System.out.println("3. Get minimum transaction");
        System.out.println("4. Get maximum transaction");
        System.out.println("5. Get amount due");
        System.out.println("6. Get total amount paid");
        System.out.println("7. Get payment history");
        System.out.println("8. Display all the purchases made");
        System.out.println("9. Exit\n");
    }

}
