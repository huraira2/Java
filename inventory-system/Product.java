/**
 * Product class representing an inventory item
 */
public class Product {
    private String productId;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private int minStockLevel;
    private String supplier;
    private static int productCounter = 1000;
    
    public Product(String name, String category, double price, int quantity, 
                   int minStockLevel, String supplier) {
        this.productId = "PRD" + (++productCounter);
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.minStockLevel = minStockLevel;
        this.supplier = supplier;
    }
    
    public boolean isLowStock() {
        return quantity <= minStockLevel;
    }
    
    public double getTotalValue() {
        return price * quantity;
    }
    
    public boolean sellProduct(int sellQuantity) {
        if (sellQuantity <= quantity) {
            quantity -= sellQuantity;
            return true;
        }
        return false;
    }
    
    public void restockProduct(int restockQuantity) {
        quantity += restockQuantity;
    }
    
    // Getters
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getMinStockLevel() { return minStockLevel; }
    public String getSupplier() { return supplier; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setMinStockLevel(int minStockLevel) { this.minStockLevel = minStockLevel; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    
    @Override
    public String toString() {
        String status = isLowStock() ? " [LOW STOCK]" : "";
        return String.format("ID: %s | %s | Category: %s | Price: $%.2f | Qty: %d | Min: %d | Supplier: %s%s",
                productId, name, category, price, quantity, minStockLevel, supplier, status);
    }
}