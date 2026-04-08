

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for Calculator class
 * File must end with "UnitTest" so CI pipeline picks it up separately
 */
@DisplayName("Calculator Unit Tests")
public class CalculatorUnitTest {

    private Calculator calculator;

    // Runs before each test - creates a fresh Calculator object
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    // ─── ADD ───────────────────────────────────────────────

    @Test
    @DisplayName("Add: 2 + 3 should equal 5")
    void testAddPositiveNumbers() {
        int result = calculator.add(2, 3);
        assertEquals(5, result, "2 + 3 should be 5");
    }

    @Test
    @DisplayName("Add: negative numbers")
    void testAddNegativeNumbers() {
        int result = calculator.add(-4, -6);
        assertEquals(-10, result);
    }

    @Test
    @DisplayName("Add: zero + number")
    void testAddWithZero() {
        assertEquals(7, calculator.add(0, 7));
    }

    // ─── SUBTRACT ─────────────────────────────────────────

    @Test
    @DisplayName("Subtract: 10 - 4 should equal 6")
    void testSubtract() {
        assertEquals(6, calculator.subtract(10, 4));
    }

    @Test
    @DisplayName("Subtract: result can be negative")
    void testSubtractNegativeResult() {
        assertEquals(-3, calculator.subtract(2, 5));
    }

    // ─── MULTIPLY ─────────────────────────────────────────

    @Test
    @DisplayName("Multiply: 3 x 4 should equal 12")
    void testMultiply() {
        assertEquals(12, calculator.multiply(3, 4));
    }

    @Test
    @DisplayName("Multiply: anything x 0 = 0")
    void testMultiplyByZero() {
        assertEquals(0, calculator.multiply(99, 0));
    }

    // ─── DIVIDE ───────────────────────────────────────────

    @Test
    @DisplayName("Divide: 10 / 2 should equal 5.0")
    void testDivide() {
        assertEquals(5.0, calculator.divide(10, 2));
    }

    @Test
    @DisplayName("Divide: dividing by zero throws ArithmeticException")
    void testDivideByZeroThrowsException() {
        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> calculator.divide(10, 0)
        );
        assertEquals("Cannot divide by zero!", exception.getMessage());
    }

    // ─── IS EVEN ──────────────────────────────────────────

    @Test
    @DisplayName("isEven: 4 is even")
    void testIsEvenTrue() {
        assertTrue(calculator.isEven(4));
    }

    @Test
    @DisplayName("isEven: 7 is not even")
    void testIsEvenFalse() {
        assertFalse(calculator.isEven(7));
    }

    // ─── FACTORIAL ────────────────────────────────────────

    @Test
    @DisplayName("Factorial: 5! = 120")
    void testFactorial() {
        assertEquals(120, calculator.factorial(5));
    }

    @Test
    @DisplayName("Factorial: 0! = 1")
    void testFactorialOfZero() {
        assertEquals(1, calculator.factorial(0));
    }

    @Test
    @DisplayName("Factorial: negative number throws exception")
    void testFactorialNegativeThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.factorial(-1));
    }

    // ─── PARAMETERIZED TEST ───────────────────────────────

    @ParameterizedTest
    @DisplayName("Add: multiple input combinations")
    @CsvSource({
            "1,  1,  2",
            "5,  5,  10",
            "0,  0,  0",
            "-1, 1,  0",
            "100, 200, 300"
    })
    void testAddParameterized(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }

}