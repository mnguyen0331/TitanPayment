import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class User {
    protected static HashMap<String, User> users = new HashMap<String, User>();

    private String userName;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String country;
    private Card creditCard;

    public User(Scanner userScanner) {

        System.out.print("Enter your username: ");
        String userNameInput = userScanner.next();
        userName = userNameInput.trim();

        System.out.print("Enter your password: ");
        String passwordInput = userScanner.next();
        password = passwordInput.trim();
        userScanner.nextLine();

        System.out.print("Enter your full name: ");
        String fullNameInput = userScanner.nextLine();
        fullName = fullNameInput.trim();

        System.out.print("Enter your phone number: ");
        String phoneNumberInput = userScanner.nextLine();
        phoneNumber = phoneNumberInput.trim();

        System.out.print("Enter your address: ");
        String addressInput = userScanner.nextLine();
        address = addressInput.trim();

        System.out.print("Enter your country: ");
        String countryInput = userScanner.next();
        country = countryInput.trim();

        creditCard = Card.getCardType(userScanner);

        users.put(userName, this);
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String toString() {
        return "User(" + userName + "," + password + "," + fullName + "," + phoneNumber
                + "," + address + "," + country + creditCard + ")";
    }

    public void queryAccountInfo(Scanner scanner) {
        System.out.println();
        System.out.println("a. Get account's username");
        System.out.println("b. Get account's password");
        System.out.println("c. Get account's full name");
        System.out.println("d. Get account's phone number");
        System.out.println("e. Get account's address");
        System.out.println("f. Get account's country");
        System.out.println("g. Go back\n");
        System.out.print("What would you like to query? ");
        char querySelection = scanner.next().charAt(0);
        while (querySelection != 'g') {
            switch (querySelection) {

                case 'a':
                    System.out.println("Your account's username is " + getUserName());
                    break;

                case 'b':
                    System.out.println("Your account's password is " + getPassword());
                    break;

                case 'c':
                    System.out.println("Your account's full name is " + getFullName());
                    break;

                case 'd':
                    System.out.println("Your account's phone number is " + getPhoneNumber());
                    break;

                case 'e':
                    System.out.println("Your account's address is " + getAddress());
                    break;

                case 'f':
                    System.out.println("Your account's country is " + getCountry());
                    break;

                default:
                    System.out.println("Invalid selection. Please try again!");
            }
            System.out.println();
            System.out.print("What would you like to query? ");
            querySelection = scanner.next().charAt(0);
        }
    }

    public void uploadPurchases(Scanner scanner) {
        System.out.println("\nEnter purchase details below\n");
        Purchase newPurchase = new Purchase(scanner, creditCard);
        creditCard.addPurchase(newPurchase);
    }

    public void getMinimumAndMaximumTransaction() {
        ArrayList<Purchase> purchases = creditCard.getPurchases();
        if (purchases.size() == 0) {
            System.out.println(creditCard + " does not have any purchase.");
        } else {
            double minimumAmount = purchases.get(0).getAmountPaid();
            Purchase minimumPurchase = purchases.get(0);
            double maximumAmount = purchases.get(0).getAmountPaid();
            Purchase maximumPurchase = purchases.get(0);
            for (int i = 1; i < purchases.size(); i++) {
                double amountPaid = purchases.get(i).getAmountPaid();
                if (amountPaid < minimumAmount) {
                    minimumAmount = amountPaid;
                    minimumPurchase = purchases.get(i);
                }

                if (amountPaid > maximumAmount) {
                    maximumAmount = amountPaid;
                    maximumPurchase = purchases.get(i);
                }
            }
            System.out.println("\nMinimum Transaction:\n" + minimumPurchase);
            System.out.println("\nMaximum Transaction: \n" + maximumPurchase);
        }

    }

    public void getAmountDue() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        double amountDue = creditCard.getBalance() - creditCard.getPaidAmount();
        System.out.println("Your " + creditCard + " due balance is: " + currency.format(amountDue));
    }

    public void getTotalAmountPaid() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        System.out.println(
                "Your " + creditCard + " total paid amount is: " + currency.format(creditCard.getPaidAmount()));
    }

    public void payCard(Scanner scanner) {
        double newPayAmount = 0;
        double amountDue = creditCard.getBalance();
        double totalPaidAmount = creditCard.getPaidAmount();
        double remainingDue = amountDue - totalPaidAmount;
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        if (remainingDue == 0) {
            creditCard.resetBalance();
            System.out.println("Denied! The current due amount is " + currency.format(remainingDue));
        }

        else if (remainingDue < 10) { // Due is small
            System.out.println("The due amount is " + currency.format(remainingDue));
            System.out.print("Do you want to pay all of it? ");
            String reply = scanner.next();
            if (reply.equalsIgnoreCase("yes")) {
                newPayAmount = remainingDue;
                System.out.println("Success! You paid " + currency.format(newPayAmount));
                System.out.println("Remaining amount due is: " + currency.format(remainingDue - newPayAmount));
                creditCard.addPaidAmount(newPayAmount);
            }
        }

        else {
            System.out.print("How much do you want to pay? ");
            String payAmountInput = scanner.next();
            while (true) {
                try {
                    newPayAmount = Double.parseDouble(payAmountInput);
                    if (Double.compare(newPayAmount, remainingDue) > 0) {
                        System.out.println("The due amount is " + currency.format(remainingDue));
                        System.out.println("You are overpaying! Please try again.\n");
                        System.out.print("How much do you want to pay? ");
                        payAmountInput = scanner.next();
                    } else {
                        System.out.println("Success! You paid " + currency.format(newPayAmount));
                        creditCard.addPaidAmount(newPayAmount);
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(payAmountInput + " is not a number. Please try again\n");
                    System.out.print("How much do you want to pay? ");
                    payAmountInput = scanner.next();
                }
            }
        }

    }

    public void displayPurchasesMade() {
        ArrayList<Purchase> purchases = creditCard.getPurchases();
        System.out.println("\nList of purchases\n");
        if (purchases.size() == 0) {
            System.out.println(creditCard + " does not have any purchase.");
        } else {
            purchases.sort(Comparator.comparing(Purchase::getDate));
            for (Purchase purchase : purchases) {
                System.out.println(purchase);
                System.out.println();
            }
        }
    }
}