/*
 * Author: Phu Nguyen
 * Date: 10/31/2022
 * Project: Titan Payment System
 * Course: CPSC335-07 22473
 */

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        userName = userScanner.next().trim();
        while (wasUserNameExisted(userName)) {
            System.out.println("Denied. Account \"" + userName + "\" already exists!");
            System.out.print("Enter a different user name: ");
            userName = userScanner.next().trim();
        }

        System.out.print("Enter your password: ");
        password = userScanner.next().trim();
        userScanner.nextLine(); // consume a "\n" after next()

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
        String countryInput = userScanner.nextLine();
        country = countryInput.trim();

        creditCard = Card.getCardType(userScanner);

        users.put(userName, this);

        Helper.wait(1000, "System Processing ..................................");

        System.out.println("Successfully created account \"" + userName + "\"");
    }

    private boolean wasUserNameExisted(String userName) {
        if (users.size() == 0)
            return false;
        return users.containsKey(userName);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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
        char querySelection = scanner.nextLine().charAt(0);
        while (querySelection != 'g') {
            switch (querySelection) {

                case 'a':
                    System.out.println("Your account's username is: " + getUserName());
                    break;

                case 'b':
                    System.out.println("Your account's password is: " + getPassword());
                    break;

                case 'c':
                    System.out.println("Your account's full name is: " + fullName);
                    break;

                case 'd':
                    System.out.println("Your account's phone number is: " + phoneNumber);
                    break;

                case 'e':
                    System.out.println("Your account's address is: " + address);
                    break;

                case 'f':
                    System.out.println("Your account's country is: " + country);
                    break;

                default:
                    System.out.println("Invalid selection. Please try again!");
            }
            System.out.println();
            System.out.print("What would you like to query? ");
            querySelection = scanner.nextLine().charAt(0);
        }
    }

    public void uploadPurchases(Scanner scanner) {
        System.out.println("\nEnter purchase details below\n");
        Purchase newPurchase = new Purchase(scanner, creditCard);
        creditCard.addPurchase(newPurchase);
    }

    public void getMinimumAndMaximumTransaction() { // O(1) or O(nlogn)
        ArrayList<Purchase> purchases = creditCard.getPurchases();
        if (purchases.size() == 0) {
            System.out.println(creditCard + " does not have any transaction.");
        } else {
            purchases.sort(Comparator.comparingDouble(Purchase::getAmountPaidUsingCard)); // Merge sort on totalAmount
            Purchase minimumPurchase = purchases.get(0);
            Purchase maximumPurchase = purchases.get(purchases.size() - 1);
            System.out.println("Minimum Transaction:");
            Helper.printPurchaseTitle();
            Helper.printPurchase(minimumPurchase, creditCard);
            System.out.println("\nMaximum Transaction:");
            Helper.printPurchaseTitle();
            Helper.printPurchase(maximumPurchase, creditCard);
        }

    }

    public void getAmountDue(Scanner scanner) { // O(1) or O(n)
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        HashMap<String, Double> dueCycles = creditCard.getDueCycles();
        if (dueCycles.size() == 0) {
            System.out.println("\nNo bill present or all bills were paid.");
            return;
        }
        System.out.print("Enter billing cycle to view due amount (leave blank to view all): ");
        String billingCycle = scanner.nextLine().trim();
        Helper.wait(1000, "System Processing ..................................");
        if (billingCycle == "") {
            for (String billCycle : dueCycles.keySet()) { // Loop through each key O(n)
                double dueAmount = dueCycles.get(billCycle); // get amount from each key
                System.out.println(billCycle + ": " + currency.format(dueAmount));
            }
        } else if (!dueCycles.containsKey(billingCycle)) { // O(1)
            System.out.println("Error. " + billingCycle + " bill does not exist");
            System.out.println("All available cycles are: " + dueCycles.keySet());
        } else { // O(1)
            System.out.println(
                    "The due amount for " + billingCycle + " is: " + currency.format(dueCycles.get(billingCycle)));
        }
    }

    public void getTotalAmountPaid(Scanner scanner) { // Similar to getAmountDue() O(1) or O(n)
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        HashMap<String, Double> paidCycles = creditCard.getPaidCycles();
        if (paidCycles.size() == 0) {
            System.out.println("\nNo paid amount to view.");
            return;
        }
        System.out.print("Enter billing cycle to view paid amount (leave blank to view all): ");
        String billingCycle = scanner.nextLine().trim();
        Helper.wait(1000, "System Processing ..................................");
        if (billingCycle == "") {
            for (String billCycle : paidCycles.keySet()) {
                double paidAmount = paidCycles.get(billCycle);
                System.out.println(billCycle + ": " + currency.format(paidAmount));
            }
        } else if (!paidCycles.containsKey(billingCycle)) {
            System.out.println("Error. " + billingCycle + " payment does not exist");
            System.out.println("All available cycles are: " + paidCycles.keySet());
        } else {
            System.out.println(
                    "The paid amount for " + billingCycle + " is: " + currency.format(paidCycles.get(billingCycle)));
        }
    }

    public void payCard(Scanner scanner) { // O(1)
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        HashMap<String, Double> dueCycles = creditCard.getDueCycles();
        if (dueCycles.size() == 0) { // O(1)
            System.out.println("\nThere is no bill to pay.");
            return;
        }

        System.out.print("Enter the billing cycle you want to pay: ");
        String billingCycle = scanner.nextLine().trim();
        Helper.wait(1000, "System Processing ..................................");
        if (!dueCycles.containsKey(billingCycle)) { // O(1)
            System.out.println("Error. " + billingCycle + " bill does not exist or was already paid.");
            System.out.println("All due bills are: " + dueCycles.keySet());
            return;
        } else { // O(1)
            LocalDateTime paymentDateTime = LocalDateTime.now(); // current date and time of local machine
            double dueAmount = dueCycles.get(billingCycle);
            double paidAmount = 0;
            System.out.println("The due amount for " + billingCycle + " is " + currency.format(dueAmount));
            System.out.print("Do you want to pay all of it (yes or no)?  ");
            String reply = scanner.nextLine();
            if (reply.equalsIgnoreCase("yes")) // paid all
                paidAmount = dueAmount;
            else { // paid partially
                paidAmount = Helper.getPositiveDouble(scanner);
                if (Double.compare(paidAmount, dueAmount) > 0) {
                    System.out.println("You are overpaying! Please try again.");
                    return;
                }
            }
            System.out.println("Approved! " + "Your " + creditCard + " was credited " + currency.format(paidAmount));
            Payment newPayment = new Payment(creditCard, paymentDateTime, paidAmount, billingCycle);
            creditCard.addPayment(newPayment);
        }
    }

    public void getPaymentHistory() { // O(1) or O(nlogn)
        ArrayList<Payment> payments = creditCard.getPayments();
        if (payments.size() == 0)
            System.out.println("There is no payment to display");
        else { // O(nlogn) + O(n)
            payments.sort(Comparator.comparing(Payment::getPaidDateTime)); // Merge sort on dateTime field
            Helper.printPaymentTitle();
            for (Payment payment : payments) { // O(n)
                Helper.printPayment(payment);
            }
            Helper.printDash(66);
        }
    }

    public void displayPurchasesMade(Scanner scanner) { // O(1) or O(nlogn)
        ArrayList<Purchase> purchases = creditCard.getPurchases();
        if (purchases.size() == 0) { // O(1)
            System.out.println("\n" + creditCard + " does not have any purchase.");
        } else { // O(nlogn) + O(n)
            purchases.sort(Comparator.comparing(Purchase::getDate)); // Merge sort on date, O(nlogn)
            System.out.print("Do you want to display all purchases made (yes or no)? ");
            String reply = scanner.nextLine();
            if (reply.equalsIgnoreCase("yes")) {
                Helper.wait(1000, "System Processing ..................................");
                Helper.printPurchaseTitle();
                for (Purchase p : purchases) // O(n)
                    Helper.printPurchase(p, creditCard);
            }

            else { // O(n)
                int purchasesSize = purchases.size();
                LocalDate latestDate = purchases.get(purchasesSize - 1).getDate();
                System.out.println("The latest date is " + Helper.formatDate(latestDate));
                System.out.println("Enter the appropriate range of dates to view purchases:");
                LocalDate dateFrom = Helper.getDateFromInput(scanner);
                LocalDate dateTo = Helper.getDateFromInput(scanner);
                while (!Helper.areDatesCorrect(dateFrom, dateTo, latestDate)) {
                    System.out.println("Error! The latest date is " + Helper.formatDate(latestDate));
                    System.out.println("Enter the appropriate range of dates to view purchases:");
                    dateFrom = Helper.getDateFromInput(scanner);
                    dateTo = Helper.getDateFromInput(scanner);
                }
                Helper.wait(1000, "System Processing ..................................");
                Helper.printPurchaseTitle();
                int startIndex = -1;// index of date that is later or equal to dateFrom
                int endIndex = -1; // index of date that is sooner or equal to dateTo
                int i = 0;
                int j = purchases.size() - 1;
                while (i <= j) { // O(n)
                    LocalDate lookUpStartDate = purchases.get(i).getDate();
                    LocalDate lookUpEndDate = purchases.get(j).getDate();

                    if (lookUpStartDate.compareTo(dateFrom) >= 0)
                        startIndex = i;
                    else
                        i++;

                    if (lookUpEndDate.compareTo(dateTo) <= 0)
                        endIndex = j;
                    else
                        j--;

                    if (startIndex != -1 && endIndex != -1)
                        break;
                }

                List<Purchase> sub = purchases.subList(startIndex, endIndex + 1); // [startIndex, // endIndex) // O(1)
                for (Purchase p : sub) // O(n)
                    Helper.printPurchase(p, creditCard);
            }
            Helper.printDash(119);
        }
    }
}
