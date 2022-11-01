/*
 * Author: Phu Nguyen
 * Date: 10/31/2022
 * Project: Titan Payment System
 * Course: CPSC335-07 22473
 */

import java.time.LocalDateTime;

public class Payment {
    private Card card;
    private LocalDateTime dateTime;
    private double amount;
    private String billingCycle;

    public Payment(Card creditCard, LocalDateTime paymentDateTime, double paidAmount, String billingCycle) {
        this.card = creditCard;
        this.dateTime = paymentDateTime;
        this.amount = paidAmount;
        this.billingCycle = billingCycle;
    }

    public double getPaidAmount() {
        return amount;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public Card getPaidcard() {
        return card;
    }

    public LocalDateTime getPaidDateTime() {
        return dateTime;
    }
}
