
public class Calculator {

    /**
     * Adds two numbers
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts second number from first
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiplies two numbers
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides first number by second
     * Throws exception if dividing by zero
     */
    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero!");
        }
        return (double) a / b;
    }

    /**
     * Returns true if number is even
     */
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * Returns the factorial of a number
     */
    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial not defined for negative numbers");
        }
        if (n == 0 || n == 1) return 1;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}