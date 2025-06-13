import java.util.*;

/**
 * Bank System - A comprehensive banking application with multiple account management
 */
public class BankSystem {
    private ArrayList<BankAccount> accounts;
    private Scanner scanner;
    
    public BankSystem() {
        accounts = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("=== Welcome to Java Bank System ===");
        
        while (true) {
            try {
                displayMainMenu();
                int choice = getChoice();
                
                switch (choice) {
                    case 1: createAccount(); break;
                    case 2: accountOperations(); break;
                    case 3: transferMoney(); break;
                    case 4: viewAllAccounts(); break;
                    case 5: 
                        System.out.println("Thank you for using Java Bank System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Create New Account");
        System.out.println("2. Account Operations");
        System.out.println("3. Transfer Money");
        System.out.println("4. View All Accounts");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }
    
    private int getChoice() {
        return scanner.nextInt();
    }
    
    private void createAccount() {
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter initial deposit amount: $");
        double initialDeposit = scanner.nextDouble();
        
        if (initialDeposit < 0) {
            System.out.println("Initial deposit cannot be negative!");
            return;
        }
        
        BankAccount account = new BankAccount(name, initialDeposit);
        accounts.add(account);
        
        System.out.println("\nAccount created successfully!");
        account.displayAccountInfo();
    }
    
    private void accountOperations() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found. Please create an account first.");
            return;
        }
        
        BankAccount account = selectAccount();
        if (account == null) return;
        
        while (true) {
            displayAccountMenu(account);
            int choice = getChoice();
            
            switch (choice) {
                case 1: // View Account Info
                    account.displayAccountInfo();
                    break;
                    
                case 2: // Deposit
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                    
                case 3: // Withdraw
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                    
                case 4: // View Transaction History
                    account.displayTransactionHistory();
                    break;
                    
                case 5: // View Recent Transactions
                    account.displayRecentTransactions();
                    break;
                    
                case 6: // Back to Main Menu
                    return;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayAccountMenu(BankAccount account) {
        System.out.println("\n--- Account Operations ---");
        System.out.println("Current Account: " + account.getAccountNumber() + 
                          " (" + account.getAccountHolderName() + ")");
        System.out.printf("Balance: $%.2f%n", account.getBalance());
        System.out.println("1. View Account Information");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. View Transaction History");
        System.out.println("5. View Recent Transactions");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice (1-6): ");
    }
    
    private void transferMoney() {
        if (accounts.size() < 2) {
            System.out.println("Need at least 2 accounts for transfer. Current accounts: " + accounts.size());
            return;
        }
        
        System.out.println("Select source account:");
        BankAccount sourceAccount = selectAccount();
        if (sourceAccount == null) return;
        
        System.out.println("Select destination account:");
        BankAccount destAccount = selectAccount();
        if (destAccount == null) return;
        
        if (sourceAccount == destAccount) {
            System.out.println("Cannot transfer to the same account!");
            return;
        }
        
        System.out.print("Enter transfer amount: $");
        double amount = scanner.nextDouble();
        
        sourceAccount.transfer(destAccount, amount);
    }
    
    private BankAccount selectAccount() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return null;
        }
        
        System.out.println("\n--- Select Account ---");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i));
        }
        
        System.out.print("Enter account number (1-" + accounts.size() + "): ");
        int choice = scanner.nextInt();
        
        if (choice < 1 || choice > accounts.size()) {
            System.out.println("Invalid account selection.");
            return null;
        }
        
        return accounts.get(choice - 1);
    }
    
    private void viewAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        System.out.println("\n--- All Bank Accounts ---");
        System.out.println("Total Accounts: " + accounts.size());
        System.out.println("-".repeat(70));
        
        double totalBalance = 0;
        for (BankAccount account : accounts) {
            System.out.println(account);
            totalBalance += account.getBalance();
        }
        
        System.out.println("-".repeat(70));
        System.out.printf("Total Bank Balance: $%.2f%n", totalBalance);
    }
    
    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        bankSystem.start();
    }
}