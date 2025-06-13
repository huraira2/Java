import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BankAccount class representing a bank account with transaction history
 */
public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private ArrayList<Transaction> transactionHistory;
    private static int accountCounter = 1000;
    
    // Constructor
    public BankAccount(String accountHolderName, double initialDeposit) {
        this.accountNumber = "ACC" + (++accountCounter);
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        
        // Add initial deposit transaction
        if (initialDeposit > 0) {
            addTransaction("DEPOSIT", initialDeposit, "Initial deposit");
        }
    }
    
    // Deposit money
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive!");
            return false;
        }
        
        balance += amount;
        addTransaction("DEPOSIT", amount, "Cash deposit");
        System.out.printf("Successfully deposited $%.2f. New balance: $%.2f%n", amount, balance);
        return true;
    }
    
    // Withdraw money
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive!");
            return false;
        }
        
        if (amount > balance) {
            System.out.println("Insufficient funds! Current balance: $" + String.format("%.2f", balance));
            return false;
        }
        
        balance -= amount;
        addTransaction("WITHDRAWAL", amount, "Cash withdrawal");
        System.out.printf("Successfully withdrew $%.2f. New balance: $%.2f%n", amount, balance);
        return true;
    }
    
    // Transfer money to another account
    public boolean transfer(BankAccount targetAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive!");
            return false;
        }
        
        if (amount > balance) {
            System.out.println("Insufficient funds for transfer!");
            return false;
        }
        
        // Perform transfer
        balance -= amount;
        targetAccount.balance += amount;
        
        // Record transactions
        addTransaction("TRANSFER_OUT", amount, "Transfer to " + targetAccount.accountNumber);
        targetAccount.addTransaction("TRANSFER_IN", amount, "Transfer from " + this.accountNumber);
        
        System.out.printf("Successfully transferred $%.2f to %s%n", amount, targetAccount.accountNumber);
        return true;
    }
    
    // Add transaction to history
    private void addTransaction(String type, double amount, String description) {
        Transaction transaction = new Transaction(type, amount, description, balance);
        transactionHistory.add(transaction);
    }
    
    // Display account information
    public void displayAccountInfo() {
        System.out.println("\n--- Account Information ---");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.printf("Current Balance: $%.2f%n", balance);
        System.out.println("Total Transactions: " + transactionHistory.size());
    }
    
    // Display transaction history
    public void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println("\n--- Transaction History ---");
        System.out.println("Account: " + accountNumber + " (" + accountHolderName + ")");
        System.out.println("-".repeat(80));
        
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
    
    // Display recent transactions (last 5)
    public void displayRecentTransactions() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println("\n--- Recent Transactions (Last 5) ---");
        int start = Math.max(0, transactionHistory.size() - 5);
        
        for (int i = start; i < transactionHistory.size(); i++) {
            System.out.println(transactionHistory.get(i));
        }
    }
    
    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance() { return balance; }
    public int getTransactionCount() { return transactionHistory.size(); }
    
    @Override
    public String toString() {
        return String.format("Account: %s | Holder: %s | Balance: $%.2f", 
                accountNumber, accountHolderName, balance);
    }
}

/**
 * Transaction class to represent individual transactions
 */
class Transaction {
    private String type;
    private double amount;
    private String description;
    private double balanceAfter;
    private LocalDateTime timestamp;
    
    public Transaction(String type, double amount, String description, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s | %s | $%.2f | %s | Balance: $%.2f",
                timestamp.format(formatter), type, amount, description, balanceAfter);
    }
    
    // Getters
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getBalanceAfter() { return balanceAfter; }
}