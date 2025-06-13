import java.util.Scanner;

/**
 * A comprehensive calculator application that performs basic arithmetic operations
 * with proper error handling and input validation.
 */
public class Calculator {
    private Scanner scanner;
    
    public Calculator() {
        scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("=== Advanced Calculator ===");
        System.out.println("Available operations: +, -, *, /, %, ^, sqrt");
        
        while (true) {
            try {
                displayMenu();
                int choice = getChoice();
                
                if (choice == 8) {
                    System.out.println("Thank you for using the calculator!");
                    break;
                }
                
                performOperation(choice);
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    private void displayMenu() {
        System.out.println("\n--- Calculator Menu ---");
        System.out.println("1. Addition (+)");
        System.out.println("2. Subtraction (-)");
        System.out.println("3. Multiplication (*)");
        System.out.println("4. Division (/)");
        System.out.println("5. Modulus (%)");
        System.out.println("6. Power (^)");
        System.out.println("7. Square Root");
        System.out.println("8. Exit");
        System.out.print("Choose an operation (1-8): ");
    }
    
    private int getChoice() {
        int choice = scanner.nextInt();
        if (choice < 1 || choice > 8) {
            throw new IllegalArgumentException("Please enter a number between 1 and 8");
        }
        return choice;
    }
    
    private void performOperation(int choice) {
        double result = 0;
        double num1, num2;
        
        switch (choice) {
            case 1: // Addition
                System.out.print("Enter first number: ");
                num1 = scanner.nextDouble();
                System.out.print("Enter second number: ");
                num2 = scanner.nextDouble();
                result = add(num1, num2);
                System.out.printf("%.2f + %.2f = %.2f%n", num1, num2, result);
                break;
                
            case 2: // Subtraction
                System.out.print("Enter first number: ");
                num1 = scanner.nextDouble();
                System.out.print("Enter second number: ");
                num2 = scanner.nextDouble();
                result = subtract(num1, num2);
                System.out.printf("%.2f - %.2f = %.2f%n", num1, num2, result);
                break;
                
            case 3: // Multiplication
                System.out.print("Enter first number: ");
                num1 = scanner.nextDouble();
                System.out.print("Enter second number: ");
                num2 = scanner.nextDouble();
                result = multiply(num1, num2);
                System.out.printf("%.2f * %.2f = %.2f%n", num1, num2, result);
                break;
                
            case 4: // Division
                System.out.print("Enter dividend: ");
                num1 = scanner.nextDouble();
                System.out.print("Enter divisor: ");
                num2 = scanner.nextDouble();
                result = divide(num1, num2);
                System.out.printf("%.2f / %.2f = %.2f%n", num1, num2, result);
                break;
                
            case 5: // Modulus
                System.out.print("Enter first number: ");
                num1 = scanner.nextDouble();
                System.out.print("Enter second number: ");
                num2 = scanner.nextDouble();
                result = modulus(num1, num2);
                System.out.printf("%.2f %% %.2f = %.2f%n", num1, num2, result);
                break;
                
            case 6: // Power
                System.out.print("Enter base: ");
                num1 = scanner.nextDouble();
                System.out.print("Enter exponent: ");
                num2 = scanner.nextDouble();
                result = power(num1, num2);
                System.out.printf("%.2f ^ %.2f = %.2f%n", num1, num2, result);
                break;
                
            case 7: // Square Root
                System.out.print("Enter number: ");
                num1 = scanner.nextDouble();
                result = squareRoot(num1);
                System.out.printf("√%.2f = %.2f%n", num1, result);
                break;
        }
    }
    
    // Arithmetic operation methods
    private double add(double a, double b) {
        return a + b;
    }
    
    private double subtract(double a, double b) {
        return a - b;
    }
    
    private double multiply(double a, double b) {
        return a * b;
    }
    
    private double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }
    
    private double modulus(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Modulus by zero is not allowed");
        }
        return a % b;
    }
    
    private double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    
    private double squareRoot(double number) {
        if (number < 0) {
            throw new ArithmeticException("Square root of negative number is not allowed");
        }
        return Math.sqrt(number);
    }
    
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.start();
    }
}