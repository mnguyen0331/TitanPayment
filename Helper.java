/*
 * Author: Phu Nguyen
 * Date: 10/31/2022
 * Project: Titan Payment System
 * Course: CPSC335-07 22473
 */

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Helper {
    public static double getPositiveDouble(Scanner scanner, String label) {
        double result = 0;
        System.out.print(label + ": ");
        String userInput = scanner.nextLine();
        while (true) {
            try {
                result = Double.parseDouble(userInput);
                if (result < 0)
                    System.out.println(label + " cannot be negative.");
                else
                    break;
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not a number. Please try again.");
            }
            System.out.print(label + ": ");
            userInput = scanner.nextLine();
        }
        return result;
    }

    public static int getIntFromUser(Scanner scanner) {
        int result = -1;
        System.out.print("Pick an acion from the menu: ");
        String userInput = scanner.nextLine();
        while (true) {
            try {
                result = Integer.parseInt(userInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not an integer. Please try again\n");
                System.out.print("Pick an acion from the menu: ");
                userInput = scanner.nextLine();
            }
        }
        return result;
    }

    public static void wait(int miliSecond, String msg) { // Stop the program for a set amount of miliseconds
        System.out.println("\n" + msg + "\n");
        try {
            Thread.sleep(miliSecond);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static LocalDate getDateFromInput(Scanner scanner, String label) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.print(label + " (MM/dd/yyyy): ");
        String dateInput = scanner.nextLine();
        LocalDate date = null;
        while (true) {
            try {
                date = LocalDate.parse(dateInput, format);
                break;

            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date. Please try again\n");
                System.out.print(label + " (MM/dd/yyyy): ");
                dateInput = scanner.nextLine();
            }
        }

        return date;

    }

    public static boolean areDatesCorrect(LocalDate from, LocalDate to, LocalDate max) { // validate Date inputs
        if (from.isAfter(to))
            return false;
        else if (from.isAfter(max))
            return false;
        else
            return true;
    }

    public static String formatDate(LocalDate date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(format);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return dateTime.format(format);
    }

    public static void printPurchase(Purchase p, Card cardName) {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        System.out.printf("| %-20s | %-10s | %-11s | %-12s | %-12s | %-12s | %-14s | %-6s |%n",
                p.getName(), formatDate(p.getDate()), cardName, currency.format(p.getPrice()),
                currency.format(p.getFee()),
                currency.format(p.getAmountPaidUsingCard()),
                p.getBillingCycle(), p.getStatus());
    }

    public static void printPurchaseTitle() {
        printDash(122);
        System.out.printf("| %-20s | %-10s | %-11s | %-12s | %-12s | %-12s | %-14s | %-6s |%n",
                "NAME", "DATE", "CREDIT CARD", "PRICE", "FEE", "TOTAL", "BILLING CYCLE", "STATUS");
        printDash(122);
    }

    public static void printDash(int numRepeat) {
        System.out.println("-".repeat(numRepeat));
    }

    public static void printPayment(Payment p) {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        System.out.printf("| %-19s | %-14s | %-8s | %-12s |%n", formatDateTime(p.getPaidDateTime()),
                p.getBillingCycle(),
                p.getPaidcard(), currency.format(p.getPaidAmount()));
    }

    public static void printPaymentTitle() {
        printDash(66);
        System.out.printf("| %-19s | %-14s | %-8s | %-12s |%n", "DATE", "BILLING CYCLE", "CARD", "PAID AMOUNT");
        printDash(66);
    }
}
