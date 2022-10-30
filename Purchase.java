import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        scanner.nextLine();
        name = scanner.nextLine();

        date = Helper.getDateFromInput(scanner);

        billingCycle = fitDateIntoBillingCycle(date.getMonthValue(), date.getYear());

        cardUse = userCard;

        amountPaid = Helper.getPositiveDouble(scanner);
        amountPaidUsingCard = amountPaid + amountPaid * (userCard.TRANSACTION_FEE + CONVENIENT_FEE);
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

    public String getBillingCycle() {
        return billingCycle;
    }

    public void changeStatus() {
        status = "Paid";
    }

    public String toString() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return "\nPurchase name: " + name + "\n"
                + "Date: " + date.format(format) + "\n"
                + "Purchase card: " + cardUse + "\n"
                + "Amount paid: " + currency.format(amountPaid) + "\n"
                + "Amount paid using card: " + currency.format(amountPaidUsingCard) + "\n"
                + "Convenient amount: " + currency.format(convinientAmount) + "\n"
                + "Billing cycle: " + billingCycle + "\n"
                + "Status: " + status + "\n";
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
