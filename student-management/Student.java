/**
 * Student class representing a student entity with basic information
 */
public class Student {
    private int studentId;
    private String name;
    private int age;
    private String email;
    private String course;
    private double gpa;
    
    // Constructor
    public Student(int studentId, String name, int age, String email, String course, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.course = course;
        this.gpa = gpa;
    }
    
    // Getters
    public int getStudentId() { return studentId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public double getGpa() { return gpa; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
    public void setCourse(String course) { this.course = course; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Age: %d | Email: %s | Course: %s | GPA: %.2f",
                studentId, name, age, email, course, gpa);
    }
    
    // Method to get formatted string for file storage
    public String toFileString() {
        return studentId + "," + name + "," + age + "," + email + "," + course + "," + gpa;
    }
    
    // Static method to create Student from file string
    public static Student fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        return new Student(
            Integer.parseInt(parts[0]),
            parts[1],
            Integer.parseInt(parts[2]),
            parts[3],
            parts[4],
            Double.parseDouble(parts[5])
        );
    }
}