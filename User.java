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
            System.out.println("Denied. Account \"" + userName + "\"already exists!");
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
                    System.out.println("Your account's username is " + getUserName());
                    break;

                case 'b':
                    System.out.println("Your account's password is " + getPassword());
                    break;

                case 'c':
                    System.out.println("Your account's full name is " + fullName);
                    break;

                case 'd':
                    System.out.println("Your account's phone number is " + phoneNumber);
                    break;

                case 'e':
                    System.out.println("Your account's address is " + address);
                    break;

                case 'f':
                    System.out.println("Your account's country is " + country);
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

    public void getMinimumAndMaximumTransaction() {
        ArrayList<Purchase> purchases = creditCard.getPurchases();
        if (purchases.size() == 0) {
            System.out.println(creditCard + " does not have any transaction.");
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
            System.out.println("\nMinimum Transaction:");
            Helper.printPurchaseTitle();
            Helper.printPurchase(minimumPurchase, creditCard);
            System.out.println("\nMaximum Transaction:");
            Helper.printPurchaseTitle();
            Helper.printPurchase(maximumPurchase, creditCard);
        }

    }

    public void getAmountDue(Scanner scanner) {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        HashMap<String, Double> dueCycles = creditCard.getDueCycles();
        if (dueCycles.size() == 0) {
            System.out.println("No data to display");
            return;
        }
        System.out.print("Enter billing cycle to view due amount (enter to view all): ");
        String billingCycle = scanner.nextLine().trim();
        if (billingCycle == "") {
            for (String billCycle : dueCycles.keySet()) {
                double value = dueCycles.get(billCycle);
                System.out.println(billCycle + ": " + currency.format(value));
            }
        } else if (!dueCycles.containsKey(billingCycle)) {
            System.out.println(billingCycle + " bill does not exist");
            System.out.println("All available cycles are: " + dueCycles.keySet());
        } else {
            System.out.println(
                    "The due amount for " + billingCycle + " is: " + currency.format(dueCycles.get(billingCycle)));
        }

        // HashMap<String, ArrayList<Purchase>> purchaseBillingCycles =
        // creditCard.getPurchaseBillingCycles();
        // if (!purchaseBillingCycles.containsKey(billingCycle)) {
        // System.out.println(billingCycle + " bill does not exist!");
        // System.out.println("All available cycles are: " +
        // purchaseBillingCycles.keySet());
        // return;
        // }
        // ArrayList<Purchase> purchases = purchaseBillingCycles.get(billingCycle);
        // double dueAmount = 0;
        // for (Purchase p : purchases)
        // dueAmount += p.getAmountPaidUsingCard();
        // System.out.println("The due amount for " + billingCycle + " is: " +
        // currency.format(dueAmount));
    }

    public void getTotalAmountPaid() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        System.out.println(
                "Your " + creditCard + " total paid amount is: " + currency.format(creditCard.getPaidAmount()));
    }

    public void payCard(Scanner scanner) {
        double amountDue = creditCard.getBalance();
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        if (amountDue == 0) { // Not allow to pay
            System.out.println("Denied! The current due amount is " + currency.format(amountDue));
            return;
        }
        LocalDateTime paymentDateTime = LocalDateTime.now();
        double newPaidAmount = 0;
        System.out.println("The due amount is " + currency.format(amountDue));
        System.out.print("Do you want to pay all of it? (yes or no) ");
        String reply = scanner.nextLine();
        if (reply.equalsIgnoreCase("yes")) // Option to pay all
            newPaidAmount = amountDue;
        else {
            newPaidAmount = Helper.getPositiveDouble(scanner);
            if (Double.compare(newPaidAmount, amountDue) > 0) {
                System.out.println("You are overpaying! Please try again.");
                return;
            }
        }
        System.out.println("Success! " + "Your " + creditCard + "was credited " + currency.format(newPaidAmount));
        Payment newPayment = new Payment(creditCard, paymentDateTime, newPaidAmount);
        creditCard.addPayment(newPayment);
    }

    public void getPaymentHistory() {
        ArrayList<Payment> payments = creditCard.getPayments();
        if (payments.size() == 0)
            System.out.println("\nThere is no payment to display");
        else {
            System.out.println(payments);
        }
    }

    public void displayPurchasesMade(Scanner scanner) {
        ArrayList<Purchase> purchases = creditCard.getPurchases();
        if (purchases.size() == 0) {
            System.out.println(creditCard + " does not have any purchase.");
        } else {
            purchases.sort(Comparator.comparing(Purchase::getDate));
            System.out.print("Do you want to display all purchases made? (yes or no): ");
            String reply = scanner.nextLine();
            if (reply.equalsIgnoreCase("yes")) {
                Helper.wait(1000);
                Helper.printPurchaseTitle();
                for (Purchase p : purchases)
                    Helper.printPurchase(p, creditCard);
            }

            else {
                int purchasesSize = purchases.size();
                LocalDate latestDate = purchases.get(purchasesSize - 1).getDate();
                System.out.println("The latest date is " + Helper.formatDate(latestDate));
                System.out.println("Enter the appropriate range of dates to view purchases:");
                LocalDate dateFrom = Helper.getDateFromInput(scanner);
                LocalDate dateTo = Helper.getDateFromInput(scanner);
                while (!Helper.areDatesCorrect(dateFrom, dateTo, latestDate)) {
                    System.out.println("The latest date is " + Helper.formatDate(latestDate));
                    System.out.println("Enter the appropriate range of dates to view purchases:");
                    dateFrom = Helper.getDateFromInput(scanner);
                    dateTo = Helper.getDateFromInput(scanner);
                }
                Helper.wait(1000);
                Helper.printPurchaseTitle();
                int startIndex = -1;// index of date that is later or equal to dateFrom
                int endIndex = -1; // index of date that is sooner or equal to dateTo
                int i = 0;
                int j = purchases.size() - 1;
                while (i <= j) {
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

                List<Purchase> sub = purchases.subList(startIndex, endIndex + 1); // [startIndex, // endIndex)
                for (Purchase p : sub)
                    Helper.printPurchase(p, creditCard);
            }
            Helper.printDash();
        }
    }
}
