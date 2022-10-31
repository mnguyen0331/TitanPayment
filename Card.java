/*
 * Author: Phu Nguyen
 * Date: 10/31/2022
 * Project: Titan Payment System
 * Course: CPSC335-07 22473
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public enum Card {
    AMEX(0.008f),
    VISA(0.01f),
    DISCOVER(0.005f);

    final float TRANSACTION_FEE;

    private HashMap<String, Double> dueCycles = new HashMap<>(); // faster key-value access, not ordered
    private HashMap<String, Double> paidCycles = new HashMap<>(); // store due or paid according to billing cycle
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

    public HashMap<String, Double> getDueCycles() {
        return dueCycles;
    }

    public HashMap<String, Double> getPaidCycles() {
        return paidCycles;
    }

    public void addPayment(Payment newPayment) { // O(1)
        payments.add(newPayment);
        double paidAmount = newPayment.getPaidAmount();
        String billingCycle = newPayment.getBillingCycle();

        // add paid amount to billing cycle
        if (paidCycles.containsKey(billingCycle)) { // billing cycle exists
            double lastPayment = paidCycles.get(billingCycle);
            double currentPayment = lastPayment + paidAmount;
            paidCycles.replace(billingCycle, lastPayment, currentPayment);
        } else
            paidCycles.put(billingCycle, paidAmount);

        // subtract paid amount from previous due
        double lastDue = dueCycles.get(billingCycle);
        double currentDue = lastDue - paidAmount;
        dueCycles.replace(billingCycle, lastDue, currentDue);
        if (currentDue == 0) {
            dueCycles.remove(billingCycle); // remove $0 due bill from cycles
            for (Purchase p : purchases)
                if (p.getBillingCycle().equals(billingCycle))
                    p.changeStatus(); // Change to paid
        }
    }

    public void addPurchase(Purchase newPurchase) { // O(1) similar to addPayment
        purchases.add(newPurchase);
        double dueAmount = newPurchase.getAmountPaidUsingCard();
        String billingCycle = newPurchase.getBillingCycle();
        if (dueCycles.containsKey(billingCycle)) {
            double lastDue = dueCycles.get(billingCycle);
            double currentDue = lastDue + dueAmount;
            dueCycles.replace(billingCycle, lastDue, currentDue);
        } else
            dueCycles.put(billingCycle, dueAmount);
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
