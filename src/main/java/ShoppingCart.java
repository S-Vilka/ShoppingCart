import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ShoppingCart {

    // Method to calculate cost of each item (price × quantity)
    public static double calculateItemCost(double unitPrice, int unitAmount) {
        return unitPrice * unitAmount;
    }

    // Method to calculate the total cost of the shopping cart
    public static double calculateTotalCost(int shoppingItems, ResourceBundle rb, Scanner scanner) {
        double cartTotalCost = 0;

        for (int i = 1; i <= shoppingItems; i++) {
            System.out.println(rb.getString("item") + " " + i);

            System.out.print(rb.getString("unitPrice"));
            double unitPrice = scanner.nextDouble();

            System.out.print(rb.getString("quantity"));
            int unitAmount = scanner.nextInt();

            // Calculate total cost of the current item
            double totalItemCost = calculateItemCost(unitPrice, unitAmount);
            cartTotalCost += totalItemCost;
        }

        return cartTotalCost;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Language selection menu
        System.out.println("Select language:");
        System.out.println("1. English");
        System.out.println("2. Finnish");
        System.out.println("3. Swedish");
        System.out.println("4. Japanese");
        System.out.println();

        int choice = scanner.nextInt();
        Locale locale;

        switch (choice) {
            case 1:
                locale = new Locale("en", "US");
                break;
            case 2:
                locale = new Locale("fi", "FI");
                break;
            case 3:
                locale = new Locale("sv", "SE");
                break;
            case 4:
                locale = new Locale("ja", "JP");
                break;
            default:
                System.out.println("Invalid selection. Defaulting to English.");
                locale = new Locale("en", "US");
                break;
        }

        ResourceBundle rb;
        try {
            rb = ResourceBundle.getBundle("messages", locale);
        } catch (MissingResourceException e) {
            System.err.println("Error: Language resource file not found. Defaulting to English.");
            rb = ResourceBundle.getBundle("messages", new Locale("en", "US"));
        }

        // Ask user the number of different items they want to purchase
        System.out.print(rb.getString("numberOfItems"));
        int shoppingItems = scanner.nextInt();

        // Calculate total cost using the new method
        double cartTotalCost = calculateTotalCost(shoppingItems, rb, scanner);

        // Display the total cost
        System.out.printf(rb.getString("total"), cartTotalCost);

        scanner.close();
    }
}