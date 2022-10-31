import java.util.Scanner;
import java.time.LocalDate;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Helper {
    public static double getPositiveDouble(Scanner scanner) {
        double result = 0;
        System.out.print("Paid Amount: ");
        String userInput = scanner.nextLine();
        while (true) {
            try {
                result = Double.parseDouble(userInput);
                if (result < 0)
                    System.out.println("Paid Amount cannot be negative.");
                else
                    break;
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not a number. Please try again.");
            }
            System.out.print("Paid Amount: ");
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

    public static void wait(int miliSecond) {
        System.out.println("System Processing ..........");
        try {
            Thread.sleep(miliSecond);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static LocalDate getDateFromInput(Scanner scanner) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.print("Date (Entered as MM/dd/yyyy): ");
        String dateInput = scanner.nextLine();
        LocalDate date = null;
        while (true) {
            try {
                date = LocalDate.parse(dateInput, format);
                break;

            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date. Please try again\n");
                System.out.print("Date (Entered as MM/dd/yyyy): ");
                dateInput = scanner.nextLine();
            }
        }

        return date;

    }

    public static boolean areDatesCorrect(LocalDate from, LocalDate to, LocalDate max) {
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

    public static void printPurchase(Purchase p, Card cardName) {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        System.out.printf("| %20s | %-10s | %-8s | %-12s | %-12s | %-12s | %-14s | %-6s |%n",
                p.getName(), formatDate(p.getDate()), cardName, currency.format(p.getAmountPaid()),
                currency.format(p.getAmountPaidUsingCard()),
                currency.format(p.getConvenientAmount()), p.getBillingCycle(), p.getStatus());
    }

    public static void printPurchaseTitle() {
        printDash();
        System.out.printf("| %-20s | %-10s | %-8s | %-12s | %-12s | %-12s | %-14s | %-6s |%n",
                "NAME", "DATE", "CARD", "AMOUNT", "TOTAL AMOUNT", "FEE", "BILLING CYCLE", "STATUS");
        printDash();
    }

    public static void printDash() {
        System.out.printf(
                "-----------------------------------------------------------------------------------------------------------------------%n");
    }
}
