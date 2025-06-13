import java.time.LocalDate;

/**
 * Book class representing a book in the library system
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private boolean isAvailable;
    private LocalDate publishDate;
    private int totalCopies;
    private int availableCopies;
    
    public Book(String isbn, String title, String author, String category, 
                LocalDate publishDate, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publishDate = publishDate;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.isAvailable = totalCopies > 0;
    }
    
    public boolean borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
            isAvailable = availableCopies > 0;
            return true;
        }
        return false;
    }
    
    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            isAvailable = true;
        }
    }
    
    // Getters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return isAvailable; }
    public LocalDate getPublishDate() { return publishDate; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    
    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setTotalCopies(int totalCopies) { 
        this.totalCopies = totalCopies;
        if (availableCopies > totalCopies) {
            availableCopies = totalCopies;
        }
        isAvailable = availableCopies > 0;
    }
    
    @Override
    public String toString() {
        return String.format("ISBN: %s | Title: %s | Author: %s | Category: %s | Available: %d/%d",
                isbn, title, author, category, availableCopies, totalCopies);
    }
}