public class Expense {
    private String date;
    private String category;
    private double amount;
    private String note;

    public Expense(String date, String category, double amount, String note) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.note = note;
    }

    public String getDate() { return date; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getNote() { return note; }

    @Override
    public String toString() {
        return date + ", " + category + ", Rs." + amount + ", Note: " + note;
    }
}
