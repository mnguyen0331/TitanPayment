import java.util.ArrayList;
import java.util.Scanner;

public enum Card {
    AMEX(0.008f),
    VISA(0.01f),
    DISCOVER(0.005f);

    final float TRANSACTION_FEE;
    private double balance;
    private double paidAmount;
    private ArrayList<Purchase> purchases = new ArrayList<Purchase>();

    Card(float transactionFee) {
        this.TRANSACTION_FEE = transactionFee;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public void addPurchase(Purchase newPurchase) {
        purchases.add(newPurchase);
        balance += newPurchase.getAmountPaidUsingCard();
    }

    public double getBalance() {
        return balance;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void resetBalance() { // when paidAmount == balance
        balance = balance - paidAmount;
        paidAmount = 0;
        for (Purchase purchase : purchases) {
            purchase.changeStatus();
        }
    }

    public void addPaidAmount(double amount) {
        paidAmount += amount;
    }

    public static Card getCardType(Scanner scanner) {
        Card card;
        System.out.print("What is the type of your credit card? ");
        String cardName = scanner.next().toUpperCase();
        while (true) {
            if (cardName.equals("AMEX")) {
                card = Card.AMEX;
                break;
            } else if (cardName.equals("VISA")) {
                card = Card.VISA;
                break;
            } else if (cardName.equals("DISCOVER")) {
                card = Card.DISCOVER;
                break;
            } else {
                System.out.println("\nInvalid card name. Please try again\n");
                System.out.print("What is the type of your credit card? ");
                cardName = scanner.next().toUpperCase();
            }
        }
        return card;
    }
}
