import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartTest {

    private ResourceBundle rb;

    @BeforeEach
    void setUp() {
        rb = ResourceBundle.getBundle("messages", new Locale("en", "US"));
    }

    @Test
    void testCalculateItemCost() {
        assertEquals(20.0, ShoppingCart.calculateItemCost(10.0, 2), 0.01);
        assertEquals(0.0, ShoppingCart.calculateItemCost(5.0, 0), 0.01);
        assertEquals(100.0, ShoppingCart.calculateItemCost(25.0, 4), 0.01);
    }

    @Test
    void testCalculateTotalCost() {
        String input = "10\n2\n5\n3\n"; // Simulating user input: unitPrice, quantity, etc.
        Scanner scanner = new Scanner(input);

        double totalCost = ShoppingCart.calculateTotalCost(2, rb, scanner);

        assertEquals(35.0, totalCost, 0.01);
    }
}