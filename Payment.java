import java.text.NumberFormat;
import java.time.LocalDate;

public class Payment {
    private Card card;
    private LocalDate date;
    private double amount;

    public Payment(Card creditCard, LocalDate paymentDate, double paidAmount) {
        this.card = creditCard;
        this.date = paymentDate;
        this.amount = paidAmount;
    }

    public String toString() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return "Paid to Card: " + card + "\n"
                    + "Payment Date: " + date + "\n"
                    + "Payment Amount: " + currency.format(amount) + "\n";
    }
}
