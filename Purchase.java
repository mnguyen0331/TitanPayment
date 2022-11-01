/*
 * Author: Phu Nguyen
 * Date: 10/31/2022
 * Project: Titan Payment System
 * Course: CPSC335-07 22473
 */

import java.time.LocalDate;
import java.util.Scanner;

public class Purchase {

    final float CONVENIENT_FEE = 0.002f;

    private String name;
    private LocalDate date;
    private Card cardUse;
    private double price;
    private double amountPaidUsingCard;
    private double fee;
    private String billingCycle;
    private String status;

    public Purchase(Scanner scanner, Card userCard) {

        System.out.print("Purchase name: ");
        name = scanner.nextLine();

        date = Helper.getDateFromInput(scanner, "Purchase date");

        billingCycle = fitDateIntoBillingCycle(date.getMonthValue(), date.getYear());

        cardUse = userCard;

        price = Helper.getPositiveDouble(scanner, "Purchase price: ");
        fee = price * (CONVENIENT_FEE + userCard.TRANSACTION_FEE);
        amountPaidUsingCard = price + fee;
        amountPaidUsingCard = Math.round(amountPaidUsingCard * 100.0) / 100.0; // Round up to two decimal places

        status = "Due";
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Card getCard() {
        return cardUse;
    }

    public double getPrice() {
        return price;
    }

    public double getAmountPaidUsingCard() {
        return amountPaidUsingCard;
    }

    public double getFee() {
        return fee;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public String getStatus() {
        return status;
    }

    public void changeStatus() {
        status = "Paid";
    }

    private String fitDateIntoBillingCycle(int month, int year) {
        String result = "";
        switch (month) {
            case 1:
                result = "January " + year;
                break;

            case 2:
                result = "February " + year;
                break;

            case 3:
                result = "March " + year;
                break;

            case 4:
                result = "April " + year;
                break;

            case 5:
                result = "May " + year;
                break;

            case 6:
                result = "June " + year;
                break;

            case 7:
                result = "July " + year;
                break;

            case 8:
                result = "August " + year;
                break;

            case 9:
                result = "September " + year;
                break;

            case 10:
                result = "October " + year;
                break;

            case 11:
                result = "November " + year;
                break;

            case 12:
                result = "December " + year;
        }

        return result;
    }
}
