import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private Card card;
    private LocalDateTime dateTime;
    private double amount;

    public Payment(Card creditCard, LocalDateTime paymentDateTime, double paidAmount) {
        this.card = creditCard;
        this.dateTime = paymentDateTime;
        this.amount = paidAmount;
    }

    public String toString() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return "Paid to Card: " + card + "\n"
                + "Payment Date: " + dateTime.format(format) + "\n"
                + "Payment Amount: " + currency.format(amount) + "\n";
    }
}
