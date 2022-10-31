import java.util.ArrayList;
import java.util.Scanner;

public enum Card {
    AMEX(0.008f),
    VISA(0.01f),
    DISCOVER(0.005f);

    final float TRANSACTION_FEE;

    private double balance;
    private double paidAmount;
    private ArrayList<Purchase> purchases = new ArrayList<>();
    private ArrayList<Payment> payments = new ArrayList<Payment>();

    Card(float transactionFee) {
        this.TRANSACTION_FEE = transactionFee;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void addPayment(Payment newPayment) {
        payments.add(newPayment);
        paidAmount += newPayment.getPaidAmount();
        balance -= newPayment.getPaidAmount();
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

    public void resetPaidAmount() {
        paidAmount = 0;
    }

    public static Card getCardType(Scanner scanner) {
        Card card;
        System.out.print("Enter credit card type: ");
        String cardName = scanner.nextLine().trim().toUpperCase();
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
                System.out.println("\nInvalid card type. Please try again\n");
                System.out.print("Enter credit card type: ");
                cardName = scanner.nextLine().trim().toUpperCase();
            }
        }
        return card;
    }
}
