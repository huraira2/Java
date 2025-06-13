import java.util.*;
import java.time.LocalDate;

/**
 * Library Management System - A comprehensive library management application
 */
public class LibraryManagementSystem {
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private Scanner scanner;
    
    public LibraryManagementSystem() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeSampleData();
    }
    
    public void start() {
        System.out.println("=== Library Management System ===");
        
        while (true) {
            try {
                displayMainMenu();
                int choice = getChoice();
                
                switch (choice) {
                    case 1: bookManagement(); break;
                    case 2: memberManagement(); break;
                    case 3: borrowBook(); break;
                    case 4: returnBook(); break;
                    case 5: searchBooks(); break;
                    case 6: generateReports(); break;
                    case 7:
                        System.out.println("Thank you for using Library Management System!");
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
        System.out.println("1. Book Management");
        System.out.println("2. Member Management");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Books");
        System.out.println("6. Generate Reports");
        System.out.println("7. Exit");
        System.out.print("Enter your choice (1-7): ");
    }
    
    private int getChoice() {
        return scanner.nextInt();
    }
    
    private void bookManagement() {
        while (true) {
            System.out.println("\n--- Book Management ---");
            System.out.println("1. Add New Book");
            System.out.println("2. View All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Remove Book");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            
            int choice = getChoice();
            
            switch (choice) {
                case 1: addBook(); break;
                case 2: viewAllBooks(); break;
                case 3: updateBook(); break;
                case 4: removeBook(); break;
                case 5: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    
    private void addBook() {
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        
        // Check if book already exists
        if (findBookByIsbn(isbn) != null) {
            System.out.println("Book with this ISBN already exists!");
            return;
        }
        
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        
        System.out.print("Enter publish year: ");
        int year = scanner.nextInt();
        
        System.out.print("Enter number of copies: ");
        int copies = scanner.nextInt();
        
        LocalDate publishDate = LocalDate.of(year, 1, 1);
        Book book = new Book(isbn, title, author, category, publishDate, copies);
        books.add(book);
        
        System.out.println("Book added successfully!");
        System.out.println(book);
    }
    
    private void viewAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        
        System.out.println("\n--- All Books ---");
        System.out.println("Total Books: " + books.size());
        System.out.println("-".repeat(100));
        
        for (Book book : books) {
            System.out.println(book);
        }
    }
    
    private void updateBook() {
        scanner.nextLine();
        System.out.print("Enter ISBN of book to update: ");
        String isbn = scanner.nextLine();
        
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        System.out.println("Current book information:");
        System.out.println(book);
        
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Category");
        System.out.println("4. Total Copies");
        System.out.print("Enter choice: ");
        
        int choice = getChoice();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.print("Enter new title: ");
                book.setTitle(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new author: ");
                book.setAuthor(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new category: ");
                book.setCategory(scanner.nextLine());
                break;
            case 4:
                System.out.print("Enter new total copies: ");
                int newCopies = scanner.nextInt();
                if (newCopies >= (book.getTotalCopies() - book.getAvailableCopies())) {
                    book.setTotalCopies(newCopies);
                } else {
                    System.out.println("Cannot reduce copies below borrowed amount!");
                    return;
                }
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        System.out.println("Book updated successfully!");
        System.out.println(book);
    }
    
    private void removeBook() {
        scanner.nextLine();
        System.out.print("Enter ISBN of book to remove: ");
        String isbn = scanner.nextLine();
        
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            System.out.println("Cannot remove book. Some copies are currently borrowed!");
            return;
        }
        
        System.out.println("Book to be removed:");
        System.out.println(book);
        System.out.print("Are you sure? (y/n): ");
        
        String confirmation = scanner.nextLine().toLowerCase();
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            books.remove(book);
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Removal cancelled.");
        }
    }
    
    private void memberManagement() {
        while (true) {
            System.out.println("\n--- Member Management ---");
            System.out.println("1. Add New Member");
            System.out.println("2. View All Members");
            System.out.println("3. View Member Details");
            System.out.println("4. Update Member");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            
            int choice = getChoice();
            
            switch (choice) {
                case 1: addMember(); break;
                case 2: viewAllMembers(); break;
                case 3: viewMemberDetails(); break;
                case 4: updateMember(); break;
                case 5: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    
    private void addMember() {
        scanner.nextLine();
        
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        
        Member member = new Member(name, email, phone);
        members.add(member);
        
        System.out.println("Member added successfully!");
        System.out.println(member);
    }
    
    private void viewAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        
        System.out.println("\n--- All Members ---");
        System.out.println("Total Members: " + members.size());
        System.out.println("-".repeat(80));
        
        for (Member member : members) {
            System.out.println(member);
        }
    }
    
    private void viewMemberDetails() {
        scanner.nextLine();
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();
        
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        
        System.out.println("\n--- Member Details ---");
        System.out.println("ID: " + member.getMemberId());
        System.out.println("Name: " + member.getName());
        System.out.println("Email: " + member.getEmail());
        System.out.println("Phone: " + member.getPhone());
        System.out.println("Membership Date: " + member.getMembershipDate());
        System.out.println("Books Borrowed: " + member.getBorrowedBooksCount() + "/5");
        
        if (!member.getBorrowedBooks().isEmpty()) {
            System.out.println("\nBorrowed Books:");
            for (String isbn : member.getBorrowedBooks()) {
                Book book = findBookByIsbn(isbn);
                if (book != null) {
                    System.out.println("- " + book.getTitle() + " by " + book.getAuthor());
                }
            }
        }
    }
    
    private void updateMember() {
        scanner.nextLine();
        System.out.print("Enter member ID to update: ");
        String memberId = scanner.nextLine();
        
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        
        System.out.println("Current member information:");
        System.out.println(member);
        
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Name");
        System.out.println("2. Email");
        System.out.println("3. Phone");
        System.out.print("Enter choice: ");
        
        int choice = getChoice();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                member.setName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new email: ");
                member.setEmail(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new phone: ");
                member.setPhone(scanner.nextLine());
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        System.out.println("Member updated successfully!");
        System.out.println(member);
    }
    
    private void borrowBook() {
        scanner.nextLine();
        
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();
        
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        
        if (!member.canBorrowMore()) {
            System.out.println("Member has reached maximum borrowing limit (5 books)!");
            return;
        }
        
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        if (!book.isAvailable()) {
            System.out.println("Book is not available for borrowing!");
            return;
        }
        
        // Perform borrowing
        book.borrowBook();
        member.borrowBook(isbn);
        
        System.out.println("Book borrowed successfully!");
        System.out.println("Member: " + member.getName());
        System.out.println("Book: " + book.getTitle());
        System.out.println("Available copies: " + book.getAvailableCopies());
    }
    
    private void returnBook() {
        scanner.nextLine();
        
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();
        
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        
        if (member.getBorrowedBooks().isEmpty()) {
            System.out.println("Member has no borrowed books!");
            return;
        }
        
        System.out.println("Borrowed books:");
        for (int i = 0; i < member.getBorrowedBooks().size(); i++) {
            String isbn = member.getBorrowedBooks().get(i);
            Book book = findBookByIsbn(isbn);
            if (book != null) {
                System.out.println((i + 1) + ". " + book.getTitle() + " (" + isbn + ")");
            }
        }
        
        System.out.print("Enter book number to return: ");
        int bookChoice = scanner.nextInt();
        
        if (bookChoice < 1 || bookChoice > member.getBorrowedBooks().size()) {
            System.out.println("Invalid book selection!");
            return;
        }
        
        String isbn = member.getBorrowedBooks().get(bookChoice - 1);
        Book book = findBookByIsbn(isbn);
        
        // Perform return
        book.returnBook();
        member.returnBook(isbn);
        
        System.out.println("Book returned successfully!");
        System.out.println("Member: " + member.getName());
        System.out.println("Book: " + book.getTitle());
        System.out.println("Available copies: " + book.getAvailableCopies());
    }
    
    private void searchBooks() {
        scanner.nextLine();
        
        System.out.println("Search by:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Category");
        System.out.println("4. ISBN");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        
        ArrayList<Book> results = new ArrayList<>();
        
        for (Book book : books) {
            boolean match = false;
            
            switch (choice) {
                case 1: match = book.getTitle().toLowerCase().contains(searchTerm); break;
                case 2: match = book.getAuthor().toLowerCase().contains(searchTerm); break;
                case 3: match = book.getCategory().toLowerCase().contains(searchTerm); break;
                case 4: match = book.getIsbn().toLowerCase().contains(searchTerm); break;
            }
            
            if (match) {
                results.add(book);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No books found matching your search.");
        } else {
            System.out.println("\n--- Search Results ---");
            System.out.println("Found " + results.size() + " book(s):");
            for (Book book : results) {
                System.out.println(book);
            }
        }
    }
    
    private void generateReports() {
        System.out.println("\n--- Library Reports ---");
        
        // Basic statistics
        System.out.println("Total Books: " + books.size());
        System.out.println("Total Members: " + members.size());
        
        // Available vs borrowed books
        int totalCopies = 0;
        int availableCopies = 0;
        
        for (Book book : books) {
            totalCopies += book.getTotalCopies();
            availableCopies += book.getAvailableCopies();
        }
        
        System.out.println("Total Book Copies: " + totalCopies);
        System.out.println("Available Copies: " + availableCopies);
        System.out.println("Borrowed Copies: " + (totalCopies - availableCopies));
        
        // Most popular categories
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Book book : books) {
            categoryCount.put(book.getCategory(), 
                categoryCount.getOrDefault(book.getCategory(), 0) + 1);
        }
        
        System.out.println("\nBooks by Category:");
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " books");
        }
        
        // Active members
        int activeMembers = 0;
        for (Member member : members) {
            if (member.getBorrowedBooksCount() > 0) {
                activeMembers++;
            }
        }
        
        System.out.println("\nActive Members (with borrowed books): " + activeMembers);
        System.out.println("Inactive Members: " + (members.size() - activeMembers));
    }
    
    private Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
    
    private Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
    
    private void initializeSampleData() {
        // Add sample books
        books.add(new Book("978-0134685991", "Effective Java", "Joshua Bloch", "Programming", LocalDate.of(2017, 1, 1), 3));
        books.add(new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman", "Programming", LocalDate.of(2004, 1, 1), 2));
        books.add(new Book("978-0132350884", "Clean Code", "Robert Martin", "Programming", LocalDate.of(2008, 1, 1), 4));
        books.add(new Book("978-0201633610", "Design Patterns", "Gang of Four", "Programming", LocalDate.of(1994, 1, 1), 2));
        books.add(new Book("978-0321356680", "Effective C++", "Scott Meyers", "Programming", LocalDate.of(2005, 1, 1), 2));
        
        // Add sample members
        members.add(new Member("John Doe", "john@email.com", "123-456-7890"));
        members.add(new Member("Jane Smith", "jane@email.com", "098-765-4321"));
        members.add(new Member("Bob Johnson", "bob@email.com", "555-123-4567"));
        
        System.out.println("Sample data initialized: " + books.size() + " books, " + members.size() + " members");
    }
    
    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        library.start();
    }
}