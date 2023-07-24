public class Money {
    private int amount;
    private String currency;



    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    @Override
    public String toString() {
        return "Money [amount=" + amount + ", currency=" + currency + "]";
    }



}
