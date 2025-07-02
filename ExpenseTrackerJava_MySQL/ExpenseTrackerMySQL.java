import java.sql.*;
import java.util.*;

public class ExpenseTracker {
    private Scanner sc = new Scanner(System.in);

    public void addExpense() {
        try (Connection conn = DBUtil.getConnection()) {
            System.out.print("Date (yyyy-mm-dd): ");
            String date = sc.next();
            System.out.print("Category: ");
            String category = sc.next();
            System.out.print("Amount: Rs.");
            double amount = sc.nextDouble();
            sc.nextLine(); // consume newline
            System.out.print("Note: ");
            String note = sc.nextLine();

            String sql = "INSERT INTO expenses (date, category, amount, note) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, date);
            stmt.setString(2, category);
            stmt.setDouble(3, amount);
            stmt.setString(4, note);
            stmt.executeUpdate();

            System.out.println("Expense added to database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewExpenses() {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM expenses";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getDate("date") + ", " +
                        rs.getString("category") + ", Rs." +
                        rs.getDouble("amount") + ", Note: " +
                        rs.getString("note"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalSpent() {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT SUM(amount) AS total FROM expenses";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Total Spent: Rs." + rs.getDouble("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterByCategory() {
        try (Connection conn = DBUtil.getConnection()) {
            System.out.print("Enter category to filter: ");
            String category = sc.next();
            String sql = "SELECT * FROM expenses WHERE category = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getDate("date") + ", " +
                        rs.getString("category") + ", Rs." +
                        rs.getDouble("amount") + ", Note: " +
                        rs.getString("note"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Expense\n2. View Expenses\n3. Total Spent\n4. Filter by Category\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> tracker.addExpense();
                case 2 -> tracker.viewExpenses();
                case 3 -> tracker.totalSpent();
                case 4 -> tracker.filterByCategory();
                case 5 -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
