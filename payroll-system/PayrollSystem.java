import java.util.*;

/**
 * Payroll System - A comprehensive employee payroll management application
 */
public class PayrollSystem {
    private ArrayList<Employee> employees;
    private ArrayList<PayrollRecord> payrollHistory;
    private Scanner scanner;
    
    public PayrollSystem() {
        employees = new ArrayList<>();
        payrollHistory = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeSampleData();
    }
    
    public void start() {
        System.out.println("=== Employee Payroll Management System ===");
        
        while (true) {
            try {
                displayMainMenu();
                int choice = getChoice();
                
                switch (choice) {
                    case 1: employeeManagement(); break;
                    case 2: processPayroll(); break;
                    case 3: viewPayrollHistory(); break;
                    case 4: generateReports(); break;
                    case 5: updateEmployeeData(); break;
                    case 6:
                        System.out.println("Thank you for using Payroll Management System!");
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
        System.out.println("1. Employee Management");
        System.out.println("2. Process Payroll");
        System.out.println("3. View Payroll History");
        System.out.println("4. Generate Reports");
        System.out.println("5. Update Employee Data");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }
    
    private int getChoice() {
        return scanner.nextInt();
    }
    
    private void employeeManagement() {
        while (true) {
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add New Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Remove Employee");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            
            int choice = getChoice();
            
            switch (choice) {
                case 1: addEmployee(); break;
                case 2: viewAllEmployees(); break;
                case 3: searchEmployee(); break;
                case 4: removeEmployee(); break;
                case 5: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    
    private void addEmployee() {
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        
        System.out.println("Select employee type:");
        System.out.println("1. Full-Time");
        System.out.println("2. Part-Time");
        System.out.println("3. Contract");
        System.out.print("Enter choice: ");
        
        int typeChoice = scanner.nextInt();
        Employee employee = null;
        
        switch (typeChoice) {
            case 1: // Full-Time
                System.out.print("Enter annual salary: $");
                double annualSalary = scanner.nextDouble();
                employee = new FullTimeEmployee(name, email, department, annualSalary);
                break;
                
            case 2: // Part-Time
                System.out.print("Enter hourly rate: $");
                double hourlyRate = scanner.nextDouble();
                employee = new PartTimeEmployee(name, email, department, hourlyRate);
                break;
                
            case 3: // Contract
                System.out.print("Enter contract amount: $");
                double contractAmount = scanner.nextDouble();
                System.out.print("Enter total number of projects: ");
                int totalProjects = scanner.nextInt();
                employee = new ContractEmployee(name, email, department, contractAmount, totalProjects);
                break;
                
            default:
                System.out.println("Invalid employee type.");
                return;
        }
        
        employees.add(employee);
        System.out.println("Employee added successfully!");
        System.out.println(employee);
    }
    
    private void viewAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        
        System.out.println("\n--- All Employees ---");
        System.out.println("Total Employees: " + employees.size());
        System.out.println("-".repeat(120));
        
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        
        // Summary by type
        Map<String, Integer> typeCount = new HashMap<>();
        for (Employee emp : employees) {
            typeCount.put(emp.getEmployeeType(), typeCount.getOrDefault(emp.getEmployeeType(), 0) + 1);
        }
        
        System.out.println("-".repeat(120));
        System.out.println("Employee Distribution:");
        for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " employees");
        }
    }
    
    private void searchEmployee() {
        scanner.nextLine();
        System.out.print("Enter employee ID or name to search: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        
        ArrayList<Employee> results = new ArrayList<>();
        
        for (Employee employee : employees) {
            if (employee.getEmployeeId().toLowerCase().contains(searchTerm) ||
                employee.getName().toLowerCase().contains(searchTerm)) {
                results.add(employee);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No employees found matching your search.");
        } else {
            System.out.println("\n--- Search Results ---");
            for (Employee employee : results) {
                System.out.println(employee);
            }
        }
    }
    
    private void removeEmployee() {
        scanner.nextLine();
        System.out.print("Enter Employee ID to remove: ");
        String employeeId = scanner.nextLine();
        
        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        System.out.println("Employee to be removed:");
        System.out.println(employee);
        System.out.print("Are you sure? (y/n): ");
        
        String confirmation = scanner.nextLine().toLowerCase();
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            employees.remove(employee);
            System.out.println("Employee removed successfully!");
        } else {
            System.out.println("Removal cancelled.");
        }
    }
    
    private void processPayroll() {
        if (employees.isEmpty()) {
            System.out.println("No employees found. Add employees first.");
            return;
        }
        
        System.out.println("\n--- Process Payroll ---");
        System.out.println("1. Process All Employees");
        System.out.println("2. Process Single Employee");
        System.out.print("Enter choice: ");
        
        int choice = getChoice();
        
        switch (choice) {
            case 1:
                processAllEmployees();
                break;
            case 2:
                processSingleEmployee();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void processAllEmployees() {
        System.out.println("Processing payroll for all employees...");
        
        for (Employee employee : employees) {
            PayrollRecord record = employee.generatePayrollRecord();
            payrollHistory.add(record);
        }
        
        System.out.println("Payroll processed successfully for " + employees.size() + " employees!");
        
        // Display summary
        double totalGross = 0, totalTax = 0, totalNet = 0;
        for (Employee emp : employees) {
            PayrollRecord record = emp.generatePayrollRecord();
            totalGross += record.getGrossSalary();
            totalTax += record.getTax();
            totalNet += record.getNetSalary();
        }
        
        System.out.println("\n--- Payroll Summary ---");
        System.out.printf("Total Gross Salary: $%.2f%n", totalGross);
        System.out.printf("Total Tax Deducted: $%.2f%n", totalTax);
        System.out.printf("Total Net Salary: $%.2f%n", totalNet);
    }
    
    private void processSingleEmployee() {
        scanner.nextLine();
        System.out.print("Enter Employee ID: ");
        String employeeId = scanner.nextLine();
        
        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        PayrollRecord record = employee.generatePayrollRecord();
        payrollHistory.add(record);
        
        System.out.println("Payroll processed successfully!");
        System.out.println(record);
    }
    
    private void viewPayrollHistory() {
        if (payrollHistory.isEmpty()) {
            System.out.println("No payroll history found.");
            return;
        }
        
        System.out.println("\n--- Payroll History ---");
        System.out.println("Total Records: " + payrollHistory.size());
        System.out.println("-".repeat(120));
        
        // Show recent 10 records
        int start = Math.max(0, payrollHistory.size() - 10);
        System.out.println("Showing last 10 records:");
        
        for (int i = start; i < payrollHistory.size(); i++) {
            System.out.println(payrollHistory.get(i));
        }
    }
    
    private void generateReports() {
        System.out.println("\n--- Payroll Reports ---");
        
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        
        // Employee statistics
        System.out.println("=== EMPLOYEE STATISTICS ===");
        System.out.println("Total Employees: " + employees.size());
        
        Map<String, Integer> typeCount = new HashMap<>();
        Map<String, Double> typeSalary = new HashMap<>();
        
        for (Employee emp : employees) {
            String type = emp.getEmployeeType();
            typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
            typeSalary.put(type, typeSalary.getOrDefault(type, 0.0) + emp.calculateSalary());
        }
        
        for (String type : typeCount.keySet()) {
            System.out.printf("%s: %d employees, Total Monthly Salary: $%.2f%n", 
                    type, typeCount.get(type), typeSalary.get(type));
        }
        
        // Department analysis
        Map<String, Integer> deptCount = new HashMap<>();
        Map<String, Double> deptSalary = new HashMap<>();
        
        for (Employee emp : employees) {
            String dept = emp.getDepartment();
            deptCount.put(dept, deptCount.getOrDefault(dept, 0) + 1);
            deptSalary.put(dept, deptSalary.getOrDefault(dept, 0.0) + emp.calculateSalary());
        }
        
        System.out.println("\n=== DEPARTMENT ANALYSIS ===");
        for (String dept : deptCount.keySet()) {
            System.out.printf("%s: %d employees, Total Monthly Salary: $%.2f%n", 
                    dept, deptCount.get(dept), deptSalary.get(dept));
        }
        
        // Salary statistics
        double totalSalary = 0;
        double maxSalary = 0;
        double minSalary = Double.MAX_VALUE;
        
        for (Employee emp : employees) {
            double salary = emp.calculateSalary();
            totalSalary += salary;
            maxSalary = Math.max(maxSalary, salary);
            minSalary = Math.min(minSalary, salary);
        }
        
        System.out.println("\n=== SALARY STATISTICS ===");
        System.out.printf("Total Monthly Payroll: $%.2f%n", totalSalary);
        System.out.printf("Average Salary: $%.2f%n", totalSalary / employees.size());
        System.out.printf("Highest Salary: $%.2f%n", maxSalary);
        System.out.printf("Lowest Salary: $%.2f%n", minSalary);
        
        // Payroll history summary
        if (!payrollHistory.isEmpty()) {
            System.out.println("\n=== PAYROLL HISTORY SUMMARY ===");
            System.out.println("Total Payroll Records: " + payrollHistory.size());
            
            double totalGross = 0, totalTax = 0, totalNet = 0;
            for (PayrollRecord record : payrollHistory) {
                totalGross += record.getGrossSalary();
                totalTax += record.getTax();
                totalNet += record.getNetSalary();
            }
            
            System.out.printf("Total Gross Paid: $%.2f%n", totalGross);
            System.out.printf("Total Tax Collected: $%.2f%n", totalTax);
            System.out.printf("Total Net Paid: $%.2f%n", totalNet);
        }
    }
    
    private void updateEmployeeData() {
        scanner.nextLine();
        System.out.print("Enter Employee ID to update: ");
        String employeeId = scanner.nextLine();
        
        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        System.out.println("Current employee information:");
        System.out.println(employee);
        
        if (employee instanceof PartTimeEmployee) {
            PartTimeEmployee ptEmp = (PartTimeEmployee) employee;
            System.out.print("Enter hours worked this period: ");
            int hours = scanner.nextInt();
            ptEmp.setHoursWorked(hours);
            System.out.println("Hours updated successfully!");
            
        } else if (employee instanceof FullTimeEmployee) {
            FullTimeEmployee ftEmp = (FullTimeEmployee) employee;
            System.out.print("Enter bonus amount: $");
            double bonus = scanner.nextDouble();
            ftEmp.setBonus(bonus);
            System.out.println("Bonus updated successfully!");
            
        } else if (employee instanceof ContractEmployee) {
            ContractEmployee ctEmp = (ContractEmployee) employee;
            System.out.print("Enter number of projects completed: ");
            int projects = scanner.nextInt();
            ctEmp.setProjectsCompleted(projects);
            System.out.println("Projects updated successfully!");
        }
        
        System.out.println("Updated employee information:");
        System.out.println(employee);
    }
    
    private Employee findEmployeeById(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                return employee;
            }
        }
        return null;
    }
    
    private void initializeSampleData() {
        // Add sample employees
        employees.add(new FullTimeEmployee("John Smith", "john@company.com", "Engineering", 75000));
        employees.add(new FullTimeEmployee("Sarah Johnson", "sarah@company.com", "Marketing", 65000));
        employees.add(new PartTimeEmployee("Mike Wilson", "mike@company.com", "Support", 25.0));
        employees.add(new PartTimeEmployee("Lisa Brown", "lisa@company.com", "Sales", 22.0));
        employees.add(new ContractEmployee("David Lee", "david@company.com", "Consulting", 50000, 10));
        
        // Set some initial data for part-time employees
        ((PartTimeEmployee) employees.get(2)).setHoursWorked(35);
        ((PartTimeEmployee) employees.get(3)).setHoursWorked(40);
        
        // Set projects for contract employee
        ((ContractEmployee) employees.get(4)).setProjectsCompleted(3);
        
        System.out.println("Sample employee data initialized: " + employees.size() + " employees");
    }
    
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();
        payrollSystem.start();
    }
}