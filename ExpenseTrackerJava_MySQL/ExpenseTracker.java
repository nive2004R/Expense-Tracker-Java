import java.util.*;
import java.io.*;

public class ExpenseTracker {
    private List<Expense> expenses = new ArrayList<>();
    private static final String FILE_NAME = "expenses.txt";

    public void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    expenses.add(new Expense(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous expenses found.");
        }
    }

    public void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.write(e.getDate() + "," + e.getCategory() + "," + e.getAmount() + "," + e.getNote());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses.");
        }
    }

    public void addExpense(Scanner sc) {
        System.out.print("Date (yyyy-mm-dd): ");
        String date = sc.next();
        System.out.print("Category: ");
        String category = sc.next();
        System.out.print("Amount: Rs.");
        double amount = sc.nextDouble();
        sc.nextLine(); // consume newline
        System.out.print("Note: ");
        String note = sc.nextLine();

        Expense e = new Expense(date, category, amount, note);
        expenses.add(e);
        System.out.println("Expense added successfully.");
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to show.");
        } else {
            for (Expense e : expenses) {
                System.out.println(e);
            }
        }
    }

    public void totalSpent() {
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.println("Total Spent: Rs." + total);
    }

    public void filterByCategory(Scanner sc) {
        System.out.print("Enter category to filter: ");
        String cat = sc.next();
        expenses.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(cat))
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        Scanner sc = new Scanner(System.in);
        tracker.loadExpenses();

        while (true) {
            System.out.println("\n1. Add Expense\n2. View Expenses\n3. Total Spent\n4. Filter by Category\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> tracker.addExpense(sc);
                case 2 -> tracker.viewExpenses();
                case 3 -> tracker.totalSpent();
                case 4 -> tracker.filterByCategory(sc);
                case 5 -> {
                    tracker.saveExpenses();
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
