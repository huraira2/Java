import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Member class representing a library member
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private LocalDate membershipDate;
    private ArrayList<String> borrowedBooks;
    private static int memberCounter = 1000;
    
    public Member(String name, String email, String phone) {
        this.memberId = "MEM" + (++memberCounter);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = LocalDate.now();
        this.borrowedBooks = new ArrayList<>();
    }
    
    public boolean canBorrowMore() {
        return borrowedBooks.size() < 5; // Maximum 5 books per member
    }
    
    public void borrowBook(String isbn) {
        if (canBorrowMore()) {
            borrowedBooks.add(isbn);
        }
    }
    
    public void returnBook(String isbn) {
        borrowedBooks.remove(isbn);
    }
    
    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getMembershipDate() { return membershipDate; }
    public ArrayList<String> getBorrowedBooks() { return borrowedBooks; }
    public int getBorrowedBooksCount() { return borrowedBooks.size(); }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Email: %s | Books Borrowed: %d/5",
                memberId, name, email, borrowedBooks.size());
    }
}