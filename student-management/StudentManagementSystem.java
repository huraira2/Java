import java.util.*;
import java.io.*;

/**
 * Student Management System - A comprehensive system to manage student records
 * with CRUD operations and file persistence
 */
public class StudentManagementSystem {
    private ArrayList<Student> students;
    private Scanner scanner;
    private final String DATA_FILE = "students.txt";
    private int nextId;
    
    public StudentManagementSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
        nextId = 1;
        loadStudentsFromFile();
    }
    
    public void start() {
        System.out.println("=== Student Management System ===");
        
        while (true) {
            try {
                displayMenu();
                int choice = getChoice();
                
                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: viewAllStudents(); break;
                    case 3: searchStudent(); break;
                    case 4: updateStudent(); break;
                    case 5: deleteStudent(); break;
                    case 6: displayStatistics(); break;
                    case 7: 
                        saveStudentsToFile();
                        System.out.println("Thank you for using Student Management System!");
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
    
    private void displayMenu() {
        System.out.println("\n--- Student Management Menu ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Display Statistics");
        System.out.println("7. Exit");
        System.out.print("Enter your choice (1-7): ");
    }
    
    private int getChoice() {
        return scanner.nextInt();
    }
    
    private void addStudent() {
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter course: ");
        String course = scanner.nextLine();
        
        System.out.print("Enter GPA (0.0-4.0): ");
        double gpa = scanner.nextDouble();
        
        if (gpa < 0.0 || gpa > 4.0) {
            System.out.println("Invalid GPA. Please enter a value between 0.0 and 4.0");
            return;
        }
        
        Student student = new Student(nextId++, name, age, email, course, gpa);
        students.add(student);
        
        System.out.println("Student added successfully!");
        System.out.println(student);
    }
    
    private void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.println("\n--- All Students ---");
        System.out.println("Total Students: " + students.size());
        System.out.println("-".repeat(80));
        
        for (Student student : students) {
            System.out.println(student);
        }
    }
    
    private void searchStudent() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.println("Search by:");
        System.out.println("1. Student ID");
        System.out.println("2. Name");
        System.out.print("Enter choice: ");
        
        int searchChoice = scanner.nextInt();
        scanner.nextLine();
        
        switch (searchChoice) {
            case 1:
                System.out.print("Enter Student ID: ");
                int id = scanner.nextInt();
                Student foundById = findStudentById(id);
                if (foundById != null) {
                    System.out.println("Student found:");
                    System.out.println(foundById);
                } else {
                    System.out.println("Student with ID " + id + " not found.");
                }
                break;
                
            case 2:
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                ArrayList<Student> foundByName = findStudentsByName(name);
                if (!foundByName.isEmpty()) {
                    System.out.println("Students found:");
                    for (Student student : foundByName) {
                        System.out.println(student);
                    }
                } else {
                    System.out.println("No students found with name containing: " + name);
                }
                break;
                
            default:
                System.out.println("Invalid search option.");
        }
    }
    
    private void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }
        
        System.out.println("Current student information:");
        System.out.println(student);
        
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Name");
        System.out.println("2. Age");
        System.out.println("3. Email");
        System.out.println("4. Course");
        System.out.println("5. GPA");
        System.out.print("Enter choice: ");
        
        int updateChoice = scanner.nextInt();
        scanner.nextLine();
        
        switch (updateChoice) {
            case 1:
                System.out.print("Enter new name: ");
                student.setName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new age: ");
                student.setAge(scanner.nextInt());
                break;
            case 3:
                System.out.print("Enter new email: ");
                student.setEmail(scanner.nextLine());
                break;
            case 4:
                System.out.print("Enter new course: ");
                student.setCourse(scanner.nextLine());
                break;
            case 5:
                System.out.print("Enter new GPA: ");
                double newGpa = scanner.nextDouble();
                if (newGpa >= 0.0 && newGpa <= 4.0) {
                    student.setGpa(newGpa);
                } else {
                    System.out.println("Invalid GPA. Update cancelled.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        System.out.println("Student updated successfully!");
        System.out.println(student);
    }
    
    private void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();
        
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }
        
        System.out.println("Student to be deleted:");
        System.out.println(student);
        System.out.print("Are you sure? (y/n): ");
        
        scanner.nextLine(); // Consume newline
        String confirmation = scanner.nextLine().toLowerCase();
        
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            students.remove(student);
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private void displayStatistics() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.println("\n--- Student Statistics ---");
        System.out.println("Total Students: " + students.size());
        
        // Calculate average GPA
        double totalGpa = 0;
        double highestGpa = 0;
        double lowestGpa = 4.0;
        
        Map<String, Integer> courseCount = new HashMap<>();
        
        for (Student student : students) {
            double gpa = student.getGpa();
            totalGpa += gpa;
            
            if (gpa > highestGpa) highestGpa = gpa;
            if (gpa < lowestGpa) lowestGpa = gpa;
            
            String course = student.getCourse();
            courseCount.put(course, courseCount.getOrDefault(course, 0) + 1);
        }
        
        double averageGpa = totalGpa / students.size();
        
        System.out.printf("Average GPA: %.2f%n", averageGpa);
        System.out.printf("Highest GPA: %.2f%n", highestGpa);
        System.out.printf("Lowest GPA: %.2f%n", lowestGpa);
        
        System.out.println("\nStudents by Course:");
        for (Map.Entry<String, Integer> entry : courseCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " students");
        }
    }
    
    private Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getStudentId() == id) {
                return student;
            }
        }
        return null;
    }
    
    private ArrayList<Student> findStudentsByName(String name) {
        ArrayList<Student> found = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                found.add(student);
            }
        }
        return found;
    }
    
    private void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromFileString(line);
                students.add(student);
                if (student.getStudentId() >= nextId) {
                    nextId = student.getStudentId() + 1;
                }
            }
            System.out.println("Loaded " + students.size() + " students from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No existing data file found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    
    private void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Student student : students) {
                writer.println(student.toFileString());
            }
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.start();
    }
}