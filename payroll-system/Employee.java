/**
 * Abstract Employee class - base class for all employee types
 */
public abstract class Employee {
    protected String employeeId;
    protected String name;
    protected String email;
    protected String department;
    protected static int employeeCounter = 1000;
    
    public Employee(String name, String email, String department) {
        this.employeeId = "EMP" + (++employeeCounter);
        this.name = name;
        this.email = email;
        this.department = department;
    }
    
    // Abstract method to be implemented by subclasses
    public abstract double calculateSalary();
    public abstract String getEmployeeType();
    
    // Common methods
    public PayrollRecord generatePayrollRecord() {
        double grossSalary = calculateSalary();
        double tax = calculateTax(grossSalary);
        double netSalary = grossSalary - tax;
        
        return new PayrollRecord(employeeId, name, grossSalary, tax, netSalary, getEmployeeType());
    }
    
    protected double calculateTax(double grossSalary) {
        // Simple tax calculation - 20% for salary > 50000, 15% otherwise
        if (grossSalary > 50000) {
            return grossSalary * 0.20;
        } else {
            return grossSalary * 0.15;
        }
    }
    
    // Getters
    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setDepartment(String department) { this.department = department; }
    
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Type: %s | Department: %s | Salary: $%.2f",
                employeeId, name, getEmployeeType(), department, calculateSalary());
    }
}