import java.util.*;

/**
 * Inventory Management System - A comprehensive inventory tracking application
 */
public class InventoryManagementSystem {
    private ArrayList<Product> products;
    private Scanner scanner;
    private double totalSales;
    
    public InventoryManagementSystem() {
        products = new ArrayList<>();
        scanner = new Scanner(System.in);
        totalSales = 0.0;
        initializeSampleData();
    }
    
    public void start() {
        System.out.println("=== Inventory Management System ===");
        
        while (true) {
            try {
                displayMainMenu();
                int choice = getChoice();
                
                switch (choice) {
                    case 1: addProduct(); break;
                    case 2: viewAllProducts(); break;
                    case 3: searchProducts(); break;
                    case 4: updateProduct(); break;
                    case 5: sellProduct(); break;
                    case 6: restockProduct(); break;
                    case 7: viewLowStockItems(); break;
                    case 8: generateReports(); break;
                    case 9: removeProduct(); break;
                    case 10:
                        System.out.println("Thank you for using Inventory Management System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add New Product");
        System.out.println("2. View All Products");
        System.out.println("3. Search Products");
        System.out.println("4. Update Product");
        System.out.println("5. Sell Product");
        System.out.println("6. Restock Product");
        System.out.println("7. View Low Stock Items");
        System.out.println("8. Generate Reports");
        System.out.println("9. Remove Product");
        System.out.println("10. Exit");
        System.out.print("Enter your choice (1-10): ");
    }
    
    private int getChoice() {
        return scanner.nextInt();
    }
    
    private void addProduct() {
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        
        System.out.print("Enter price: $");
        double price = scanner.nextDouble();
        
        System.out.print("Enter initial quantity: ");
        int quantity = scanner.nextInt();
        
        System.out.print("Enter minimum stock level: ");
        int minStock = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter supplier name: ");
        String supplier = scanner.nextLine();
        
        Product product = new Product(name, category, price, quantity, minStock, supplier);
        products.add(product);
        
        System.out.println("Product added successfully!");
        System.out.println(product);
    }
    
    private void viewAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        
        System.out.println("\n--- All Products ---");
        System.out.println("Total Products: " + products.size());
        System.out.println("-".repeat(120));
        
        for (Product product : products) {
            System.out.println(product);
        }
        
        // Summary
        double totalValue = 0;
        int lowStockCount = 0;
        
        for (Product product : products) {
            totalValue += product.getTotalValue();
            if (product.isLowStock()) {
                lowStockCount++;
            }
        }
        
        System.out.println("-".repeat(120));
        System.out.printf("Total Inventory Value: $%.2f%n", totalValue);
        System.out.println("Low Stock Items: " + lowStockCount);
    }
    
    private void searchProducts() {
        scanner.nextLine();
        
        System.out.println("Search by:");
        System.out.println("1. Product Name");
        System.out.println("2. Category");
        System.out.println("3. Supplier");
        System.out.println("4. Product ID");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        
        ArrayList<Product> results = new ArrayList<>();
        
        for (Product product : products) {
            boolean match = false;
            
            switch (choice) {
                case 1: match = product.getName().toLowerCase().contains(searchTerm); break;
                case 2: match = product.getCategory().toLowerCase().contains(searchTerm); break;
                case 3: match = product.getSupplier().toLowerCase().contains(searchTerm); break;
                case 4: match = product.getProductId().toLowerCase().contains(searchTerm); break;
            }
            
            if (match) {
                results.add(product);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No products found matching your search.");
        } else {
            System.out.println("\n--- Search Results ---");
            System.out.println("Found " + results.size() + " product(s):");
            for (Product product : results) {
                System.out.println(product);
            }
        }
    }
    
    private void updateProduct() {
        scanner.nextLine();
        System.out.print("Enter Product ID to update: ");
        String productId = scanner.nextLine();
        
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        
        System.out.println("Current product information:");
        System.out.println(product);
        
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Name");
        System.out.println("2. Category");
        System.out.println("3. Price");
        System.out.println("4. Minimum Stock Level");
        System.out.println("5. Supplier");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                product.setName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new category: ");
                product.setCategory(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new price: $");
                product.setPrice(scanner.nextDouble());
                break;
            case 4:
                System.out.print("Enter new minimum stock level: ");
                product.setMinStockLevel(scanner.nextInt());
                break;
            case 5:
                System.out.print("Enter new supplier: ");
                product.setSupplier(scanner.nextLine());
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        System.out.println("Product updated successfully!");
        System.out.println(product);
    }
    
    private void sellProduct() {
        scanner.nextLine();
        System.out.print("Enter Product ID to sell: ");
        String productId = scanner.nextLine();
        
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        
        System.out.println("Product: " + product.getName());
        System.out.println("Available quantity: " + product.getQuantity());
        System.out.printf("Price per unit: $%.2f%n", product.getPrice());
        
        System.out.print("Enter quantity to sell: ");
        int sellQuantity = scanner.nextInt();
        
        if (sellQuantity <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }
        
        if (product.sellProduct(sellQuantity)) {
            double saleAmount = sellQuantity * product.getPrice();
            totalSales += saleAmount;
            
            System.out.println("Sale completed successfully!");
            System.out.printf("Sold %d units for $%.2f%n", sellQuantity, saleAmount);
            System.out.println("Remaining quantity: " + product.getQuantity());
            
            if (product.isLowStock()) {
                System.out.println("WARNING: Product is now low in stock!");
            }
        } else {
            System.out.println("Insufficient stock! Available: " + product.getQuantity());
        }
    }
    
    private void restockProduct() {
        scanner.nextLine();
        System.out.print("Enter Product ID to restock: ");
        String productId = scanner.nextLine();
        
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        
        System.out.println("Product: " + product.getName());
        System.out.println("Current quantity: " + product.getQuantity());
        System.out.println("Minimum stock level: " + product.getMinStockLevel());
        
        System.out.print("Enter quantity to add: ");
        int restockQuantity = scanner.nextInt();
        
        if (restockQuantity <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }
        
        product.restockProduct(restockQuantity);
        
        System.out.println("Product restocked successfully!");
        System.out.printf("Added %d units. New quantity: %d%n", restockQuantity, product.getQuantity());
    }
    
    private void viewLowStockItems() {
        ArrayList<Product> lowStockProducts = new ArrayList<>();
        
        for (Product product : products) {
            if (product.isLowStock()) {
                lowStockProducts.add(product);
            }
        }
        
        if (lowStockProducts.isEmpty()) {
            System.out.println("No low stock items found. All products are adequately stocked!");
            return;
        }
        
        System.out.println("\n--- Low Stock Items ---");
        System.out.println("Items requiring attention: " + lowStockProducts.size());
        System.out.println("-".repeat(120));
        
        for (Product product : lowStockProducts) {
            System.out.println(product);
            System.out.printf("  → Recommended restock: %d units%n", 
                    Math.max(50, product.getMinStockLevel() * 2 - product.getQuantity()));
        }
    }
    
    private void generateReports() {
        System.out.println("\n--- Inventory Reports ---");
        
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }
        
        // Basic statistics
        System.out.println("=== INVENTORY SUMMARY ===");
        System.out.println("Total Products: " + products.size());
        System.out.printf("Total Sales: $%.2f%n", totalSales);
        
        // Calculate inventory value and stock status
        double totalValue = 0;
        int lowStockCount = 0;
        int outOfStockCount = 0;
        
        for (Product product : products) {
            totalValue += product.getTotalValue();
            if (product.getQuantity() == 0) {
                outOfStockCount++;
            } else if (product.isLowStock()) {
                lowStockCount++;
            }
        }
        
        System.out.printf("Total Inventory Value: $%.2f%n", totalValue);
        System.out.println("Low Stock Items: " + lowStockCount);
        System.out.println("Out of Stock Items: " + outOfStockCount);
        
        // Category analysis
        Map<String, Integer> categoryCount = new HashMap<>();
        Map<String, Double> categoryValue = new HashMap<>();
        
        for (Product product : products) {
            String category = product.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
            categoryValue.put(category, categoryValue.getOrDefault(category, 0.0) + product.getTotalValue());
        }
        
        System.out.println("\n=== CATEGORY ANALYSIS ===");
        for (String category : categoryCount.keySet()) {
            System.out.printf("%s: %d products, Value: $%.2f%n", 
                    category, categoryCount.get(category), categoryValue.get(category));
        }
        
        // Top 5 most valuable products
        System.out.println("\n=== TOP 5 MOST VALUABLE PRODUCTS ===");
        products.stream()
                .sorted((p1, p2) -> Double.compare(p2.getTotalValue(), p1.getTotalValue()))
                .limit(5)
                .forEach(p -> System.out.printf("%s - Value: $%.2f%n", p.getName(), p.getTotalValue()));
        
        // Supplier analysis
        Map<String, Integer> supplierCount = new HashMap<>();
        for (Product product : products) {
            String supplier = product.getSupplier();
            supplierCount.put(supplier, supplierCount.getOrDefault(supplier, 0) + 1);
        }
        
        System.out.println("\n=== SUPPLIER ANALYSIS ===");
        for (Map.Entry<String, Integer> entry : supplierCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " products");
        }
    }
    
    private void removeProduct() {
        scanner.nextLine();
        System.out.print("Enter Product ID to remove: ");
        String productId = scanner.nextLine();
        
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        
        System.out.println("Product to be removed:");
        System.out.println(product);
        
        if (product.getQuantity() > 0) {
            System.out.printf("WARNING: This product has %d units in stock (Value: $%.2f)%n", 
                    product.getQuantity(), product.getTotalValue());
        }
        
        System.out.print("Are you sure you want to remove this product? (y/n): ");
        String confirmation = scanner.nextLine().toLowerCase();
        
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            products.remove(product);
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Removal cancelled.");
        }
    }
    
    private Product findProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
    
    private void initializeSampleData() {
        // Add sample products
        products.add(new Product("Laptop", "Electronics", 999.99, 15, 5, "TechSupplier Inc"));
        products.add(new Product("Wireless Mouse", "Electronics", 29.99, 50, 10, "TechSupplier Inc"));
        products.add(new Product("Office Chair", "Furniture", 199.99, 8, 3, "FurnitureCorp"));
        products.add(new Product("Desk Lamp", "Furniture", 49.99, 25, 5, "FurnitureCorp"));
        products.add(new Product("Notebook", "Stationery", 4.99, 100, 20, "OfficeSupplies Ltd"));
        products.add(new Product("Pen Set", "Stationery", 12.99, 75, 15, "OfficeSupplies Ltd"));
        products.add(new Product("Coffee Maker", "Appliances", 89.99, 12, 4, "HomeAppliances Co"));
        products.add(new Product("Bluetooth Speaker", "Electronics", 79.99, 20, 8, "TechSupplier Inc"));
        
        System.out.println("Sample inventory data initialized: " + products.size() + " products");
    }
    
    public static void main(String[] args) {
        InventoryManagementSystem inventory = new InventoryManagementSystem();
        inventory.start();
    }
}