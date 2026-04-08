

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration Tests for Calculator class
 * These tests simulate real-world usage scenarios (chaining operations together).
 * File must end with "IntegrationTest" so CI pipeline picks it up separately.
 */
@DisplayName("Calculator Integration Tests")
public class CalculatorIntegrationTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Integration: (5 + 3) * 2 = 16")
    void testAddThenMultiply() {
        int sum = calculator.add(5, 3);        // 8
        int result = calculator.multiply(sum, 2); // 16
        assertEquals(16, result);
    }

    @Test
    @DisplayName("Integration: divide result of addition")
    void testAddThenDivide() {
        int sum = calculator.add(10, 10);       // 20
        double result = calculator.divide(sum, 4); // 5.0
        assertEquals(5.0, result);
    }

    @Test
    @DisplayName("Integration: factorial of addition result")
    void testAddThenFactorial() {
        int sum = calculator.add(2, 3);          // 5
        long result = calculator.factorial(sum); // 120
        assertEquals(120, result);
    }

    @Test
    @DisplayName("Integration: check if subtraction result is even")
    void testSubtractThenCheckEven() {
        int diff = calculator.subtract(10, 4);   // 6
        assertTrue(calculator.isEven(diff));     // 6 is even
    }

    @Test
    @DisplayName("Integration: full chain - add, subtract, multiply")
    void testFullChain() {
        int step1 = calculator.add(10, 5);         // 15
        int step2 = calculator.subtract(step1, 3); // 12
        int step3 = calculator.multiply(step2, 2); // 24
        assertEquals(24, step3);
        assertTrue(calculator.isEven(step3));      // 24 is even
    }

    @Test
    @DisplayName("Integration: divide by zero after chain throws exception")
    void testChainEndingInDivideByZero() {
        int result = calculator.subtract(5, 5); // 0
        assertThrows(ArithmeticException.class,
                () -> calculator.divide(10, result));
    }

}