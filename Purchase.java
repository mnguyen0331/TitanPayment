import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.text.NumberFormat;
import java.util.Scanner;

public class Purchase {

    final float CONVENIENT_FEE = 0.002f;

    private String name;
    private LocalDate date;
    private Card cardUse;
    private double amountPaid;
    private double amountPaidUsingCard;
    private double convinientAmount;
    private String billingCycle;
    private String status;

    public Purchase(Scanner scanner, Card userCard) {

        System.out.print("Enter purchase name: ");
        name = scanner.nextLine();
        scanner.nextLine();

        date = getDateFromInput(scanner);

        billingCycle = fitDateIntoBillingCycle(date.getMonthValue(), date.getYear());

        cardUse = userCard;

        System.out.print("Amount paid: ");
        amountPaid = scanner.nextDouble();
        amountPaidUsingCard = amountPaid + amountPaid * userCard.TRANSACTION_FEE;
        convinientAmount = amountPaid * CONVENIENT_FEE;

        status = "Due";
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public double getAmountPaidUsingCard() {
        return amountPaidUsingCard;
    }

    public double getConvenientAmount() {
        return convinientAmount;
    }

    public void changeStatus() {
        status = "Paid";
    }

    public String toString() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return "Purchase name: " + name
                + "Date: " + date.format(format) + "\n"
                + "Purchase card: " + cardUse + "\n"
                + "Amount paid: " + currency.format(amountPaid) + "\n"
                + "Amount paid using card: " + currency.format(amountPaidUsingCard) + "\n"
                + "Convenient amount: " + currency.format(convinientAmount) + "\n"
                + "Billing cycle: " + billingCycle + "\n"
                + "Status: " + status;
    }

    private LocalDate getDateFromInput(Scanner scanner) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.print("Date (Entered as MM/dd/yyyy): ");
        String dateInput = scanner.next();
        LocalDate date = null;
        while (true) {
            try {
                date = LocalDate.parse(dateInput, format);
                break;

            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date. Please try again\n");
                System.out.print("Date (Entered as MM/dd/yyyy): ");
                dateInput = scanner.next();
            }
        }

        return date;

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
